package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hoang on 4/18/2017.
 */
public class ForTheScience extends JPanel{
    private Image background = Utils.loadImageFromRes("laivansam.png");;

    public ForTheScience() {
        this.setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        setLayout(null);
        repaint();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(background,0,0,GameFrame.GAME_WIDTH + 10, GameFrame.GAME_HEIGHT + 10,null);
    }
}
