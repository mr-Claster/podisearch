package consciousadnetwork.podisearch.mapper.impl;

import consciousadnetwork.podisearch.dto.request.PodcastRequestDto;
import consciousadnetwork.podisearch.dto.response.PodcastResponseDto;
import consciousadnetwork.podisearch.dto.response.WordResponseDto;
import consciousadnetwork.podisearch.mapper.RequestDtoMapper;
import consciousadnetwork.podisearch.mapper.ResponseDtoMapper;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.model.Word;
import consciousadnetwork.podisearch.service.VoiceDetectorService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PodcastMapper implements ResponseDtoMapper<PodcastResponseDto, Podcast>,
        RequestDtoMapper<PodcastRequestDto, Podcast> {

    private final ResponseDtoMapper<WordResponseDto, Word> wordWordResponseDtoMapper;
    private final VoiceDetectorService voiceDetectorService;

    public PodcastMapper(ResponseDtoMapper<WordResponseDto, Word> wordWordResponseDtoMapper,
                         VoiceDetectorService voiceDetectorService) {
        this.wordWordResponseDtoMapper = wordWordResponseDtoMapper;
        this.voiceDetectorService = voiceDetectorService;
    }

    @Override
    public Podcast mapToModel(PodcastRequestDto dto) {
        Podcast podcast = new Podcast();
        podcast.setTitle(dto.getTitle());
        podcast.setPublished(dto.getPublished());
        podcast.setWords(voiceDetectorService.convertSoundToText(dto.getFile()));
        return podcast;
    }

    @Override
    public PodcastResponseDto mapToDto(Podcast podcast) {
        PodcastResponseDto dto = new PodcastResponseDto();
        dto.setTitle(podcast.getTitle());
        dto.setWords(podcast.getWords()
                .stream()
                .map(wordWordResponseDtoMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setPublished(podcast.getPublished());
        return dto;
    }
}
