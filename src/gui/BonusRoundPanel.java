package gui;

import controller.GameController;
import player.Player;
import puzzle.Puzzle;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Inpriron on 4/17/2017.
 */
public class BonusRoundPanel extends JPanel {
    private JButton button;
    private String prompt;
    private String wheelResult;
    private ArrayList<Player> winnerList;
    private JPanel playerInfo = new JPanel(new GridBagLayout());
    private JLabel playerLabel;
    private GridBagConstraints gbc = new GridBagConstraints();
    private SamPanel samPanel;

    public boolean isFinish() {
        return isFinish;
    }

    private Puzzle puzzle;
    private int winner;
    private boolean isFinish;
    private boolean isGotWinner;
    private Image image = Utils.loadImageFromRes("background.jpg");

    public BonusRoundPanel(Puzzle puzzle, ArrayList<Player> playerArrayList) {
        winnerList = playerArrayList;
        this.puzzle = puzzle;
        setLayout(null);
        button = new JButton("Special round");
        button.setBounds(100, 400, 200, 50);
        add(button);
        button.setVisible(false);
        add(WheelPanel.instance);
        for (int i = 0; i < winnerList.size(); i++) {
            winnerList.get(i).setCurrentScore(0);
        }
        samPanel = new SamPanel(0);
        this.add(samPanel);
        playerInfo.setBounds(200, 550, 600, 100);
        this.add(playerInfo);
        playerInfo.setOpaque(false);
        playerInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        paintPlayerInfo();
    }

    private void paintPlayerInfo() {
        playerInfo.removeAll();
        gbc.insets = new Insets(10, 10, 10, 10);
        for (int i = 0; i < 2; i++) {
            playerLabel = new JLabel(winnerList.get(i).getName().toString());
            gbc.gridx = 0;
            gbc.gridy = i;
            playerInfo.add(playerLabel, gbc);
            playerLabel = new JLabel(winnerList.get(i).getCurrentScore() + "");
            gbc.gridx = 1;
            gbc.gridy = i;
            playerInfo.add(playerLabel, gbc);
        }

        if (winnerList.size() > 2) {
            for (int i = 2; i < winnerList.size(); i++) {
                gbc.insets = new Insets(0, 100, 0, 0);
                playerLabel = new JLabel(winnerList.get(i).getName().toString());
                gbc.gridx = 4;
                gbc.gridy = i - 2;
                playerInfo.add(playerLabel, gbc);
                gbc.insets = new Insets(10, 10, 10, 10);
                playerLabel = new JLabel(winnerList.get(i).getCurrentScore() + "");
                gbc.gridx = 5;
                gbc.gridy = i - 2;
                playerInfo.add(playerLabel, gbc);
            }
        }
        validate();
        repaint();
    }

    public void run() {
        String monitor = WheelPanel.instance.monitor;
        int max = 0;
        if (!isGotWinner) {
            int count = 0;
            for (int i = 0; i < winnerList.size(); i++) {
                samPanel.notifySpin(winnerList.get(i).getName());
                repaint();
                synchronized (monitor) {
                    try {
                        WheelPanel.instance.setPowerBar(true);
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                wheelResult = WheelPanel.instance.getResult();
                winnerList.get(i).setCurrentScore(resultToScore(wheelResult));
                samPanel.notifyBonusRound(wheelResult);
                paintPlayerInfo();
            }

            for (int i = 0; i < winnerList.size(); i++) {
                if (winnerList.get(i).getCurrentScore() > max) {
                    max = winnerList.get(i).getCurrentScore();
                    winner = i;
                }
            }
            for (int i = 0; i < winnerList.size(); i++) {
                if (winnerList.get(i).getCurrentScore() == max) {
                    count++;
                }
            }
            if (count == 1) {
                prompt = "The winner is " + winnerList.get(winner).getName();
                samPanel.notifyWinner(winnerList.get(winner).getName());
                repaint();
                isGotWinner = true;
            } else {
                for (int i = 0; i < winnerList.size(); i++) {
                    winnerList.get(i).setCurrentScore(0);
                }
            }
        } else {
            GameController.specialRound = new SpecialRoundPanel(puzzle, winnerList.get(winner));
            button.setVisible(true);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    GameFrame.mainPanel.showSpecialRoundPanel(GameController.specialRound);
                    isFinish = true;
                }
            });
        }


    }

    private int resultToScore(String result) {
        if (result.equals("bankrupt")) {
            return 0;
        }
        if (result.equals("lose turn")) {
            return 0;
        }
        if (result.equals("get turn"))
            return 0;
        if (result.equals("Prize"))
            return 0;
        return Integer.parseInt(result);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, GameFrame.GAME_WIDTH + 10, GameFrame.GAME_HEIGHT + 10, null);
        graphics.setFont(new Font("Serif", Font.BOLD, 20));
        graphics.drawString("Bonus Round", 450, 100);
    }
}


