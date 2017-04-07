package gui;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thaotonto on 3/29/2017.
 */
public class MainPanel extends JPanel {
    public static final String TAG_MENU = "tag_menu";
    public static final String TAG_START = "tag_start";
    public static final String TAG_INSTRUCTION = "tag_instruction";
    public static final String TAG_GAME = "tag_game";
    public static final String TAG_GAME_OVER = "tag_game_over";
    public static final String TAG_CREATE_Q = "tag_create_q";
    public static final String TAG_NEXT_ROUND = "tag_next_round";
    private CardLayout cardLayout;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private StartPanel startPanel;
    private JPanel createPuzzlePanelHolder;
    private JPanel nextRoundPanelHolder;
//    private GameOverPanel gameOverPanel;

    public MainPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menuPanel = new MenuPanel();
        add(menuPanel, TAG_MENU);
        setSize(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT);
        cardLayout.show(this, TAG_MENU);
        setVisible(true);
    }


    public void showPanel(String tag) {
        if (tag.equals(TAG_GAME_OVER)) {
//            gameOverPanel = new GameOverPanel();
//            add(gameOverPanel, TAG_GAME_OVER);
//            cardLayout.show(this, tag);
        } else if (tag.equals(TAG_START)) {
            startPanel = new StartPanel();
            add(startPanel, TAG_START);
            cardLayout.show(this, tag);
        } else if (tag.equals(TAG_CREATE_Q)) {
            createPuzzlePanelHolder=new JPanel();
            createPuzzlePanelHolder.setLayout(null);
            createPuzzlePanelHolder.add(new NewPuzzlePanel());
            add(createPuzzlePanelHolder,TAG_CREATE_Q);
            cardLayout.show(this,tag);
        } else {
            cardLayout.show(this, tag);
        }
    }

    public void showGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        add(gamePanel, TAG_GAME);
        cardLayout.show(this, TAG_GAME);
    }
    public void showNextRoundPanel()
    {
        nextRoundPanelHolder= new JPanel();
        nextRoundPanelHolder.setLayout(null);
        nextRoundPanelHolder.add(new NextRoundPanel(GameController.gamePanel.getPlayerList(),GameController.gamePanel.getPhrase()));
        add(nextRoundPanelHolder,TAG_NEXT_ROUND);
        cardLayout.show(this,TAG_NEXT_ROUND);
    }

    public void showSummaryPanel() {
        nextRoundPanelHolder= new JPanel();
        nextRoundPanelHolder.setLayout(null);
        nextRoundPanelHolder.add(new SummaryPanel(GameController.gamePanel.getPlayerList(),GameController.gamePanel.getPhrase()));
        add(nextRoundPanelHolder,TAG_NEXT_ROUND);
        cardLayout.show(this,TAG_NEXT_ROUND);
    }
}
