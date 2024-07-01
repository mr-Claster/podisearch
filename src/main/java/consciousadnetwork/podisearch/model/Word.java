package consciousadnetwork.podisearch.model;

import lombok.Data;

@Data
public class Word {

    private String word;
    private double startTime; //sec
    private double endTime; //sec

    public Word(String word,
                double startTime,
                double endTime) {
        this.word = word;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
