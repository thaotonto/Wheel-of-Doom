package player;

/**
 * Created by Hoang on 3/30/2017.
 */
public class Player {
    private String name;
    private int currentScore;
    private int totalScore;
    private PlayerStatus status;
    private int extraTurn;
    private boolean isSpin = false;

    public Player(String name){
        this.name = name;
        currentScore = 0;
        totalScore = 0;
        status = PlayerStatus.WAITING;
    }

    public String getName() {
        return name;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public int getExtraTurn() {
        return extraTurn;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setExtraTurn(int extraTurn) {
        this.extraTurn = extraTurn;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public boolean isSpin() {
        return isSpin;
    }

    public void setSpin(boolean spin) {
        isSpin = spin;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
