package controller;

import gui.*;
import player.Player;
import puzzle.Puzzle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hoang on 4/4/2017.
 */
public class GameController {
    public static GamePanel gamePanel;
    private ArrayList<Player> playerList;
    private PuzzleController puzzleController;
    private Thread thread;
    private int round;
    private int playerNo;
    public static SpecialRoundPanel specialRound;

    public GameController(String theme, ArrayList<Player> playerList, int playerNo) {
        round = 0;
        this.playerList = playerList;
        this.playerNo = playerNo;
        if (specialRound != null) specialRound = null;
        System.out.println("Init question:");
        puzzleController = new PuzzleController(theme, playerNo);
        Iterator<Puzzle> puzzleListIterator = puzzleController.getPuzzleList().iterator();
        while (puzzleListIterator.hasNext()) {
            Puzzle temp = puzzleListIterator.next();
            System.out.println("Game question: " + temp.getQuestion());
            System.out.println("Game phrase: " + temp.getPhrase());
            System.out.println("Round : " + temp.getRound());
        }
        System.out.println("Finished question init");
        System.out.println("Init gamepanel:");
        gamePanel = new GamePanel(puzzleController.getPuzzleList().get(round), playerList);
        System.out.println("Finished gamepanel init");
        GameFrame.mainPanel.showGamePanel(gamePanel);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (round <= playerNo)
                        runGame();
                    else {
                        if(specialRound.isWin())
                            GameFrame.mainPanel.showGameWinPanel();
                        else
                            GameFrame.mainPanel.showGameLosePanel();
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    public void runGame() {
        if (specialRound == null) {
            gamePanel.run();
            if (gamePanel.isFinished()) nextRound();
        } else {
            specialRound.run();
            if (specialRound.isFinished()) nextRound();
        }
    }

    public void nextRound() {
        round++;
        if (round > playerNo) {
            System.out.println("Game over");
//            System.exit(0);
        } else {
            if (round == playerNo) {
                int playerWin = 0;
                int maxScore = 0;
                for (int i = 0; i < playerNo; i++) {
                    if (playerList.get(i).getTotalScore() >= maxScore) {
                        playerWin = i;
                        maxScore = playerList.get(i).getTotalScore();
                    }
                }
                GameFrame.mainPanel.showSummaryPanel();
                specialRound = new SpecialRoundPanel(puzzleController.getPuzzleList().get(round), playerList.get(playerWin));


            } else {
                GameFrame.mainPanel.showNextRoundPanel();
                gamePanel = new GamePanel(puzzleController.getPuzzleList().get(round), playerList);
//                GameFrame.mainPanel.showGamePanel(gamePanel);
            }
        }
    }

}
