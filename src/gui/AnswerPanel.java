package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hoang on 3/30/2017.
 */
public class AnswerPanel extends JPanel implements MouseListener {
    private JTextField answerField;
    private JLabel answerLabel;
    private JButton answerButton;
    private String answer = "";

    public AnswerPanel() {
        setOpaque(false);
        this.setBounds(150, 400, 500, 40);
        setVisible(true);
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);
        answerField = new JTextField(22);
        answerButton = new JButton("CONFIRM");
        answerButton.setFocusPainted(false);
        answerLabel = new JLabel("YOUR ANSWER:");
        answerButton.addMouseListener(this);
        add(answerLabel);
        add(answerField);
        add(answerButton);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == answerButton) {
            System.out.println(answerField.getText());
            answer = answerField.getText().toUpperCase();
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

    public String getAnswer() {
        return answer;
    }

    public void refreshAnswer() {
        answer = "";
        answerField.setText("");
    }
}
