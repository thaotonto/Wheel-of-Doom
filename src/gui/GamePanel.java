package gui;

import controller.GameController;
import player.Player;
import player.PlayerStatus;
import puzzle.Puzzle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * Created by Hoang on 3/29/2017.
 */
public class GamePanel extends JPanel {
    private int nPlayer = 0;
    //private ArrayList<String> playerName = new ArrayList<>();
    private BoardPanel boardPanel;
    private ButtonPanel buttonPanel;
    private AnswerPanel answerPanel;
    private String currentPhrase = "";
    private String phrase;
    private String question;
    private Thread thread;
    private boolean finished = false;
    private String wheelResult;
    private ArrayList<Player> playerList;
    private Player currentPlayer;
    private boolean guessTrue = false;
    private String monitor = "";
    private int round;
    private boolean isEnd = false;

    public GamePanel(Puzzle puzzle, ArrayList<Player> playerList) {
        this.playerList = playerList;

        Iterator<Player> playerListIterator = playerList.iterator();
        System.out.println(nPlayer);
        while (playerListIterator.hasNext()) {
            Player temp = playerListIterator.next();
            nPlayer++;
//            System.out.println(nPlayer);
        }
        System.out.println("Number of players: " + nPlayer);
        playerList.get(0).setStatus(PlayerStatus.PLAYING);
        currentPlayer = getCurrentPlayer();

        this.question = puzzle.getQuestion();
        this.phrase = puzzle.getPhrase().toUpperCase();
        this.round = puzzle.getRound();

        System.out.println("This round: " + puzzle.getRound());
        System.out.println("This round's question: " + puzzle.getQuestion());
        System.out.println("This round's phrase: " + puzzle.getPhrase());
        for (int i = 0; i < phrase.length(); i++) {
            char append = phrase.charAt(i);
            if (append == ' ')
                currentPhrase += " ";
            else currentPhrase += "_";
        }

        setLayout(null);
        setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        boardPanel = new BoardPanel(currentPhrase);
        buttonPanel = new ButtonPanel();
        answerPanel = new AnswerPanel();

        this.add(boardPanel);
        this.add(buttonPanel);
        this.add(answerPanel);
        this.add(WheelPanel.instance);
        setVisible(true);

        System.out.println("Phrase setup: " + currentPhrase);

    }

    private void nextPlayer() {
        guessTrue = false;
        int playerLeft = 0;
        int index = 0;
        for (int i = 0; i < nPlayer; i++) {
            if (playerList.get(i).getStatus() != PlayerStatus.BANNED) {
                playerLeft++;
                index = i;
            }
        }
        if (playerLeft == 0) {
            endGame();
        } else if (playerLeft == 1) {
            playerList.get(index).setStatus(PlayerStatus.PLAYING);
        } else {
            if (currentPlayer.getStatus() != PlayerStatus.BANNED) {
                currentPlayer.setStatus(PlayerStatus.WAITING);
            }
            if (playerList.indexOf(currentPlayer) == nPlayer - 1) {
                if (playerList.get(0).getStatus() != PlayerStatus.BANNED) {
                    playerList.get(0).setStatus(PlayerStatus.PLAYING);
                } else {
                    currentPlayer = playerList.get(0);
                    nextPlayer();
                }
            } else {
                if (playerList.get(playerList.indexOf(currentPlayer) + 1).getStatus() != PlayerStatus.BANNED) {
                    playerList.get(playerList.indexOf(currentPlayer) + 1).setStatus(PlayerStatus.PLAYING);
                } else {
                    currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
                    nextPlayer();
                }
            }
        }
    }

    private void endGame() {
        isEnd = true;
        for (Player playerEl : playerList) {
            playerEl.setCurrentScore(0);
            playerEl.setSpin(false);
            playerEl.setStatus(PlayerStatus.WAITING);
        }
        finished = true;
    }

    public void getGuess() {
        String buttonPressed = buttonPanel.getButtonPressed();
        if (buttonPressed != "") {
            guessTrue = false;
            char c = buttonPressed.charAt(0);
            System.out.println("You guessed: " + c);
            char[] phraseArr = phrase.toCharArray();
            char[] currArr = currentPhrase.toCharArray();
            int count = 0;
            for (int i = 0; i < phrase.length(); i++) {
                if (Character.toUpperCase(c) == phraseArr[i] && currArr[i] == '_') {
                    currArr[i] = phraseArr[i];
                    count++;
                    guessTrue = true;
                    try {
                        int point = Integer.parseInt(wheelResult);
                        currentPlayer.setCurrentScore(currentPlayer.getCurrentScore() + point);
                    } catch (NumberFormatException e) {
                        //e.printStackTrace();
                        if (count == 1) {
                            switch (wheelResult) {
                                case "get turn":
                                    currentPlayer.setExtraTurn(currentPlayer.getExtraTurn() + 1);
                                    break;
                                case "Prize":
                                    break;
                            }
                        }
                    }
                }
            }
            if (!guessTrue) {
                if (currentPlayer.getExtraTurn() == 0) {
                    nextPlayer();
                    currentPlayer = getCurrentPlayer();
                } else {
                    currentPlayer.setExtraTurn(currentPlayer.getExtraTurn() - 1);
                    currentPlayer.setSpin(false);
                }
            } else {
                currentPlayer.setSpin(false);
            }
            currentPhrase = new String(currArr);
            wheelResult = "YO SPIN";
            System.out.println("Phrase: " + phrase);
            System.out.println("Current phrase: " + currentPhrase);
            buttonPanel.refreshButton();
            updateBoard();
        }
    }

