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
    private JLabel playBtn = new JLabel(new ImageIcon("resources/play-0.png"));
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private ArrayList<Player> playerList = new ArrayList<>();
    private JLabel backToMenu;
    private ImageIcon imageIcon;
    private Image background = Utils.loadImageFromRes("background.jpg");


    public StartPanel() {
        setLayout(new BorderLayout());
        initComp();
    }

    private void initComp() {
        Font font = new Font(null, Font.PLAIN, 24);
        jLabel1.setFont(font);
        jLabel2.setFont(font);
        jLabel3.setFont(font);
        jLabel4.setFont(font);
        namePlayer1.setFont(font);
        namePlayer2.setFont(font);
        namePlayer3.setFont(font);
        namePlayer4.setFont(font);
        jLabel1.setFont(font);
        jLabel2.setFont(font);
        jLabel3.setFont(font);
        jLabel4.setFont(font);
        namePlayer1.setFont(font);
        namePlayer2.setFont(font);
        namePlayer3.setFont(font);
        namePlayer4.setFont(font);
        playBtn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        boxnPlayer = new JComboBox(numberOfPlayer);
        boxnPlayer.setFont(font);
        boxTheme = new JComboBox(theme);
        boxTheme.setFont(font);
        imageIcon= new ImageIcon("resources/back-0.png");
        backToMenu= new JLabel(imageIcon);

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.Y_AXIS));

        JPanel jPanel = new JPanel(new FlowLayout());
        JLabel jLabel = new JLabel("Number Of player: ");
        jLabel.setFont(new Font(null, Font.PLAIN, 24));
        jPanel.add(jLabel);
        jPanel.add(boxnPlayer);
        panelNorth.add(jPanel);
        jPanel.setOpaque(false);

        jPanel = new JPanel(new FlowLayout());
        jLabel = new JLabel("Theme: ");
        jLabel.setPreferredSize(new Dimension(100,100));
        jLabel.setFont(new Font(null, Font.PLAIN, 24));
        jPanel.add(jLabel);
        jPanel.add(boxTheme);
        panelNorth.add(jPanel);
        jPanel.setOpaque(false);
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.setOpaque(false);

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
        jPanel.setOpaque(false);
        jPanel = new JPanel(new FlowLayout());
        playBtn.setPreferredSize(new Dimension(242,72));
        backToMenu.setPreferredSize(new Dimension(242,72));
        JLabel space = new JLabel();
        space.setPreferredSize(new Dimension(50,72));
        jPanel.add(playBtn);
        space.setOpaque(false);
        jPanel.add(space);
        jPanel.add(backToMenu);
        playBtn.addMouseListener(this);
        backToMenu.addMouseListener(this);
        add(jPanel, BorderLayout.SOUTH);
        jPanel.setOpaque(false);
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
        g.drawImage(background,0,0, GameFrame.GAME_WIDTH + 10, GameFrame.GAME_HEIGHT + 10,null);
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
        if (e.getSource().equals(playBtn)) {
            ImageIcon imageIcon = new ImageIcon("resources/play-1.png");
            playBtn.setIcon(imageIcon);
        } else if (e.getSource().equals(backToMenu)) {
            ImageIcon imageIcon = new ImageIcon("resources/back-1.png");
            backToMenu.setIcon(imageIcon);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(playBtn)) {
            ImageIcon imageIcon = new ImageIcon("resources/play-0.png");
            playBtn.setIcon(imageIcon);
        } else if (e.getSource().equals(backToMenu)) {
            ImageIcon imageIcon = new ImageIcon("resources/back-0.png");
            backToMenu.setIcon(imageIcon);
        }
    }
}
