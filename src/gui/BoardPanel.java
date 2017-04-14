package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hoang on 3/29/2017.
 */
public class BoardPanel extends JPanel {

    private String round = "ROUND";

    public BoardPanel(String phrase, int roundNo) {

        this.setBounds(150, 40, 697, 103);
//        System.out.println("Board phrase: " + phrase);
        FlowLayout flowLayout = new FlowLayout(3, 3, 3);
        setLayout(flowLayout);
        setVisible(true);
        setBackground(Color.BLACK);
        showRound(round + " " + roundNo);
        if (phrase.length() < 22) {
            showWord(phrase);
            showWord(" ");
        } else {
            String[] splitPhrase = phrase.split(" ");
            for (String stringEl : splitPhrase) {
                showWord(stringEl);
            }
        }
    }

    public void showWord(String word) {
        int length = 1;
        while (length < 22) {
            if (length == (22 - word.length()) / 2 + 1) {
                for (char c : word.toCharArray()) {
                    if (c != '_' && c != ' ') {
//                        System.out.println("Board char: " + c);
                        JPanel letterPanel = new JPanel();
                        letterPanel.setPreferredSize(new Dimension(30, 30));
                        letterPanel.setBackground(Color.WHITE);
                        letterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        JLabel letterLabel = new JLabel(Character.toString(c));
                        letterLabel.setForeground(Color.BLACK);
                        letterPanel.add(letterLabel);
                        this.add(letterPanel);
                    } else {
                        JPanel letterPanel = new JPanel();
                        letterPanel.setPreferredSize(new Dimension(30, 30));
                        if (c == ' ') letterPanel.setBackground(Color.GREEN);
                        else letterPanel.setBackground(Color.CYAN);
                        letterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        this.add(letterPanel);
                    }
                    length++;
                }
            } else {
                JPanel letterPanel = new JPanel();
                letterPanel.setPreferredSize(new Dimension(30, 30));
                letterPanel.setBackground(Color.GREEN);
                letterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.add(letterPanel);
                length++;
            }
        }
    }

    public void showRound(String word) {
        int length = 1;
        while (length < 22) {
            if (length == (22 - word.length()) / 2 + 1) {
                for (char c : word.toCharArray()) {
                    if (c!=' ') {
//                        System.out.println("Board char: " + c);
                        JPanel letterPanel = new JPanel();
                        letterPanel.setPreferredSize(new Dimension(30, 30));
                        letterPanel.setBackground(Color.WHITE);
                        letterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        JLabel letterLabel = new JLabel(Character.toString(c));
                        letterLabel.setForeground(Color.BLACK);
                        letterPanel.add(letterLabel);
                        this.add(letterPanel);
                    }
                    else{
                        JPanel letterPanel = new JPanel();
                        letterPanel.setPreferredSize(new Dimension(30, 30));
                        letterPanel.setBackground(Color.CYAN);
                        letterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        this.add(letterPanel);
                    }
                    length++;
                }
            } else {
                JPanel letterPanel = new JPanel();
                letterPanel.setPreferredSize(new Dimension(30, 30));
                letterPanel.setBackground(Color.CYAN);
                letterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.add(letterPanel);
                length++;
            }
        }
    }
}
