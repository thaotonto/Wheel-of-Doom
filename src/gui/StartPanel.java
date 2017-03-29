package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Thaotonto on 3/30/2017.
 */
public class StartPanel extends JPanel {
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
        jLabel = new JLabel("Number Of Player: ");
        jPanel.add(jLabel);
        jPanel.add(box);
        add(jPanel, BorderLayout.NORTH);
        gridBagConstraints.insets = new Insets(40, 15, 15, 40);


        jPanel = new JPanel(new GridBagLayout());
        jLabel = new JLabel("Player1 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer1 = new JTextField(25);
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

        jLabel = new JLabel("Player2 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer2 = new JTextField(25);
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

        jLabel = new JLabel("Player3 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer3 = new JTextField(25);
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

        jLabel = new JLabel("Player4 name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel.add(jLabel, gridBagConstraints);
        namePlayer4 = new JTextField(25);
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

        add(jPanel, BorderLayout.CENTER);
        jPanel = new JPanel(new FlowLayout());
        jPanel.add(playBtn);
        add(jPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawString("Number Of Player: ", GameFrame.GAME_WIDTH / 2 - 200, 20);
    }
}
