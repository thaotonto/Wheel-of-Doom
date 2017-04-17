package gui;


import controller.GameController;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hoang on 4/8/2017.
 */
public class SamPanel extends JPanel {
    private JLabel lvsLabel;
    private JLabel bubbleLabel;
    private ImageIcon lvs;
    private ImageIcon bubble;
    private String info;

    public SamPanel(int round) {
        if (round == 1) {
            info = "Welcome to<br>Wheel of Fortune";
        } else {
            if (round == 0) {
                info = "Welcome to<br>Bonus Round";
            } else info = "Welcome to<br>Round " + round;
        }
        setVisible(true);
        setLayout(null);
        setOpaque(false);
        this.setBounds(100, 200, 380, 200);

        lvs = new ImageIcon(Utils.loadImageFromRes("laivansam.png"));
        bubble = new ImageIcon(Utils.loadImageFromRes("bubble.png"));

        lvsLabel = new JLabel(lvs);
        bubbleLabel = new JLabel(bubble);
        bubbleLabel.setHorizontalTextPosition(JLabel.CENTER);
        bubbleLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        bubbleLabel.setText(info);

        add(lvsLabel);
        add(bubbleLabel);

        lvsLabel.setBounds(10, 40, lvs.getIconWidth(), lvs.getIconHeight());
        bubbleLabel.setBounds(160, 5, bubble.getIconWidth(), bubble.getIconHeight());
    }

    public void sayResult(char letter, int letterNo) {
        if (letterNo == 0) info = "There is no " + letter + "<br>" + "Next player";
        else if (letterNo == 1) info = "There is 1 " + letter;
        else info = "There are " + letterNo + " " + letter + "s";
        repaint();
    }

    public void sayResultSpecial(char letter, int letterNo) {
        if (letterNo == 0) info = "There is no " + letter;
        else if (letterNo == 1) info = "There is 1 " + letter;
        else info = "There are " + letterNo + " " + letter + "s";
        repaint();
    }

    public void notifySpin(String currentPlayerName) {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        while (end - start < 1000) {
            end = System.currentTimeMillis();
        }
        info = currentPlayerName + "<br>Please spin the wheel";
        repaint();
    }

    public void notifyGuess(String wheelResult) {
        if (wheelResult == "lose turn")
            info = "You lost the turn<br>Next player";
        else if (wheelResult == "bankrupt")
            info = "You lost all points<br> and your turn<br>Next player";
        else info = "You landed on " + wheelResult + "<br>Please guess a letter";
        repaint();
    }

    public void notifyBonusRound(String wheelResult) {
        if (wheelResult == "lose turn")
            info = "You only got<br> 20 point";
        else if (wheelResult == "bankrupt")
            info = "You only got<br> 10 point";
        else if (wheelResult == "get turn")
            info = "You only got<br> 30 point";
        else if (wheelResult == "Prize")
            info = "You got<br> 1200 point";
        else info = "You got<br>" + wheelResult + " point";
        repaint();
    }

    public void notifySpinning() {
        info = "The wheel is spinning";
        bubbleLabel.setText(info);
        repaint();
    }

    public void notifyTime() {
        info = "Time's up. Next player";
        bubbleLabel.setText(info);
        repaint();
    }

    public void notifySpecialRound() {
        info = "This is the special round<br>Please guess two times";
        bubbleLabel.setText(info);
        repaint();
    }

    public void notifyAnswer(String answer, String phrase) {
        if (answer.equals(phrase))
            info = "You are correct<br>You've won this round";
        else info = "You are wrong<br>You've been banned";
        repaint();
    }

    public void notifyAnswerSpecial(String answer, String phrase){
        if (answer.equals(phrase))
            info = "You are correct<br>You've won the game";
        else info = "You are wrong<br>Try again";
        repaint();
    }

    public void notifyOptions() {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        while (end - start < 1000) {
            end = System.currentTimeMillis();
        }
        info = "Please answer <br>or spin the wheel";
        WheelPanel.instance.setPowerBar(true);
        repaint();
    }

    public void notifyGuessSpecial(int guessLeft) {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        while (end - start < 1000) {
            end = System.currentTimeMillis();
        }
        if (guessLeft == 2)
            info = "You can guess 2 times";
        else info = "You have " + guessLeft + " guess left";
        repaint();
    }

    public void notifyAnswerTimer(int timer) {
        info = "You have " + timer + "s<br>to guess the answer";
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bubbleLabel.setText("<html>" + info + "</html>");
    }

    public void notifyWinner(String name) {
        info = "The winner is<br>" + name;
    }
}
