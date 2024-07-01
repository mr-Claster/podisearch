package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.service.CollectionService;
import consciousadnetwork.podisearch.service.VoiceDetectorService;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class PodcastDownloader {

    private static final int LIMIT = 200;
    private static final int OFFSET_LIMIT = 10000;
    private static final String URL = "https://feeds.simplecast.com";

    private final VoiceDetectorService voiceDetectorService;
    private final CollectionService collectionService;

    public PodcastDownloader(VoiceDetectorService voiceDetectorService,
                             CollectionService collectionService) {
        this.voiceDetectorService = voiceDetectorService;
        this.collectionService = collectionService;
    }

    public void getCollections() {
        for (int i = 0; LIMIT * i < OFFSET_LIMIT; i++) {
            String url = "https://itunes.apple.com/search"
                    + "?term=podcast"
                    + "&entity=podcast"
                    + "&lang=en_us"
                    + "&limit=" + LIMIT
                    + "&offset=" + LIMIT * i;
            HttpURLConnection connection = openHttpUrlConnection(url);
            try {
                JSONObject jsonObject = new JSONObject(getJsonResponse(connection));
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                int counter = 0;
                for (int j = 0; j < jsonArray.length(); j++) {
                    String feedUrl = jsonArray.getJSONObject(j).getString("feedUrl");
                    if (feedUrl.contains(URL)) {
                        counter++;
                        //Collection collection = new Collection();
                        //collection.setCollectionViewUrl(jsonArray.getJSONObject(j)
                        //  .getString("collectionViewUrl"));
                        //collection.setName(jsonArray.getJSONObject(j)
                        //  .getString("collectionName"));
                        //collection.setPublished(LocalDateTime.parse(jsonArray.getJSONObject(j)
                        //  .getString("releaseDate")));
                        //collection.setUrlToImg(jsonArray.getJSONObject(j)
                        //  .getString("artworkUrl600"));
                        //collectionService.createCollection(collection);
                        System.out.println(counter + " total: " + j);
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private HttpURLConnection openHttpUrlConnection(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection
                    = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            return connection;
        } catch (IOException e) {
            throw new RuntimeException("Can't open connection with url: " + url, e);
        }
    }

    private String getJsonResponse(HttpURLConnection connection) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException("Can't get response from connection: "
                    + connection.getURL(), e);
        }
    }

    public void savePodcastsByUrl(String rssUrl) {
        Document doc = null;
        try {
            URL url = new URL(rssUrl);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(url.openStream());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        // Get the root element of the XML document
        Element root = doc.getDocumentElement();
        // Get the list of "item" elements
        NodeList items = root.getElementsByTagName("item");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
        // Iterate over the "item" elements and print their titles
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            Podcast podcast = new Podcast();
            podcast.setTitle(item.getElementsByTagName("title")
                    .item(0)
                    .getTextContent());
            podcast.setPublished(LocalDateTime.parse(item
                    .getElementsByTagName("pubDate")
                    .item(0)
                    .getTextContent(),
                    formatter));
            //podcast.setUrlToImg(doc.getElementsByTagName("itunes:image")
            //.item(0)
            //.getAttributes()
            //.getNamedItem("href")
            //.getTextContent());
            String fileUrl = item
                    .getElementsByTagName("enclosure").item(0)
                    .getAttributes()
                    .getNamedItem("url")
                    .getTextContent();
            podcast.setWords(voiceDetectorService
                    .convertSoundToText(downloadAudio(fileUrl),
                            getExtensionByUrl(fileUrl)));
            //podcastService.savePodcast(podcast);
        }
    }

    private String getExtensionByUrl(String url) {
        int indexOfDote = url.lastIndexOf('.');
        return url.substring(indexOfDote, indexOfDote + 4);
    }

    byte[] downloadAudio(String url) {
        URLConnection connection = openHttpUrlConnection(url);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = connection.getInputStream();
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            byte[] data = new byte[1024];
            int count;
            while ((count = bufferedInputStream.read(data, 0, 1024)) != -1) {
                byteArrayOutputStream.write(data, 0, count);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data by url: " + url, e);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
