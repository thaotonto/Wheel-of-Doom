package gui;

import controller.GameController;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Inpriron on 4/7/2017.
 */
public class SummaryPanel extends JPanel {
    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 600;
    private JLabel titleLabel;
    private String phrase;
    private JLabel phraseLabel;
    private JLabel sentenceLabel;
    private JLabel sentenceLabel1;
    private JLabel colName;
    private JLabel colRoundScore;
    private JLabel colTotal;
    private JLabel nextRoundButton;
    private JLabel winnerLabel;

    public SummaryPanel(ArrayList<Player> playerArrayList, String phrase, boolean isBonusRound) {
        this.phrase = phrase;
        setLayout(null);
        int playerWin = 0;
        int maxScore = 0;
        for (int i = 0; i < playerArrayList.size(); i++) {
            if (playerArrayList.get(i).getTotalScore() >= maxScore) {
                playerWin = i;
                maxScore = playerArrayList.get(i).getTotalScore();
            }
        }
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        setBounds(GameFrame.GAME_WIDTH / 2 - PANEL_WIDTH / 2, GameFrame.GAME_HEIGHT / 2 - PANEL_HEIGHT / 2, PANEL_WIDTH, PANEL_HEIGHT);
        setVisible(true);
        if (!isBonusRound)
            winnerLabel = new JLabel("The winner and advance to the special round is: " + playerArrayList.get(playerWin).getName());
        else
            winnerLabel = new JLabel("There is a tie so we have a bonus round");
        winnerLabel.setBounds(40, 400, 400, 20);
        add(winnerLabel);
        titleLabel = new JLabel("ROUND RESULT");
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        titleLabel.setBounds(90, 30, 300, 50);
        add(titleLabel);
        sentenceLabel = new JLabel("The correct answer is: ");
        sentenceLabel.setBounds(40, 100, 200, 20);
        add(sentenceLabel);
        phraseLabel = new JLabel(phrase);
        phraseLabel.setFont(new Font("Arial", Font.BOLD, 15));
        phraseLabel.setBounds(200, 100, 200, 20);
        add(phraseLabel);
        sentenceLabel1 = new JLabel("The total score of each player after this round is:  ");
        sentenceLabel1.setBounds(40, 150, 300, 20);
        add(sentenceLabel1);
        colName = new JLabel("Name");
        colName.setBounds(40, 180, 50, 20);
        add(colName);
        colRoundScore = new JLabel("Round Score");
        colRoundScore.setBounds(120, 180, 100, 20);
        add(colRoundScore);
        colTotal = new JLabel("Total Score");
        colTotal.setBounds(220, 180, 100, 20);
        add(colTotal);
        for (int i = 0; i < playerArrayList.size(); i++) {
            Player player = playerArrayList.get(i);
            JLabel jLabelName = new JLabel(player.getName());
            jLabelName.setBounds(40, 210 + i * 30, 100, 20);
            add(jLabelName);
            JLabel jLabelScore = new JLabel(player.getCurrentScore() + "");
            jLabelScore.setBounds(150, 210 + i * 30, 50, 20);
            add(jLabelScore);
            JLabel jLabelTotal = new JLabel(player.getTotalScore() + "");
            jLabelTotal.setBounds(250, 210 + i * 30, 50, 20);
            add(jLabelTotal);
        }
        if (!isBonusRound)
            nextRoundButton = new JLabel(new ImageIcon("resources/next-0.png"));
        else
            nextRoundButton = new JLabel(new ImageIcon("resources/bonus-0.png"));

        nextRoundButton.setBounds(PANEL_WIDTH/2 - nextRoundButton.getIcon().getIconWidth()/2, 450, nextRoundButton.getIcon().getIconWidth(), nextRoundButton.getIcon().getIconHeight());
        add(nextRoundButton);
        nextRoundButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getSource() == nextRoundButton) {
                    if (!isBonusRound)
                        GameFrame.mainPanel.showSpecialRoundPanel(GameController.specialRound);
                    else
                        GameFrame.mainPanel.showBonusRoundPanel(GameController.bonusRoundPanel);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (isBonusRound){
                    ImageIcon imageIcon = new ImageIcon("resources/bonus-1.png");
                    nextRoundButton.setIcon(imageIcon);
                }
                if (!isBonusRound){
                    ImageIcon imageIcon = new ImageIcon("resources/next-1.png");
                    nextRoundButton.setIcon(imageIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                if (isBonusRound){
                    ImageIcon imageIcon = new ImageIcon("resources/bonus-0.png");
                    nextRoundButton.setIcon(imageIcon);
                }
                if (!isBonusRound){
                    ImageIcon imageIcon = new ImageIcon("resources/next-0.png");
                    nextRoundButton.setIcon(imageIcon);
                }
            }
        });
        setBackground(new Color(214, 232, 255));
    }
}
