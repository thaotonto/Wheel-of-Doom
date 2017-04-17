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
public class NextRoundPanel extends JPanel {
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

    public NextRoundPanel(ArrayList<Player> playerArrayList, String phrase) {
        this.phrase = phrase;
//        Player player= new Player("long");
//        playerArrayList.add(player);
//        player= new Player("Hoang");
//        playerArrayList.add(player);
//        player= new Player("Thao");
//        playerArrayList.add(player);
//        phrase="LONDON";
        setLayout(null);
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        setBounds(GameFrame.GAME_WIDTH / 2 - PANEL_WIDTH / 2, GameFrame.GAME_HEIGHT / 2 - PANEL_HEIGHT / 2, PANEL_WIDTH, PANEL_HEIGHT);
        setVisible(true);
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
        ImageIcon imageIcon = new ImageIcon("resources/next-0.png");
        nextRoundButton = new JLabel(imageIcon);
        nextRoundButton.setBounds(122, 400, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(nextRoundButton);
        nextRoundButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getSource() == nextRoundButton) {
                    GameFrame.mainPanel.showGamePanel(GameController.gamePanel);
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
                ImageIcon imageIcon = new ImageIcon("resources/next-1.png");
                nextRoundButton.setIcon(imageIcon);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                ImageIcon imageIcon = new ImageIcon("resources/next-0.png");
                nextRoundButton.setIcon(imageIcon);
            }
        });
    }

}
