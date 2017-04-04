package puzzle;

/**
 * Created by Hoang on 4/4/2017.
 */
public class Puzzle {
    private String question;
    private String phrase;
    private int round;

    public Puzzle(String question, String phrase, int round) {
        this.question = question;
        this.phrase = phrase;
        this.round = round;
    }

    public String getQuestion() {
        return question;
    }

    public String getPhrase() {
        return phrase;
    }

    public int getRound() {
        return round;
    }
}
