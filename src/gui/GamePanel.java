package gui;

import player.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Hoang on 3/29/2017.
 */
public class GamePanel extends JPanel {
    private BoardPanel boardPanel;
    private ButtonPanel buttonPanel;
    private AnswerPanel answerPanel;
    private String currentPhrase = "";
    private String phrase;
    private String question;
    private Thread thread;
    private static int nPlayer;
    private ArrayList<Player> playerList;
    private Player currentPlayer;

    private BufferedImage bufferedImage;
    private Graphics backGraphic;

    public GamePanel(String phrase) {

        playerList = new ArrayList<Player>();
        playerList.add(new Player("Hoang"));
        playerList.add(new Player("Le"));
        currentPlayer = getCurrentPlayer("Hoang");

        this.phrase = phrase;
        for (int i = 0; i < phrase.length(); i++) {
            char append = phrase.charAt(i);
            if (append == ' ')
                currentPhrase += " ";
            else currentPhrase += "_";
        }

        //BorderLayout borderLayout = new BorderLayout();
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
            @Override
            public void run() {
                while (true) {
                    getGuess();
                    getAnswer();
                    revalidate();
                    if (checkWin()) {
                        System.out.println("YOU WON!");
                    }
                }
            }
        });
        thread.start();

        bufferedImage = new BufferedImage(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);

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
            for (int i = 0; i < phrase.length(); i++) {
                if (Character.toUpperCase(c) == phraseArr[i] && currArr[i] == '_') {
                    currArr[i] = phraseArr[i];
                }
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

    public static int getnPlayer() {
        return nPlayer;
    }

    public static void setnPlayer(int nPlayer) {
        GamePanel.nPlayer = nPlayer;
    }

    public void updateBoard() {
        this.remove(boardPanel);
        boardPanel = new BoardPanel(currentPhrase);
        this.add(boardPanel, BorderLayout.NORTH);
        //repaint();
    }

    public Player getCurrentPlayer(String name) {
        for (Player playerEl : playerList) {
            if (playerEl.getName().equals(name)) {
                return playerEl;
            }
        }
        return null;
    }

    @Override
    public void update(Graphics g) {
        if (bufferedImage!=null){
            backGraphic = bufferedImage.getGraphics();
            paintComponent(backGraphic);
            g.drawImage(bufferedImage,0,0,null);
        }
    }
}
