package gui;

import org.omg.CORBA.CODESET_INCOMPATIBLE;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Thaotonto on 3/30/2017.
 */
public class InstructionPanel extends JPanel implements MouseListener{
    private Image background = Utils.loadImageFromRes("background.jpg");;
    private JTextArea jTextArea;
    private JScrollPane jScrollPane;
    private JLabel backToMenuBtn;

    public InstructionPanel() {
//        setBackground(new Color(214, 232, 255));
        setVisible(true);
        setLayout(null);
        setBounds(0,0,1000,650);
        ImageIcon imageIcon = new ImageIcon("resources/cancel-0.png");
        backToMenuBtn = new JLabel(imageIcon);
        backToMenuBtn.setBounds(1000/2 - imageIcon.getIconWidth()/2,600,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        backToMenuBtn.addMouseListener(this);
        add(backToMenuBtn);
        jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        jTextArea.setFont(new Font(null, Font.PLAIN,15));
        jTextArea.setBackground(Color.WHITE);
        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBackground(Color.WHITE);
        jScrollPane.setBounds(10,10,990,550);
        add(jScrollPane);
        File instruction = new File("resources/instruction.txt");
        try {
            Scanner scanner = new Scanner(instruction);
            while(scanner.hasNextLine()){
                jTextArea.append("\n");
                jTextArea.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        jTextArea.setCaretPosition(0);
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);
        graphics.drawImage(background, 0, 0, GameFrame.GAME_WIDTH + 10, GameFrame.GAME_HEIGHT + 10, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(backToMenuBtn)){
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
        if (e.getSource().equals(backToMenuBtn)) {
            ImageIcon imageIcon = new ImageIcon("resources/cancel-1.png");
            backToMenuBtn.setIcon(imageIcon);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(backToMenuBtn)) {
            ImageIcon imageIcon = new ImageIcon("resources/cancel-0.png");
            backToMenuBtn.setIcon(imageIcon);
        }
    }
}
