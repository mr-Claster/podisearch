package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.model.Word;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface VoiceDetectorService {
    List<Word> convertSoundToText(MultipartFile file);
}
