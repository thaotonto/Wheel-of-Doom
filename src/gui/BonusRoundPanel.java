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
    private ArrayList<Player> winnerList;

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
        button.setBounds(100, 400, 100, 50);
        add(button);
        button.setVisible(false);
        add(WheelPanel.instance);
        for (int i = 0; i < winnerList.size(); i++) {
            winnerList.get(i).setCurrentScore(0);
        }

    }

    public void run() {
        String monitor = WheelPanel.instance.monitor;
        int max = 0;
        if (!isGotWinner) {
            int count = 0;
            for (int i = 0; i < winnerList.size(); i++) {
                prompt = "Please spin " + winnerList.get(i).getName();
                repaint();
                synchronized (monitor) {
                    try {
                        WheelPanel.instance.setPowerBar(true);
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                winnerList.get(i).setCurrentScore(resultToScore(WheelPanel.instance.getResult()));
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
                prompt = "the winner is " + winnerList.get(winner).getName();
                repaint();
                isGotWinner = true;
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
            return 10;
        }
        if (result.equals("lose turn")) {
            return 20;
        }
        if (result.equals("get turn"))
            return 30;
        if (result.equals("prize"))
            return 1200;
        return Integer.parseInt(result);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT, null);
        if (prompt != null) {
            graphics.drawString(prompt, 100, 100);
        }
    }
}


