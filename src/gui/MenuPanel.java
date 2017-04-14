package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Thaotonto on 3/29/2017.
 */
public class MenuPanel extends JPanel {
    private JButton startBtn;
    private JButton exitBtn;
    private JButton instructionBtn;
    private int DISTANCE = 50;
    private JButton createBtn;

    public MenuPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getSource().equals(startBtn)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_START);
                } else if (e.getSource().equals(exitBtn)) {
                    System.exit(0);
                } else if (e.getSource().equals(createBtn)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_CREATE_Q);
                }
            }
        };

        ImageIcon icon = new ImageIcon("resources/create.png");
        createBtn = new JButton(icon);
        createBtn.setBounds(GameFrame.GAME_WIDTH / 2 - icon.getIconWidth() / 2,
                GameFrame.GAME_HEIGHT / 2 - icon.getIconHeight() - DISTANCE * 4,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(createBtn);
        createBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/startGame.png");
        startBtn = new JButton(icon);
        startBtn.setBounds(GameFrame.GAME_WIDTH / 2 - icon.getIconWidth() / 2,
                GameFrame.GAME_HEIGHT / 2 - icon.getIconHeight() - DISTANCE,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(startBtn);
        startBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/instruction-0.png");
        instructionBtn = new JButton(icon);
        instructionBtn.setBounds(GameFrame.GAME_WIDTH / 2 - icon.getIconWidth() / 2,
                GameFrame.GAME_HEIGHT / 2,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(instructionBtn);
        instructionBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/quitGame.png");
        exitBtn = new JButton(icon);
        exitBtn.setBounds(GameFrame.GAME_WIDTH / 2 - icon.getIconWidth() / 2,
                GameFrame.GAME_HEIGHT / 2 + icon.getIconHeight() + DISTANCE,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(exitBtn);
        exitBtn.addMouseListener(mouseAdapter);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = Utils.loadImageFromRes("background.jpg");
        g.drawImage(image, 0, 0, GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT, null);
    }
}
