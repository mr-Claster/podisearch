package consciousadnetwork.podisearch.service.impl;

import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import com.google.protobuf.Duration;
import consciousadnetwork.podisearch.model.Word;
import consciousadnetwork.podisearch.service.VoiceDetectorService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VoiceDetectorServiceImpl implements VoiceDetectorService {
    private static final double NANO = 0.000000001;
    private final Map<String, RecognitionConfig> recognitionConfigMap;

    public VoiceDetectorServiceImpl(Map<String, RecognitionConfig> recognitionConfigMap) {
        this.recognitionConfigMap = recognitionConfigMap;
    }

    @Override
    public List<Word> convertSoundToText(MultipartFile file) {
        RecognitionConfig recognitionConfig = getRecognitionConfig(file);
        RecognitionAudio recognitionAudio = getRecognitionAudio(file);

        try (SpeechClient speechClient = SpeechClient.create()) {
            RecognizeResponse response = speechClient
                    .recognize(recognitionConfig, recognitionAudio);
            return extractWordsFromResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("Can't create speech client", e);
        }
    }

    private RecognitionConfig getRecognitionConfig(MultipartFile file) {
        String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf('.'));
        return recognitionConfigMap.get(fileExtension);
    }

    private RecognitionAudio getRecognitionAudio(MultipartFile file) {
        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from uploaded file", e);
        }
        ByteString audioBytes = ByteString.copyFrom(data);
        return RecognitionAudio.newBuilder()
                .setContent(audioBytes)
                .build();
    }

    private List<Word> extractWordsFromResponse(RecognizeResponse response) {
        List<Word> words = new ArrayList<>();
        List<SpeechRecognitionResult> results = response.getResultsList();
        for (SpeechRecognitionResult result : results) {
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            words.addAll(alternative.getWordsList()
                    .stream()
                    .map(w -> new Word(w.getWord(),
                            durationToSeconds(w.getStartTime()),
                            durationToSeconds(w.getEndTime()))
                    ).toList());
        }
        return words;
    }

    private double durationToSeconds(Duration duration) {
        return duration.getSeconds() + duration.getNanos() * NANO;
    }
}
