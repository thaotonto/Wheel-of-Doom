package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Created by Inpriron on 4/15/2017.
 */
public class WinGamePanel extends JPanel {
    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 600;
    private Image congratsImage= Utils.loadImageFromRes("gratz.gif");
    private Image youWinImage= Utils.loadImageFromRes("youwin.gif");
    private Image backGround= Utils.loadImageFromRes("plain.jpg");
    private ImageIcon icon;
    private JButton backToMenu;
    public WinGamePanel() {
        setLayout(null);
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        setBounds(GameFrame.GAME_WIDTH / 2 - PANEL_WIDTH / 2, GameFrame.GAME_HEIGHT / 2 - PANEL_HEIGHT / 2, PANEL_WIDTH, PANEL_HEIGHT);
        setVisible(true);
        icon= new ImageIcon("resources/backtomenu.png");
        backToMenu= new JButton(icon);
        backToMenu.setBounds(PANEL_WIDTH/2-icon.getIconWidth()/2,500,icon.getIconWidth(),icon.getIconHeight());
        add(backToMenu);
        backToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(backGround,0,0,PANEL_WIDTH,PANEL_HEIGHT,null);
        graphics.drawImage(congratsImage,40,20,null);
        graphics.drawImage(youWinImage,110,200,null);

    }
}

