package consciousadnetwork.podisearch.service.mapper.impl;

import consciousadnetwork.podisearch.dto.request.PodcastRequestDto;
import consciousadnetwork.podisearch.dto.response.PodcastResponseDto;
import consciousadnetwork.podisearch.dto.response.WordResponseDto;
import consciousadnetwork.podisearch.model.Podcast;
import consciousadnetwork.podisearch.model.Word;
import consciousadnetwork.podisearch.service.VoiceDetectorService;
import consciousadnetwork.podisearch.service.mapper.RequestDtoMapper;
import consciousadnetwork.podisearch.service.mapper.ResponseDtoMapper;
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
        podcast.setUrlToImg(dto.getUrlToImg());
        podcast.setWords(voiceDetectorService.convertSoundToText(dto.getFile()));
        return podcast;
    }

    @Override
    public PodcastResponseDto mapToDto(Podcast podcast) {
        PodcastResponseDto dto = new PodcastResponseDto();
        dto.setId(podcast.getId());
        dto.setTitle(podcast.getTitle());
        dto.setWords(podcast.getWords()
                .stream()
                .map(wordWordResponseDtoMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setPublished(podcast.getPublished());
        dto.setUrlToImg(podcast.getUrlToImg());
        return dto;
    }
}
