package consciousadnetwork.podisearch.dto.response;

import lombok.Data;

@Data
public class WordResponseDto {

    private String word;
    private double startTime; //sec
    private double endTime; //sec
}
