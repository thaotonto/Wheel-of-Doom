package gui;

import utils.Utils;
import controller.GameController;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import static gui.MainPanel.TAG_GAME;

/**
 * Created by Thaotonto on 3/30/2017.
 */
public class StartPanel extends JPanel implements MouseListener {
    private JComboBox boxnPlayer;
    private JComboBox boxTheme;
    private Vector<String> theme = Utils.loadFile("theme/theme.txt");
    private String[] numberOfPlayer = {"2", "3", "4"};
    private JLabel jLabel1 = new JLabel("Player1 name: ");
    private JLabel jLabel2 = new JLabel("Player2 name: ");
    private JLabel jLabel3 = new JLabel("Player3 name: ");
    private JLabel jLabel4 = new JLabel("Player4 name: ");
    private JTextField namePlayer1 = new JTextField("Player1", 25);
    private JTextField namePlayer2 = new JTextField("Player2", 25);
    private JTextField namePlayer3 = new JTextField("Player3", 25);
    private JTextField namePlayer4 = new JTextField("Player4", 25);
    private JButton playBtn = new JButton(new ImageIcon("resources/play.png"));
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private ArrayList<Player> playerList = new ArrayList<>();
    private Image background = Utils.loadImageFromRes("background.jpg");
    private JButton backToMenu;
    private ImageIcon imageIcon;

    public StartPanel() {
        setLayout(new BorderLayout());
        initComp();
    }

    private void initComp() {
        playBtn.setOpaque(false);
        playBtn.setContentAreaFilled(false);
        playBtn.setBorderPainted(false);
        jLabel1.setFont(new Font(null, Font.PLAIN, 24));
        jLabel2.setFont(new Font(null, Font.PLAIN, 24));
        jLabel3.setFont(new Font(null, Font.PLAIN, 24));
        jLabel4.setFont(new Font(null, Font.PLAIN, 24));
        playBtn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        boxnPlayer = new JComboBox(numberOfPlayer);
        boxTheme = new JComboBox(theme);
        imageIcon= new ImageIcon("resources/back1.png");
        backToMenu= new JButton(imageIcon);
        backToMenu.setOpaque(false);
        backToMenu.setContentAreaFilled(false);
        backToMenu.setBorderPainted(false);
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.Y_AXIS));

        JPanel jPanel = new JPanel(new FlowLayout());
        JLabel jLabel = new JLabel("Number Of player: ");
        jLabel.setFont(new Font(null, Font.PLAIN, 24));
        jPanel.add(jLabel);
        jPanel.add(boxnPlayer);
        panelNorth.add(jPanel);

        jPanel = new JPanel(new FlowLayout());
        jLabel = new JLabel("Theme: ");
        jLabel.setPreferredSize(new Dimension(100,100));
        jLabel.setFont(new Font(null, Font.PLAIN, 24));
        jPanel.add(jLabel);
        jPanel.add(boxTheme);
        panelNorth.add(jPanel);
        add(panelNorth, BorderLayout.NORTH);

        gridBagConstraints.insets = new Insets(40, 15, 15, 40);
        jPanel = new JPanel(new GridBagLayout());

        //Player1
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel.add(jLabel1, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer1, gridBagConstraints);

        //Player2
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel.add(jLabel2, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer2, gridBagConstraints);

        //Player 3
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel.add(jLabel3, gridBagConstraints);

        namePlayer3.setEnabled(false);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer3, gridBagConstraints);


        //Player4
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel.add(jLabel4, gridBagConstraints);

        namePlayer4.setEnabled(false);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = gridBagConstraints.HORIZONTAL;
        jPanel.add(namePlayer4, gridBagConstraints);


        add(jPanel, BorderLayout.CENTER);
        jPanel = new JPanel(new FlowLayout());
        playBtn.setPreferredSize(new Dimension(200,100));
        backToMenu.setPreferredSize(new Dimension(200,100));
        backToMenu.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        jPanel.add(playBtn);
        jPanel.add(backToMenu);
        playBtn.addMouseListener(this);
        backToMenu.addMouseListener(this);
        add(jPanel, BorderLayout.SOUTH);
        boxnPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (boxnPlayer.getSelectedItem().toString().equals("3")) {
                    namePlayer3.setEnabled(true);
                    namePlayer4.setEnabled(false);
                }
                if (boxnPlayer.getSelectedItem().toString().equals("4")) {
                    namePlayer3.setEnabled(true);
                    namePlayer4.setEnabled(true);
                }
                if (boxnPlayer.getSelectedItem().toString().equals("2")) {
                    namePlayer3.setEnabled(false);
                    namePlayer4.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == playBtn) {
            System.out.println("Player no: " + Integer.parseInt(boxnPlayer.getSelectedItem().toString()));
            switch (boxnPlayer.getSelectedItem().toString()) {
                case "2": {
                    Player player = new Player(namePlayer1.getText());
                    playerList.add(player);
                    player = new Player(namePlayer2.getText());
                    playerList.add(player);
                }
                break;
                case "3": {
                    Player player = new Player(namePlayer1.getText());
                    playerList.add(player);
                    player = new Player(namePlayer2.getText());
                    playerList.add(player);
                    player = new Player(namePlayer3.getText());
                    playerList.add(player);
                }
                break;
                case "4": {
                    Player player = new Player(namePlayer1.getText());
                    playerList.add(player);
                    player = new Player(namePlayer2.getText());
                    playerList.add(player);
                    player = new Player(namePlayer3.getText());
                    playerList.add(player);
                    player = new Player(namePlayer4.getText());
                    playerList.add(player);
                }
                break;
            }
            for (int i = 0; i < Integer.parseInt(boxnPlayer.getSelectedItem().toString()); i++) {
                System.out.println("Player: " + playerList.get(i).getName());
            }
            GameFrame.gameController = new GameController(boxTheme.getSelectedItem().toString(), playerList, Integer.parseInt(boxnPlayer.getSelectedItem().toString()));
        }
        if (e.getSource() == backToMenu) {
            GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
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
