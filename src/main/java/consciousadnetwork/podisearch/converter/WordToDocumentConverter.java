package consciousadnetwork.podisearch.converter;

import consciousadnetwork.podisearch.model.Word;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class WordToDocumentConverter implements Converter<List<Word>, List<Document>> {

    public WordToDocumentConverter() {

    }

    @Override
    public List<Document> convert(List<Word> source) {
        List<Document> documents = new ArrayList<>();
        for (Word word : source) {
            Document document = new Document();
            document.put("word", word.getWord());
            document.put("startTime", word.getStartTime());
            document.put("endTime", word.getEndTime());
            documents.add(document);
        }
        return documents;
    }
}
