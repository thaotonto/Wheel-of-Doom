package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

/**
 * Created by Hoang on 3/29/2017.
 */
public class GamePanel extends JPanel {
    private BoardPanel boardPanel;
    private ButtonPanel buttonPanel;
    private String currentPhrase = "";
    private String phrase = "MAY BAY";
    private Thread thread;
    private static int nPlayer;



    public GamePanel() {

        for (int i = 0; i < phrase.length(); i++) {
            char append = phrase.charAt(i);
            if (append == ' ')
                currentPhrase += " ";
            else currentPhrase += "_";
        }

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        boardPanel = new BoardPanel(currentPhrase);
        buttonPanel = new ButtonPanel();

        this.add(boardPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        System.out.println("Phrase setup: " + currentPhrase);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getGuess();
                    revalidate();
                    repaint();
                    if (checkWin()){
                        JLabel wonLabel = new JLabel("YOU WON!");
                        add(wonLabel);
                    }
                }
            }
        });
        thread.start();
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
            this.remove(boardPanel);
            boardPanel = new BoardPanel(currentPhrase);
            this.add(boardPanel, BorderLayout.NORTH);
            buttonPanel.refreshButton();
        }
    }

    public boolean checkWin(){
        if(currentPhrase.equals(phrase)) return true;
        else return false;
    }

    public static int getnPlayer() {
        return nPlayer;
    }

    public static void setnPlayer(int nPlayer) {
        GamePanel.nPlayer = nPlayer;
    }
}
