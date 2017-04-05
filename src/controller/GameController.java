package controller;

import gui.GameFrame;
import gui.GamePanel;
import gui.WheelPanel;
import player.Player;
import puzzle.Puzzle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hoang on 4/4/2017.
 */
public class GameController {
    private GamePanel gamePanel;
    private ArrayList<Player> playerList;
    private PuzzleController puzzleController;
    private Thread thread;
    private int round = 0;
    private int playerNo;

    public GameController(String theme, ArrayList<Player> playerList, int playerNo) {
        this.playerList = playerList;
        this.playerNo = playerNo;
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
                    runGame();
                }
            }
        });
        thread.start();
    }

    public void runGame() {
        gamePanel.run();
        if (gamePanel.isFinished()) nextRound();
    }

    public void nextRound() {
        round++;
        if (round > playerNo) {
            System.out.println("Game over");
            System.exit(0);
        }
        gamePanel = new GamePanel(puzzleController.getPuzzleList().get(round), playerList);
        GameFrame.mainPanel.showGamePanel(gamePanel);
    }

}
