package consciousadnetwork.podisearch.converter;

import consciousadnetwork.podisearch.model.Word;
import java.util.ArrayList;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class DocumentToWordConverter implements Converter<ArrayList<Document>, ArrayList<Word>> {

    public DocumentToWordConverter() {

    }

    @Override
    public ArrayList<Word> convert(ArrayList<Document> source) {
        ArrayList<Word> words = new ArrayList<>();
        for (Document document : source) {
            String word = document.getString("word");
            Double startTime = document.getDouble("startTime");
            Double endTime = document.getDouble("endTime");
            words.add(new Word(word, startTime, endTime));
        }
        return words;
    }
}
