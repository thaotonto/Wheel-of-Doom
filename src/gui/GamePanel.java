package gui;

import player.Player;
import player.PlayerStatus;
import puzzle.Puzzle;
import utils.Utils;

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
    private BoardPanel boardPanel;
    private ButtonPanel buttonPanel;
    private AnswerPanel answerPanel;
    private SamPanel samPanel;
    private TimerPanel timerPanel;
    private String currentPhrase = "";
    private String phrase;
    private String question;
    private boolean finished = false;
    private String wheelResult;
    private ArrayList<Player> playerList;
    private Player currentPlayer;
    private boolean guessTrue = false;
    private String monitor = "";
    private int round;
    private boolean isEnd = false;
    private JPanel playerInfo = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
    private JLabel playerLabel;
    private Puzzle puzzle;
    private Image background;
    private int count;
    private boolean option = false;
    private Thread thread;

    public GamePanel() {

    }

    public GamePanel(Puzzle puzzle, ArrayList<Player> playerList) {
        background = Utils.loadImageFromRes("background.jpg");

        this.playerList = playerList;

        Iterator<Player> playerListIterator = playerList.iterator();
        System.out.println(nPlayer);
        while (playerListIterator.hasNext()) {
            Player temp = playerListIterator.next();
            temp.setCurrentScore(0);
            nPlayer++;
        }
        System.out.println("Number of players: " + nPlayer);
        playerList.get(puzzle.getRound() - 1).setStatus(PlayerStatus.PLAYING);
        currentPlayer = getCurrentPlayer();

        this.puzzle = puzzle;
        this.question = puzzle.getQuestion();
        this.phrase = puzzle.getPhrase().toUpperCase();
        this.round = puzzle.getRound();

        System.out.println("This round: " + round);
        System.out.println("This round's question: " + question);
        System.out.println("This round's phrase: " + phrase);
        for (int i = 0; i < phrase.length(); i++) {
            char append = phrase.charAt(i);
            if (append == ' ')
                currentPhrase += " ";
            else currentPhrase += "_";
        }

        setLayout(null);
        setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        boardPanel = new BoardPanel(currentPhrase, puzzle.getRound());
        buttonPanel = new ButtonPanel();
        answerPanel = new AnswerPanel();
        samPanel = new SamPanel(puzzle.getRound());
        timerPanel = new TimerPanel(30);

        this.add(boardPanel);
        this.add(buttonPanel);
        this.add(answerPanel);
        this.add(WheelPanel.instance);
        this.add(samPanel);
        this.add(timerPanel);

        answerPanel.setVisible(false);
        buttonPanel.setVisible(false);
        timerPanel.setVisible(false);

        setVisible(true);
        playerInfo.setBackground(Color.white);
        System.out.println("Phrase setup: " + currentPhrase);
        playerInfo.setBounds(200, 550, 600, 100);
        this.add(playerInfo);
        playerInfo.setOpaque(false);
        playerInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        paintPlayerInfo();
    }

    private void paintPlayerInfo() {
        playerInfo.removeAll();
        gbc.insets = new Insets(10, 10, 10, 10);
        for (int i = 0; i < 2; i++) {
            playerLabel = new JLabel(playerList.get(i).getName().toString());
            gbc.gridx = 0;
            gbc.gridy = i;
            playerInfo.add(playerLabel, gbc);
            playerLabel = new JLabel(playerList.get(i).getCurrentScore() + "");
            gbc.gridx = 1;
            gbc.gridy = i;
            playerInfo.add(playerLabel, gbc);
            playerLabel = new JLabel(playerList.get(i).getStatus().toString());
            gbc.gridx = 2;
            gbc.gridy = i;
            playerInfo.add(playerLabel, gbc);
            if (playerLabel.getText().toString().equals("PLAYING")) {
                gbc.gridx = 3;
                gbc.gridy = i;
                playerLabel = new JLabel(String.format("%d Extra Turn", playerList.get(i).getExtraTurn()));
                playerInfo.add(playerLabel, gbc);
            }
        }

        if (nPlayer > 2) {
            for (int i = 2; i < nPlayer; i++) {
                gbc.insets = new Insets(0, 100, 0, 0);
                playerLabel = new JLabel(playerList.get(i).getName().toString());
                gbc.gridx = 4;
                gbc.gridy = i - 2;
                playerInfo.add(playerLabel, gbc);
                gbc.insets = new Insets(10, 10, 10, 10);
                playerLabel = new JLabel(playerList.get(i).getCurrentScore() + "");
                gbc.gridx = 5;
                gbc.gridy = i - 2;
                playerInfo.add(playerLabel, gbc);
                playerLabel = new JLabel(playerList.get(i).getStatus().toString());
                gbc.gridx = 6;
                gbc.gridy = i - 2;
                playerInfo.add(playerLabel, gbc);
                if (playerLabel.getText().toString().equals("PLAYING")) {
                    gbc.gridx = 7;
                    gbc.gridy = i - 2;
                    playerLabel = new JLabel(String.format("%d Extra Turn", playerList.get(i).getExtraTurn()));
                    playerInfo.add(playerLabel, gbc);
                }
            }
        }
        validate();
        repaint();
    }

    private void nextPlayer() {
        guessTrue = false;
        option = false;
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
        paintPlayerInfo();
    }

    private void endGame() {
        isEnd = true;
        for (Player playerEl : playerList) {
//            playerEl.setCurrentScore(0);
            playerEl.setSpin(false);
            playerEl.setStatus(PlayerStatus.WAITING);
            playerEl.setExtraTurn(0);
        }
        finished = true;
    }

    public void getGuess() {
        String buttonPressed = buttonPanel.getButtonPressed();
        if (buttonPressed != "") {
            guessTrue = false;
            char c = buttonPressed.charAt(0);
//            System.out.println("You guessed: " + c);
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
                        if (count == 1) {
                            switch (wheelResult) {
                                case "get turn":
                                    currentPlayer.setExtraTurn(currentPlayer.getExtraTurn() + 1);
                                    break;
                                case "Prize":
                                    currentPlayer.setCurrentScore(currentPlayer.getCurrentScore() + 1200);
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
                    timerPanel.resetTimer();
                } else {
                    currentPlayer.setExtraTurn(currentPlayer.getExtraTurn() - 1);
                    currentPlayer.setSpin(false);
                }
            } else {
                currentPhrase = new String(currArr);
                updateBoard();
                currentPlayer.setSpin(false);
                timerPanel.resetTimer();
            }
//            wheelResult = "YO SPIN";
//            System.out.println("Phrase: " + phrase);
//            System.out.println("Current phrase: " + currentPhrase);
            buttonPanel.refreshButton();
            paintPlayerInfo();
            samPanel.sayResult(c, count);
        }
    }

    public void getAnswer() {
//        System.out.println("Getting answer");
        String answer = answerPanel.getAnswer();
        if (answer != "") {
            if (answer.equals(phrase)) {
                currentPhrase = phrase;
                answerPanel.refreshAnswer();
                samPanel.notifyAnswer(answer, phrase);
                for (Player playerEl : playerList) {
                    if (playerEl.getStatus() != PlayerStatus.PLAYING) {
                        playerEl.setCurrentScore(0);
                    }
                }
                updateBoard();
                repaint();
            } else {
                answerPanel.refreshAnswer();
                answerPanel.setVisible(false);
                currentPlayer.setStatus(PlayerStatus.BANNED);
                currentPlayer.setExtraTurn(0);
                currentPlayer.setCurrentScore(0);
                nextPlayer();
                currentPlayer = getCurrentPlayer();
                timerPanel.resetTimer();
                timerPanel.setVisible(false);
                samPanel.notifyAnswer(answer, phrase);
                if (currentPlayer != null)
                    samPanel.notifySpin(currentPlayer.getName());
                repaint();
            }
            paintPlayerInfo();
        }
    }

    public void checkWin() {
        if (currentPhrase.equals(phrase)) {
            count++;
            if (count == 1)
                currentPlayer.setTotalScore(currentPlayer.getTotalScore() + currentPlayer.getCurrentScore());
            for (Player playerEl : playerList) {
//                playerEl.setCurrentScore(0);
                playerEl.setSpin(false);
                playerEl.setStatus(PlayerStatus.WAITING);
                playerEl.setExtraTurn(0);
            }
            finished = true;
        }
    }


    public void updateBoard() {
        this.remove(boardPanel);
        boardPanel = new BoardPanel(currentPhrase, puzzle.getRound());
        this.add(boardPanel);
        revalidate();
        repaint();
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
        g.drawImage(background, 0, 0, null);

        drawCenteredString(g, question, new Font("Serif", Font.BOLD, 20));
    }

    public void drawCenteredString(Graphics g, String text, Font font) {

        FontMetrics metrics = g.getFontMetrics(font);
        int x = (GameFrame.GAME_WIDTH - metrics.stringWidth(text)) / 2;
        g.setFont(font);
        g.drawString(text, x, 170);
    }


    public void run() {
        if (timerPanel.run()) {
            samPanel.notifyTime();
            nextPlayer();
            currentPlayer = getCurrentPlayer();
        }
        if (guessTrue) {
            samPanel.notifyOptions();
            answerPanel.setVisible(true);
            timerPanel.setVisible(true);
            buttonPanel.setVisible(false);
            getAnswer();
            checkWin();
            option = true;
        }
        if (!isEnd) {
            String monitor = WheelPanel.instance.monitor;
            if (!currentPlayer.isSpin() && !guessTrue) {
                if (!option)
                    samPanel.notifySpin(currentPlayer.getName());
                synchronized (monitor) {
                    try {
                        WheelPanel.instance.setPowerBar(true);
                        buttonPanel.setVisible(false);

                        answerPanel.setVisible(false);
                        timerPanel.resetTimer();
                        timerPanel.setVisible(false);
                        monitor.wait();


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                WheelPanel.instance.setPowerBar(false);
                buttonPanel.setVisible(true);
                timerPanel.setVisible(true);
                wheelResult = WheelPanel.instance.getResult();
                currentPlayer.setSpin(true);
                samPanel.notifyGuess(wheelResult);
            }
            if (wheelResult == "lose turn") {
                buttonPanel.setVisible(false);
                nextPlayer();
                currentPlayer = getCurrentPlayer();
                timerPanel.resetTimer();
            } else if (wheelResult == "bankrupt") {
                buttonPanel.setVisible(false);
                currentPlayer.setCurrentScore(0);
                nextPlayer();
                currentPlayer = getCurrentPlayer();
                timerPanel.resetTimer();
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

    public String getPhrase() {
        return phrase;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public TimerPanel getTimerPanel() {
        return timerPanel;
    }
}