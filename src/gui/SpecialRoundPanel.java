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
    private boolean GUESS = false ;
    private final Image background;
    private BoardPanel boardPanel;
    private ButtonPanel buttonPanel;

    @Override
    public String getPhrase() {
        return phrase;
    }

    private AnswerPanel answerPanel;
    private String currentPhrase = "";
    private String phrase;
    private String question;
    private boolean finished = false;
    private Puzzle puzzle;
    private Player player;
    private int GUESSLEFT = 2;
    private boolean isEnd;
    private boolean guessTrue = false;
    private boolean isWin;

    public boolean isWin() {
        return isWin;
    }

    private TimerPanel timerPanel;
//    private SamPanel samPanel;

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
        timerPanel = new TimerPanel(10);
//        samPanel = new SamPanel();
        this.add(boardPanel);
        this.add(buttonPanel);
//        this.add(samPanel);
        this.add(answerPanel);
        this.add(timerPanel);
        answerPanel.setVisible(false);
        setVisible(true);

    }

    public void getGuess() {
        String buttonPressed = buttonPanel.getButtonPressed();
        if (buttonPressed != "") {
            guessTrue = false;
            GUESSLEFT--;
            int count = 0;
            char c = buttonPressed.charAt(0);
            System.out.println("You guessed: " + c);
            char[] phraseArr = phrase.toCharArray();
            char[] currArr = currentPhrase.toCharArray();
            for (int i = 0; i < phrase.length(); i++) {
                if (Character.toUpperCase(c) == phraseArr[i] && currArr[i] == '_') {
                    currArr[i] = phraseArr[i];
                    guessTrue = true;
                    count++;
                }
            }
            if (guessTrue) {
                currentPhrase = new String(currArr);
                updateBoard();
            }
            System.out.println("Phrase: " + phrase);
            System.out.println("Current phrase: " + currentPhrase);
            buttonPanel.refreshButton();
//            samPanel.sayResult(c, count);
        }
    }

    public void getAnswer() {
        String answer = answerPanel.getAnswer();
        if (answer != "") {
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
        this.add(boardPanel);
        revalidate();
        repaint();
    }

    public boolean isFinished() {
        return finished;
    }

    public void checkWin() {
        if (currentPhrase.equals(phrase)) {
            isWin=true;
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
    }

    public void run() {
        if (!GUESS) {
            if (!timerPanel.run() && GUESSLEFT != 0) {
                getGuess();
                revalidate();
                repaint();
            } else {
                GUESS = true;
                timerPanel = new TimerPanel(30);
            }
        }
        if (GUESS) {
            buttonPanel.setVisible(false);
            answerPanel.setVisible(true);
            if (!timerPanel.run()) {
                getAnswer();
                checkWin();
                revalidate();
                repaint();
            } else {
                finished = true;
            }
            revalidate();
            repaint();
        }
    }
}



