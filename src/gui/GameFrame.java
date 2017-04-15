package gui;

import controller.GameController;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Thaotonto on 3/29/2017.
 */
public class GameFrame extends JFrame {
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 650;
    public static MainPanel mainPanel;
    public static GameController gameController;

    public GameFrame() throws HeadlessException {
//        setSize(GAME_WIDTH, GAME_HEIGHT);
//        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
        setTitle("WHEEL OF FORTUNE");
        mainPanel = new MainPanel();
        add(mainPanel);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
//        setAlwaysOnTop(true);
    }
}
