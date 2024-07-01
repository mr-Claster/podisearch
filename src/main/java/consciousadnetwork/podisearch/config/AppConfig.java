package consciousadnetwork.podisearch.config;

import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import consciousadnetwork.podisearch.model.Word;
import consciousadnetwork.podisearch.converter.DocumentToWordConverter;
import consciousadnetwork.podisearch.converter.WordToDocumentConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    private static final int SAMPLE_RATE_HERTZ = 16000;
    private static final String LANGUAGE_CODE = "en-US";
    private static final boolean ENABLE_WORD_TIME_OFFSETS = true;
    private static final int AUDIO_CHANNEL_CONT = 2;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Converter<ArrayList<Document>, ArrayList<Word>> DocumentToWordConverter() {
        return new DocumentToWordConverter();
    }

    @Bean
    public Converter<List<Word>, List<Document>> WordToDocumentConverter() {
        return new WordToDocumentConverter();
    }

    @Bean
    public Map<String, RecognitionConfig> getRecognitionConfig() {
        return Map.of(
                ".wav", RecognitionConfig.newBuilder()
                        .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                        .setSampleRateHertz(SAMPLE_RATE_HERTZ)
                        .setLanguageCode(LANGUAGE_CODE)
                        .setEnableWordTimeOffsets(ENABLE_WORD_TIME_OFFSETS)
                        .setAudioChannelCount(AUDIO_CHANNEL_CONT)
                        .build(),
                ".mp3", RecognitionConfig.newBuilder()
                        .setEncoding(RecognitionConfig.AudioEncoding.MP3)
                        .setSampleRateHertz(SAMPLE_RATE_HERTZ)
                        .setLanguageCode(LANGUAGE_CODE)
                        .setEnableWordTimeOffsets(ENABLE_WORD_TIME_OFFSETS)
                        .setAudioChannelCount(AUDIO_CHANNEL_CONT)
                        .build()
        );
    }
}
