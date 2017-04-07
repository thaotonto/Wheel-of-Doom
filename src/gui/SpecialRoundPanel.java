package gui;

import player.Player;
import puzzle.Puzzle;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by Thaotonto on 4/7/2017.
 */
public class SpecialRoundPanel extends GamePanel {
    private final Image background;
    private BoardPanel boardPanel;
    private ButtonPanel buttonPanel;
    private AnswerPanel answerPanel;
    private String currentPhrase = "";
    private String phrase;
    private String question;
    private boolean finished = false;
    private Puzzle puzzle;
    private Player player;
    private int GUESSLEFT = 2;
    private int ANSWERLEFT = 5;
    private boolean isEnd;

    public SpecialRoundPanel(Puzzle puzzle, Player player) {
        super();
        setLayout(null);
        setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        background = Utils.loadImageFromRes("background.jpg");
        this.player = player;
        this.puzzle = puzzle;
        this.question = puzzle.getQuestion();
        this.phrase = puzzle.getPhrase().toUpperCase();
        for (int i = 0; i < phrase.length(); i++) {
            char append = phrase.charAt(i);
            if (append == ' ')
                currentPhrase += " ";
            else currentPhrase += "_";
        }
        boardPanel = new BoardPanel(currentPhrase, puzzle.getRound());
        buttonPanel = new ButtonPanel();
        answerPanel = new AnswerPanel();
        this.add(boardPanel);
        this.add(buttonPanel);
        this.add(answerPanel);
        setVisible(true);
    }

    public void getGuess() {
        String buttonPressed = buttonPanel.getButtonPressed();
        if (buttonPressed != "") {
            GUESSLEFT--;
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
            ANSWERLEFT--;
            if (answer.equals(phrase)) {
                currentPhrase = phrase;
                answerPanel.refreshAnswer();
                updateBoard();
            } else {
                answerPanel.refreshAnswer();
                repaint();
            }
        }
    }

    public void updateBoard() {
        this.remove(boardPanel);
        boardPanel = new BoardPanel(currentPhrase, puzzle.getRound());
        this.add(boardPanel, BorderLayout.NORTH);
    }

    public boolean isFinished() {
        return finished;
    }

    public void checkWin() {
        if (currentPhrase.equals(phrase)) {
            finished = true;
        }
    }

    private void endGame() {
        finished = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawString(question, 200, 200);
        g.drawString("Special Round ", 450, 50);
    }

    public void run() {
        while (GUESSLEFT != 0) {
            getGuess();
            revalidate();
            repaint();
        }
        if (GUESSLEFT == 0) {
            buttonPanel.setVisible(false);
        }
        if (ANSWERLEFT != 0) {
            getAnswer();
            checkWin();
        } else finished = true;
        revalidate();
        repaint();
    }

}

