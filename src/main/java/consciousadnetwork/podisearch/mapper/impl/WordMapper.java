package consciousadnetwork.podisearch.mapper.impl;

import consciousadnetwork.podisearch.dto.response.WordResponseDto;
import consciousadnetwork.podisearch.mapper.ResponseDtoMapper;
import consciousadnetwork.podisearch.model.Word;
import org.springframework.stereotype.Component;

@Component
public class WordMapper implements ResponseDtoMapper<WordResponseDto, Word> {

    @Override
    public WordResponseDto mapToDto(Word word) {
        WordResponseDto dto = new WordResponseDto();
        dto.setWord(word.getWord());
        dto.setStartTime(word.getStartTime());
        dto.setEndTime(word.getEndTime());
        return dto;
    }
}
