package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainPanel.TAG_GAME;

/**
 * Created by Thaotonto on 3/30/2017.
 */
public class StartPanel extends JPanel implements MouseListener {
    private JComboBox box;
    public static String[] numberOfPlayer = {"2", "3", "4"};
    private JLabel jLabel;
    private JTextField namePlayer1;
    private JTextField namePlayer2;
    private JTextField namePlayer3;
    private JTextField namePlayer4;
    private JButton okBtn1 = new JButton("OK");
    private JButton okBtn2 = new JButton("OK");
    private JButton okBtn3 = new JButton("OK");
    private JButton okBtn4 = new JButton("OK");
    private JButton playBtn = new JButton("Play");
    private String input;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public StartPanel() {
        setLayout(new BorderLayout());
        initComp();
    }

    private void initComp() {
        box = new JComboBox(numberOfPlayer);
        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    GamePanel.setnPlayer(box.getSelectedIndex() + 2);
                }
            }
        });
        JPanel jPanel = new JPanel(new FlowLayout());
        jLabel = new JLabel("Number Of player: ");
        jPanel.add(jLabel);
        jPanel.add(box);
        add(jPanel, BorderLayout.NORTH);
        gridBagConstraints.insets = new Insets(40, 15, 15, 40);


        jPanel = new JPanel(new GridBagLayout());
        jLabel = new JLabel("Player1 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer1 = new JTextField("Player1", 25);
        GamePanel.getPlayerName().add(0, namePlayer1.getText());
        namePlayer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = namePlayer1.getText();
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer1, gridBagConstraints);
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jPanel.add(okBtn1, gridBagConstraints);
        okBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel.getPlayerName().add(0, namePlayer1.getText());
            }
        });

        jLabel = new JLabel("Player2 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer2 = new JTextField("Player2", 25);
        GamePanel.getPlayerName().add(1, namePlayer2.getText());
        namePlayer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = namePlayer2.getText();
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer2, gridBagConstraints);
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        jPanel.add(okBtn2, gridBagConstraints);
        okBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel.getPlayerName().add(1, namePlayer2.getText());
            }
        });

        jLabel = new JLabel("Player3 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer3 = new JTextField("Player3", 25);
        GamePanel.getPlayerName().add(2, namePlayer3.getText());
        namePlayer3.setEnabled(false);
        namePlayer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = namePlayer3.getText();
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer3, gridBagConstraints);
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        jPanel.add(okBtn3, gridBagConstraints);
        okBtn3.setEnabled(false);
        okBtn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel.getPlayerName().add(2, namePlayer3.getText());
            }
        });

        jLabel = new JLabel("Player4 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer4 = new JTextField("player4", 25);
        GamePanel.getPlayerName().add(4, namePlayer4.getText());
        namePlayer4.setEnabled(false);
        namePlayer4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = namePlayer4.getText();
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer4, gridBagConstraints);
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 0;
        jPanel.add(okBtn4, gridBagConstraints);
        okBtn4.setEnabled(false);
        okBtn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel.getPlayerName().add(3,namePlayer4.getText());
            }
        });

        add(jPanel, BorderLayout.CENTER);
        jPanel = new JPanel(new FlowLayout());
        jPanel.add(playBtn);
        playBtn.addMouseListener(this);
        add(jPanel, BorderLayout.SOUTH);
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (box.getSelectedItem().toString().equals("3")) {
                    namePlayer3.setEnabled(true);
                    okBtn3.setEnabled(true);
                    namePlayer4.setEnabled(false);
                    okBtn4.setEnabled(false);
                }
                if (box.getSelectedItem().toString().equals("4")) {
                    namePlayer3.setEnabled(true);
                    okBtn3.setEnabled(true);
                    namePlayer4.setEnabled(true);
                    okBtn4.setEnabled(true);
                }
                if (box.getSelectedItem().toString().equals("2")) {
                    namePlayer3.setEnabled(false);
                    okBtn3.setEnabled(false);
                    namePlayer4.setEnabled(false);
                    okBtn4.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawString("Number Of player: ", GameFrame.GAME_WIDTH / 2 - 200, 20);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == playBtn) {
            GameFrame.mainPanel.showPanel(TAG_GAME);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
