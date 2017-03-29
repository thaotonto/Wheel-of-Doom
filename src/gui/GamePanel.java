package gui;

import javax.swing.*;

/**
 * Created by Thaotonto on 3/29/2017.
 */
public class GamePanel extends JPanel {
    private static int nPlayer;

    public static int getnPlayer() {
        return nPlayer;
    }

    public static void setnPlayer(int nPlayer) {
        GamePanel.nPlayer = nPlayer;
    }

}
