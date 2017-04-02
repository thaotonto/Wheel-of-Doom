package gui;

import player.Player;
import player.PlayerStatus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Hoang on 3/29/2017.
 */
public class GamePanel extends JPanel {
    private static int nPlayer = 2;
    private static ArrayList<String> playerName = new ArrayList<>();
    private BoardPanel boardPanel;
    private ButtonPanel buttonPanel;
    private AnswerPanel answerPanel;
    private String currentPhrase = "";
    private String phrase;
    private String question;
    private Thread thread;
    private boolean isWin = true;
    private String wheelResult;
    private String prompt;
    private ArrayList<Player> playerList;
    private Player currentPlayer;
    private boolean guessTrue;
    private String monitor="";
    public GamePanel(String phrase) {
        for (int i = 0; i < 4; i++) {
            System.out.println(playerName.get(i));
        }
        playerList = new ArrayList<>();
        for (int i = 0; i < nPlayer; i++) {
            playerList.add(new Player(playerName.get(i)));
        }

        playerList.get(0).setStatus(PlayerStatus.PLAYING);
        currentPlayer = getCurrentPlayer();

        this.phrase = phrase;
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

        thread = new Thread(new Runnable() {
            String monitor= WheelPanel.instance.monitor;
            @Override
            public synchronized void run() {
                while (true) {
                    try {
                        thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!currentPlayer.isSpin()) {
                        synchronized (monitor)
                        {
                            try {
                                prompt="YO SPIN";
                                getGraphics().drawString(prompt,80,300);
                                WheelPanel.instance.setPowerBar(true);
                                buttonPanel.setVisible(false);
                                monitor.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        prompt=null;
                        WheelPanel.instance.setPowerBar(false);
                        buttonPanel.setVisible(true);
                        wheelResult=WheelPanel.instance.getResult();
                        currentPlayer.setSpin(true);
                        guessTrue = false;
                    }

                    if (wheelResult == "lose turn") {
                        nextPlayer();
                        currentPlayer = getCurrentPlayer();
                    } else if (wheelResult == "bankrupt") {
                        currentPlayer.setCurrentScore(0);
                        nextPlayer();
                        currentPlayer = getCurrentPlayer();
                    } else {
                        getGuess();
                        getAnswer();
                    }
                    revalidate();
                    repaint();
                }
            }
        });
        thread.start();

    }

    private void nextPlayer() {
        currentPlayer.setStatus(PlayerStatus.WAITING);
        currentPlayer.setSpin(false);
        if (playerList.indexOf(currentPlayer) == nPlayer - 1) {
            if (playerList.get(0).getStatus() != PlayerStatus.BANNED) {
                playerList.get(0).setStatus(PlayerStatus.PLAYING);
            }
        } else {
            if (playerList.get(playerList.indexOf(currentPlayer) + 1).getStatus() != PlayerStatus.BANNED) {
                playerList.get(playerList.indexOf(currentPlayer) + 1).setStatus(PlayerStatus.PLAYING);
            }
        }
    }

    public void getGuess() {
//        System.out.println("Phrase: " + phrase);
//        System.out.println("Current phrase: " + currentPhrase);
//        System.out.print("Your guess: ");
//        Scanner sc = new Scanner(System.in);
        String buttonPressed = buttonPanel.getButtonPressed();
        if (buttonPressed != "") {
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
                        e.printStackTrace();
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
            System.out.println("Phrase: " + phrase);
            System.out.println("Current phrase: " + currentPhrase);
            buttonPanel.refreshButton();
            updateBoard();
        }
    }

    public void getAnswer() {
        String answer = answerPanel.getAnswer();
        if (answer != "") {
            if (answer.equals(phrase)) {
                currentPhrase = phrase;
                answerPanel.refreshAnswer();
                updateBoard();
            }
        }
    }

    public boolean checkWin() {
        if (currentPhrase.equals(phrase)) return true;
        else return false;
    }

    public static void setnPlayer(int nPlayer) {
        GamePanel.nPlayer = nPlayer;
    }

    public static ArrayList<String> getPlayerName() {
        return playerName;
    }

    public void updateBoard() {
        this.remove(boardPanel);
        boardPanel = new BoardPanel(currentPhrase);
        this.add(boardPanel, BorderLayout.NORTH);
    }

    public Player getCurrentPlayer() {
        for (Player playerEl : playerList) {
            if (playerEl.getStatus() == PlayerStatus.PLAYING) {
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
        }
        g.drawString(currentPlayer.getName(), 50, 350);
        if (wheelResult != null) {
            g.drawString(wheelResult, 50, 300);
        }
        if(prompt!=null)
        {
            System.out.println("prompt");
            g.drawString(prompt,80,300);
        }
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
        synchronized (monitor)
        {
            thread.wait();
        }

        return WheelPanel.instance.getResult();
    }
}