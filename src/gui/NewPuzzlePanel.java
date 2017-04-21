package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * Created by Inpriron on 4/5/2017.
 */
public class NewPuzzlePanel extends JPanel {
    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 600;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JTextArea questionArea;
    private JTextField answerField;
    private JComboBox themeCombo;
    private JLabel themeLabel;
    private JLabel newThemeLabel;
    private JTextField newThemeField;
    private JLabel addPuzzleButton;
    private JLabel cancelPuzzleButton;
    private String questionString;
    private String answerString;
    private String newThemeString;

    public NewPuzzlePanel() {
        setLayout(null);
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        setBounds(GameFrame.GAME_WIDTH / 2 - PANEL_WIDTH / 2, GameFrame.GAME_HEIGHT / 2 - PANEL_HEIGHT / 2, PANEL_WIDTH, PANEL_HEIGHT);
        setVisible(true);
        setBackground(new Color(214, 232, 255));
        createQuestionLabelAndArea();
        createAnswerLabelAndText();
        createComBoBox();
        createNewThemeField();
        createAddAndCancelPuzzleButton();
        addPuzzleButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getSource() == addPuzzleButton) {
                    questionString = questionArea.getText();
                    answerString = answerField.getText();
                    if (newThemeField.isEditable()) {
                        createAndWriteFile();
                    } else {
                        writeToAnExistentFile();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                ImageIcon imageIcon = new ImageIcon("resources/create-1.png");
                addPuzzleButton.setIcon(imageIcon);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                ImageIcon imageIcon = new ImageIcon("resources/create-0.png");
                addPuzzleButton.setIcon(imageIcon);
            }
        });
        cancelPuzzleButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getSource() == cancelPuzzleButton) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                ImageIcon imageIcon = new ImageIcon("resources/cancel-1.png");
                cancelPuzzleButton.setIcon(imageIcon);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                ImageIcon imageIcon = new ImageIcon("resources/cancel-0.png");
                cancelPuzzleButton.setIcon(imageIcon);
            }
        });
    }

    private void createAndWriteFile() {
        newThemeString = newThemeField.getText();
        if (questionString.isEmpty() || answerString.isEmpty() || newThemeString.isEmpty())
            JOptionPane.showConfirmDialog(null, "Please insert in all fields",
                    "Warning", JOptionPane.DEFAULT_OPTION);
        else {
            if (answerString.length() > 22) {
                JOptionPane.showMessageDialog(null, "Answer is too long limit is 22 characters", "warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String content = System.lineSeparator() + newThemeString;
                Utils.writeFile("resources/theme/theme.txt", content);
                content = questionString + "/" + answerString;
                String fileName = "resources/theme/" + newThemeString + ".txt";

                if (Utils.writeFile(fileName, content)) {
                    Object[] options = {"Add more new puzzle", "Back to menu"};
                    int choice = JOptionPane.showOptionDialog(null, "Added new puzzle succesfully", "Success",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                    if (choice == JOptionPane.YES_OPTION) {
                        answerField.setText("");
                        questionArea.setText("");
                        createComBoBox();
                        newThemeField.setText("");
                        newThemeField.setEditable(false);
                    } else
                        GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                } else {
                    Object[] options = {"Back to menu"};
                    int choice = JOptionPane.showOptionDialog(null, "Failed to add new puzzle", "Failed",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                    if (choice == JOptionPane.YES_OPTION)
                        GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                }
                revalidate();
            }
        }
    }

    private void writeToAnExistentFile() {
        if (questionString.isEmpty() || answerString.isEmpty())
            JOptionPane.showConfirmDialog(null, "Please insert in all fields",
                    "Warning", JOptionPane.DEFAULT_OPTION);
        else {
            if (answerString.length() > 22) {
                JOptionPane.showMessageDialog(null, "Answer is too long limit is 22 characters", "warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String fileName = "resources/theme/" + themeCombo.getSelectedItem().toString() + ".txt";
                String content = System.lineSeparator() + questionString + "/" + answerString;
                if (Utils.writeFile(fileName, content)) {
                    Object[] options = {"Add more new puzzle", "Back to menu"};
                    int choice = JOptionPane.showOptionDialog(null, "Added new puzzle succesfully", "Success",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    if (choice == JOptionPane.YES_OPTION) {
                        answerField.setText("");
                        questionArea.setText("");
                    } else
                        GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                } else {
                    Object[] options = {"Back to menu"};
                    int choice = JOptionPane.showOptionDialog(null, "Failed to add new puzzle", "Failed",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                    if (choice == JOptionPane.YES_OPTION)
                        GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                }
            }
        }
    }

    private void createQuestionLabelAndArea() {
        questionLabel = new JLabel("Question:");
        questionLabel.setBounds(20, 40, 60, 20);
        questionArea = new JTextArea("");
        questionArea.setLineWrap(true);
        add(questionLabel);
        add(questionArea);
        JScrollPane sp = new JScrollPane(questionArea);
        add(sp);
        sp.setBounds(90, 40, 250, 60);
    }

    private void createAnswerLabelAndText() {
        answerLabel = new JLabel("Answer:");
        answerLabel.setBounds(20, 120, 60, 20);
        answerField = new JTextField("");
        answerField.setBounds(90, 120, 250, 20);
        add(answerLabel);
        add(answerField);
    }

    private void createComBoBox() {
        if (themeCombo != null)
            themeCombo.setVisible(false);
        Vector<String> themeVector = Utils.loadFile("theme/theme.txt");
        themeVector.add("Other");
        System.out.println(themeVector.size());
        themeLabel = new JLabel("Theme:");
        themeLabel.setBounds(20, 200, 60, 20);
        themeCombo = new JComboBox(themeVector);
        themeCombo.setBounds(90, 200, 250, 20);
        themeCombo.setVisible(true);
        add(themeCombo);
        add(themeLabel);
        themeCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (themeCombo.getSelectedItem().toString().equals("Other")) {
                        newThemeField.setEditable(true);
                    } else {
                        newThemeField.setEditable(false);
                    }
                }
            }
        });
    }

    private void createNewThemeField() {
        newThemeLabel = new JLabel("Theme:");
        newThemeLabel.setBounds(20, 240, 60, 20);
        newThemeField = new JTextField("");
        newThemeField.setBounds(90, 240, 250, 20);
        add(newThemeField);
        add(newThemeLabel);
        newThemeField.setEditable(false);
    }

    private void createAddAndCancelPuzzleButton() {
        ImageIcon imageIcon = new ImageIcon("resources/create-0.png");
        addPuzzleButton = new JLabel(imageIcon);
        addPuzzleButton.setBounds(38, 320, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(addPuzzleButton);
        imageIcon = new ImageIcon("resources/cancel-0.png");
        cancelPuzzleButton = new JLabel(imageIcon);
        cancelPuzzleButton.setBounds(220, 320, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(cancelPuzzleButton);
    }
}
