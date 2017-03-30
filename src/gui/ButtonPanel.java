package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Hoang on 3/29/2017.
 */
public class ButtonPanel extends JPanel implements MouseListener {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private ArrayList<JButton> btnList;
    private String buttonPressed = "";

    public ButtonPanel() {
        btnList = new ArrayList<JButton>();
        setLayout(new GridLayout(4, 7, 3, 3));
        setVisible(true);
        for (int i = 65; i < 91; i++) {
            JButton letterButton = new JButton(Character.toString((char) i));
            letterButton.setName(Character.toString((char) i));
            letterButton.addMouseListener(this);
            letterButton.setFocusPainted(false);
            this.add(letterButton);
            btnList.add(letterButton);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicky clacky");
        for (JButton btnEl : btnList) {
            if (e.getSource() == btnEl && btnEl.isEnabled()) {
                System.out.println(btnEl.getName());
                buttonPressed = btnEl.getName();
                btnEl.setEnabled(false);
            }
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

    public String getButtonPressed() {
        return buttonPressed;
    }

    public void refreshButton() {
        buttonPressed = "";
    }
}
