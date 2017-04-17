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
    private JLabel startBtn;
    private JLabel exitBtn;
    private JLabel instructionBtn;
    private int DISTANCE = 40;
    private JLabel createBtn;
    private Image background;

    public MenuPanel() {
        background = Utils.loadImageFromRes("bg_menu.png");
        setLayout(null);
        initComp();
        setPreferredSize(new Dimension(GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT));
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

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (e.getSource().equals(startBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/start-1.png");
                    startBtn.setIcon(imageIcon);
                } else if (e.getSource().equals(exitBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/quit-1.png");
                    exitBtn.setIcon(imageIcon);
                } else if (e.getSource().equals(createBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/add-1.png");
                    createBtn.setIcon(imageIcon);
                } else if (e.getSource().equals(instructionBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/instruction-1.png");
                    instructionBtn.setIcon(imageIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (e.getSource().equals(startBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/start-0.png");
                    startBtn.setIcon(imageIcon);
                } else if (e.getSource().equals(exitBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/quit-0.png");
                    exitBtn.setIcon(imageIcon);
                } else if (e.getSource().equals(createBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/add-0.png");
                    createBtn.setIcon(imageIcon);
                } else if (e.getSource().equals(instructionBtn)) {
                    ImageIcon imageIcon = new ImageIcon("resources/instruction-0.png");
                    instructionBtn.setIcon(imageIcon);
                }
            }
        };

        ImageIcon icon = new ImageIcon("resources/add-0.png");
        createBtn = new JLabel(icon);
        createBtn.setBounds(GameFrame.GAME_WIDTH / 4 - icon.getIconWidth() / 2 - DISTANCE,
                GameFrame.GAME_HEIGHT / 2 + icon.getIconHeight() + DISTANCE * 4,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(createBtn);
        createBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/start-0.png");
        startBtn = new JLabel(icon);
        startBtn.setBounds(GameFrame.GAME_WIDTH / 4 - icon.getIconWidth() / 2 - DISTANCE,
                GameFrame.GAME_HEIGHT / 2 + DISTANCE * 4 - 30,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(startBtn);
        startBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/instruction-0.png");
        instructionBtn = new JLabel(icon);
        instructionBtn.setBounds(GameFrame.GAME_WIDTH / 2 + DISTANCE * 4,
                GameFrame.GAME_HEIGHT / 2 + DISTANCE * 4 - 30,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(instructionBtn);
        instructionBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/quit-0.png");
        exitBtn = new JLabel(icon);
        exitBtn.setBounds(GameFrame.GAME_WIDTH / 2 + DISTANCE * 4,
                GameFrame.GAME_HEIGHT / 2 + icon.getIconHeight() + DISTANCE * 4,
                icon.getIconWidth(),
                icon.getIconHeight());
        add(exitBtn);
        exitBtn.addMouseListener(mouseAdapter);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, GameFrame.GAME_WIDTH + 10, GameFrame.GAME_HEIGHT + 10, null);
    }
}