    public void getAnswer() {
        System.out.println("Getting answer");
        String answer = answerPanel.getAnswer();
        if (answer != "") {
            if (answer.equals(phrase)) {
                currentPhrase = phrase;
                answerPanel.refreshAnswer();
                updateBoard();
            } else {
                answerPanel.refreshAnswer();
                currentPlayer.setStatus(PlayerStatus.BANNED);
                currentPlayer.setExtraTurn(0);
                currentPlayer.setCurrentScore(0);
                nextPlayer();
                currentPlayer = getCurrentPlayer();
            }
        }
    }

    public void checkWin() {
        if (currentPhrase.equals(phrase)) {
            currentPlayer.setTotalScore(currentPlayer.getTotalScore() + currentPlayer.getCurrentScore());
            for (Player playerEl : playerList) {
                playerEl.setCurrentScore(0);
                playerEl.setSpin(false);
                playerEl.setStatus(PlayerStatus.WAITING);
            }
            finished = true;
        }
    }


    public void updateBoard() {
        this.remove(boardPanel);
        boardPanel = new BoardPanel(currentPhrase);
        this.add(boardPanel, BorderLayout.NORTH);
    }

    public Player getCurrentPlayer() {
        for (Player playerEl : playerList) {
            if (playerEl.getStatus() == PlayerStatus.PLAYING) {
                playerEl.setSpin(false);
                return playerEl;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < nPlayer; i++) {
            g.drawString(playerList.get(i).getName(), 50, 400 + (i * 50));
            g.drawString(playerList.get(i).getCurrentScore() + "", 50 + 70, 400 + (i * 50));
            g.drawString(playerList.get(i).getStatus().toString() + "", 50 + 70 + 70, 400 + (i * 50));
        }
        g.drawString(currentPlayer.getName(), 50, 350);
        g.drawString(question, 200, 200);
        g.drawString("Round " + round, 450, 50);
        if (wheelResult != null) {
            g.drawString(wheelResult, 50, 300);
        } else
            g.drawString("YO SPIN", 50, 300);

    }

    private String spinWheel() {
        Random random = new Random();
        String result = null;
        switch (random.nextInt(15)) {
            case 0:
                result = "900";
                break;
            case 1:
                result = "get turn";
                break;
            case 2:
                result = "500";
                break;
            case 3:
                result = "1000";
                break;
            case 4:
                result = "400";
                break;
            case 5:
                result = "lose turn";
                break;
            case 6:
                result = "600";
                break;
            case 7:
                result = "Prize";
                break;
            case 8:
                result = "300";
                break;
            case 9:
                result = "800";
                break;
            case 10:
                result = "1100";
                break;
            case 11:
                result = "100";
                break;
            case 12:
                result = "200";
                break;
            case 13:
                result = "700";
                break;
            case 14:
                result = "bankrupt";
                break;
        }
        return result;
    }

    private String spinWheel1() throws InterruptedException {
        synchronized (monitor) {
            thread.wait();
        }

        return WheelPanel.instance.getResult();
    }

    public void run() {

        System.out.println("Spin : " + currentPlayer.isSpin());
        System.out.println("Guess: " + guessTrue);

        if (guessTrue) {
            System.out.println("gud");
            answerPanel.setVisible(true);
            getAnswer();
            checkWin();
        }
        if (!isEnd) {
            String monitor = WheelPanel.instance.monitor;
            if (!currentPlayer.isSpin() && !guessTrue) {
                synchronized (monitor) {
                    try {
                        WheelPanel.instance.setPowerBar(true);
                        buttonPanel.setVisible(false);
                        answerPanel.setVisible(false);
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                WheelPanel.instance.setPowerBar(false);
                buttonPanel.setVisible(true);
                wheelResult = WheelPanel.instance.getResult();
                currentPlayer.setSpin(true);
                //guessTrue = false;
            }
            if (wheelResult == "lose turn") {
                wheelResult = "Mat luot xin moi nguoi tiep theo quay";
                nextPlayer();
                currentPlayer = getCurrentPlayer();
            } else if (wheelResult == "bankrupt") {
                wheelResult = "Mat diem xin moi nguoi tiep theo quay";
                currentPlayer.setCurrentScore(0);
                nextPlayer();
                currentPlayer = getCurrentPlayer();
            } else {
                getGuess();
                checkWin();
            }
            revalidate();
            repaint();
        }
    }

    public void setGuessTrue(boolean guessTrue) {
        this.guessTrue = guessTrue;
    }

    public boolean isFinished() {
        return finished;
    }
}