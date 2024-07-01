package consciousadnetwork.podisearch.mapper.impl;

import consciousadnetwork.podisearch.dto.request.CollectionRequestDto;
import consciousadnetwork.podisearch.dto.response.CollectionResponseDto;
import consciousadnetwork.podisearch.dto.response.PodcastResponseDto;
import consciousadnetwork.podisearch.mapper.RequestDtoMapper;
import consciousadnetwork.podisearch.mapper.ResponseDtoMapper;
import consciousadnetwork.podisearch.model.Collection;
import consciousadnetwork.podisearch.model.Podcast;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CollectionMapper implements RequestDtoMapper<CollectionRequestDto, Collection>,
        ResponseDtoMapper<CollectionResponseDto, Collection> {

    private final ResponseDtoMapper<PodcastResponseDto, Podcast> podcastMapper;

    public CollectionMapper(ResponseDtoMapper<PodcastResponseDto, Podcast> podcastMapper) {
        this.podcastMapper = podcastMapper;
    }

    @Override
    public Collection mapToModel(CollectionRequestDto dto) {
        Collection collection = new Collection();
        collection.setName(dto.getName());
        collection.setUrlToImg(dto.getUrlToImg());
        collection.setPublished(dto.getPublished());
        collection.setViewUrl(dto.getViewUrl());
        return collection;
    }

    @Override
    public CollectionResponseDto mapToDto(Collection collection) {
        CollectionResponseDto dto = new CollectionResponseDto();
        dto.setName(collection.getName());
        List<Podcast> podcasts = collection.getPodcasts();
        dto.setPodcasts(podcasts.stream()
                .map(podcastMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setPublished(collection.getPublished());
        dto.setUrlToImg(collection.getUrlToImg());
        dto.setViewUrl(collection.getViewUrl());
        return dto;
    }
}
