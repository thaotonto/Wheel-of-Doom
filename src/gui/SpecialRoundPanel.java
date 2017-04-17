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
public class SpecialRoundPanel extends JPanel {
    private boolean GUESS = false;
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
    private boolean isEnd;
    private boolean guessTrue = false;
    private boolean isWin;
    private JPanel playerInfo = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
    private JLabel playerLabel;
    private SamPanel samPanel;
    private boolean first = true;

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
        timerPanel = new TimerPanel(15);
        samPanel = new SamPanel(puzzle.getRound());
        this.add(boardPanel);
        this.add(buttonPanel);
        this.add(samPanel);
        this.add(answerPanel);
        answerPanel.setVisible(false);
        playerInfo.setBounds(200, 550, 600, 100);
        this.add(playerInfo);
        playerInfo.setOpaque(false);
        playerInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        paintPlayerInfo();
        setVisible(true);
        samPanel.notifySpecialRound();
    }

    private void paintPlayerInfo() {
        gbc.insets = new Insets(10, 10, 10, 10);
        playerLabel = new JLabel(player.getName().toString());
        gbc.gridx = 0;
        gbc.gridy = 0;
        playerInfo.add(playerLabel, gbc);
        playerLabel = new JLabel(player.getTotalScore() + "");
        gbc.gridx = 1;
        gbc.gridy = 0;
        playerInfo.add(playerLabel, gbc);
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
            samPanel.sayResultSpecial(c, count);
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
            samPanel.notifyAnswerSpecial(answer, phrase);
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
            isWin = true;
            finished = true;
        }
    }

    private void endGame() {
        finished = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
        drawCenteredString(g, question, new Font("Serif", Font.BOLD, 20));
        if (GUESSLEFT > 0) {
            g.setFont(new Font(null, Font.BOLD, 20));
            g.drawString("Guess left:" + GUESSLEFT, 345, 390);
        }
    }

    public void run() {
        if (!GUESS) {
            if (GUESSLEFT != 0) {
                getGuess();
                if (GUESSLEFT != 0)
                    samPanel.notifyGuessSpecial(GUESSLEFT);
                revalidate();
                repaint();
            } else {
                GUESS = true;
                timerPanel = new TimerPanel(30);
                timerPanel.setBounds(650, 405, 50, 40);
                this.add(timerPanel);
            }
        }
        if (GUESS) {
            buttonPanel.setVisible(false);
            answerPanel.setVisible(true);
            if (first = true) {
                long start = System.currentTimeMillis();
                long end = System.currentTimeMillis();
                while (end - start < 1000) {
                    end = System.currentTimeMillis();
                }
                first = false;
            }
            if (!timerPanel.run()) {
                samPanel.notifyAnswerTimer(timerPanel.getTimer());
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

    public void drawCenteredString(Graphics g, String text, Font font) {

        FontMetrics metrics = g.getFontMetrics(font);
        int x = (GameFrame.GAME_WIDTH - metrics.stringWidth(text)) / 2;
        g.setFont(font);
        g.drawString(text, x, 170);
    }

    public String getPhrase() {
        return phrase;
    }
}



