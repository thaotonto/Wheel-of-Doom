package gui;

import controller.GameController;
import utils.Utils;

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
    public static final String TAG_GAME_WIN= "tag_game_win";
    public static final String TAG_GAME_LOSE= "tag_game_lose";
    public static final String TAG_BONUS= "tag_bonus";
    public static final String TAG_SPECIAL= "tag_special";
    public static final String TAG_SUMMARY= "tag_summary";
    private CardLayout cardLayout;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private StartPanel startPanel;
    private InstructionPanel instructionPanel;
    private Image backGroundImage = Utils.loadImageFromRes("background.jpg");
    private HolderPanel holderPanel;
    private ForTheScience forTheScience;

    public MainPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menuPanel = new MenuPanel();
        add(menuPanel, TAG_MENU);
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
            holderPanel = new HolderPanel();
            holderPanel.add(new NewPuzzlePanel());
            add(holderPanel, TAG_CREATE_Q);
            cardLayout.show(this, tag);
        }
        else if (tag.equals(TAG_INSTRUCTION)){
//            instructionPanel = new InstructionPanel();
//            holderPanel= new HolderPanel();
//            holderPanel.add(instructionPanel);
//            holderPanel.add(instructionPanel);
//            add(holderPanel,TAG_INSTRUCTION);
//            cardLayout.show(this,tag);
            forTheScience= new ForTheScience();
            add(forTheScience,TAG_INSTRUCTION);
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

    public void showSpecialRoundPanel(SpecialRoundPanel gamePanel) {
        add(gamePanel, TAG_SPECIAL);
        cardLayout.show(this, TAG_SPECIAL);
    }
    public void showNextRoundPanel() {
        holderPanel= new HolderPanel();
        holderPanel.add(new NextRoundPanel(GameController.gamePanel.getPlayerList(), GameController.gamePanel.getPhrase()));
        add(holderPanel, TAG_NEXT_ROUND);
        cardLayout.show(this, TAG_NEXT_ROUND);
    }

    public void showSummaryPanel(boolean isBonusRound) {
        holderPanel= new HolderPanel();
        holderPanel.add(new SummaryPanel(GameController.gamePanel.getPlayerList(), GameController.gamePanel.getPhrase(),isBonusRound));
        add(holderPanel, TAG_SUMMARY);
        cardLayout.show(this, TAG_SUMMARY);
    }
    public void showGameWinPanel(){
        holderPanel= new HolderPanel();
        holderPanel.add(new WinGamePanel());
        add(holderPanel, TAG_GAME_WIN);
        cardLayout.show(this, TAG_GAME_WIN);
    }
    public void showGameLosePanel() {
        holderPanel= new HolderPanel();
        holderPanel.add(new LoseGamePanel());
        add(holderPanel, TAG_GAME_LOSE);
        cardLayout.show(this, TAG_GAME_LOSE);
    }
    public void showBonusRoundPanel(BonusRoundPanel bonusRoundPanel)
    {
        add(bonusRoundPanel, TAG_BONUS);
        cardLayout.show(this, TAG_BONUS);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(backGroundImage,0,0,GameFrame.GAME_WIDTH + 10, GameFrame.GAME_HEIGHT + 10, null);
    }
}
