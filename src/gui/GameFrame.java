package gui;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Thaotonto on 3/29/2017.
 */
public class GameFrame extends JFrame {
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 700;
    public static MainPanel mainPanel;

    public GameFrame() throws HeadlessException {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("WHEEL OF FORTUNE");
        mainPanel = new MainPanel();
        add(mainPanel);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setFocusable(true);
//        setAlwaysOnTop(true);
    }
}
