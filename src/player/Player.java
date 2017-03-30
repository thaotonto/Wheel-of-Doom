package player;

/**
 * Created by Hoang on 3/30/2017.
 */
public class Player {
    private String name;
    private int currentScore;
    private int totalScore;
    private PlayerStatus status;

    public Player(String name){
        this.name = name;
        currentScore = 0;
        totalScore = 0;
        status = PlayerStatus.WAITING;
    }
}
