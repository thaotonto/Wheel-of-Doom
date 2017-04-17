package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Inpriron on 4/17/2017.
 */
public class HolderPanel extends JPanel {
    private Image backGroundImage = Utils.loadImageFromRes("background.jpg");

    public HolderPanel() {
        this.setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        setLayout(null);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(backGroundImage,0,0, GameFrame.GAME_WIDTH + 10, GameFrame.GAME_HEIGHT + 10,null);
    }
}
