package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Thaotonto on 3/29/2017.
 */
public class MenuPanel extends JPanel {
    private JButton startBtn;
    private JButton exitBtn;
    private JButton instructionBtn;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        this.setAlignmentX(CENTER_ALIGNMENT);
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
                }
            }
        };

        ImageIcon icon = new ImageIcon("resources/startGame.png");
        startBtn = new JButton(icon);

        add(startBtn, new GridBagConstraints(0, 0, 0, 0, 0, 0, GridBagConstraints.CENTER, 0, new Insets(5, 5, 5, 5), 0, 0));
        startBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/instruction-0.png");
        instructionBtn = new JButton(icon);


        add(instructionBtn, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 0, 0, 0, 0, GridBagConstraints.CENTER, 0, new Insets(5, 5, 5, 5), 0, 0));
        instructionBtn.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/quitGame.png");
        exitBtn = new JButton(icon);

        add(exitBtn, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 0, 0, 0, 0, GridBagConstraints.CENTER, 0, new Insets(5, 5, 5, 5), 0, 0));
        exitBtn.addMouseListener(mouseAdapter);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = Utils.loadImageFromRes("BG-0.png");
        g.drawImage(image, 0, 0, GameFrame.GAME_WIDTH, GameFrame.GAME_HEIGHT, null);
    }
}
