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
    public static BonusRoundPanel bonusRoundPanel;
    private ArrayList<Player> winnerList;

    public GameController(String theme, ArrayList<Player> playerList, int playerNo) {
        winnerList = new ArrayList<>();
        round = 0;
        this.playerList = playerList;
        this.playerNo = playerNo;
        if (specialRound != null) specialRound = null;
        if (bonusRoundPanel != null) bonusRoundPanel = null;
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
                        if (specialRound != null) {
                            if (specialRound.isWin())
                                GameFrame.mainPanel.showGameWinPanel();
                            else
                                GameFrame.mainPanel.showGameLosePanel();
                            break;
                        }

                    }
                }
            }
        });
        thread.start();
    }

    public void runGame() {
        if (specialRound == null) {
            if(bonusRoundPanel==null) {
                gamePanel.run();
                if (gamePanel.isFinished()) nextRound();
            }
            else
            {
                if(!bonusRoundPanel.isFinish())
                {
                    bonusRoundPanel.run();
                }
                else {
                    bonusRoundPanel=null;
                }

            }
        } else {
            specialRound.run();
            if (specialRound.isFinished()) nextRound();
        }
    }

    public void nextRound() {
        round++;
        if (round > playerNo) {
            System.out.println("Game over");
        } else {
            if (round == playerNo) {
                int maxScore = 0;
                for (int i = 0; i < playerNo; i++) {
                    if (playerList.get(i).getTotalScore() >= maxScore) {
                        maxScore = playerList.get(i).getTotalScore();
                    }
                }
                for (int i = 0; i < playerNo; i++) {
                    if (playerList.get(i).getTotalScore() == maxScore) {
                        winnerList.add(playerList.get(i));
                    }
                }
                if (winnerList.size() == 1) {
                    GameFrame.mainPanel.showSummaryPanel();
                    specialRound = new SpecialRoundPanel(puzzleController.getPuzzleList().get(round), winnerList.get(0));
                } else {
                    bonusRoundPanel = new BonusRoundPanel(puzzleController.getPuzzleList().get(round), winnerList);
                    GameFrame.mainPanel.showBonusRoundPanel(bonusRoundPanel);
                }

            } else {
                GameFrame.mainPanel.showNextRoundPanel();
                gamePanel = new GamePanel(puzzleController.getPuzzleList().get(round), playerList);
            }
        }
    }

}
