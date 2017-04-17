package gui;

import controller.GameController;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Inpriron on 4/15/2017.
 */
public class LoseGamePanel extends JPanel {
    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 600;
    private Image timesUpImage= Utils.loadImageFromRes("timesup.gif");
    private Image youLoseImage= Utils.loadImageFromRes("gameover.gif");
    private Image backGround= Utils.loadImageFromRes("plain.jpg");
    private ImageIcon icon;
    private JLabel backToMenu;

    private String correctAnswer;
    public LoseGamePanel() {
        correctAnswer="The correct answer is: " + GameController.specialRound.getPhrase();
        setLayout(null);
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        setBounds(GameFrame.GAME_WIDTH / 2 - PANEL_WIDTH / 2, GameFrame.GAME_HEIGHT / 2 - PANEL_HEIGHT / 2, PANEL_WIDTH, PANEL_HEIGHT);
        setVisible(true);
        icon= new ImageIcon("resources/cancel-0.png");
        backToMenu= new JLabel(icon);
        backToMenu.setBounds(PANEL_WIDTH/2-icon.getIconWidth()/2,500,icon.getIconWidth(),icon.getIconHeight());
        add(backToMenu);
        backToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                icon = new ImageIcon("resources/cancel-1.png");
                backToMenu.setIcon(icon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                icon = new ImageIcon("resources/cancel-0.png");
                backToMenu.setIcon(icon);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(backGround,0,0,PANEL_WIDTH,PANEL_HEIGHT,null);
        graphics.drawImage(timesUpImage,40,20,null);
        graphics.drawImage(youLoseImage,70,200,null);
        graphics.setFont(new Font(null, Font.BOLD, 20));
        graphics.setColor(Color.YELLOW);
        graphics.drawString(correctAnswer,70,420);
    }
}
