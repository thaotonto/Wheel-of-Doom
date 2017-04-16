package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Hoang on 4/9/2017.
 */
public class TimerPanel extends JPanel {
    private long start;
    private long end;
    private int timer;
    private int TIMER;
    private Image image= Utils.loadImageFromRes("images.gif");
    public TimerPanel(int timer) {
        setVisible(true);
        setOpaque(false);
        this.setBounds(400, 520, 50, 50);
        setLayout(null);
        start = System.currentTimeMillis();
        end = System.currentTimeMillis();
        this.timer = timer;
        TIMER = timer;
    }

    public boolean run() {
//        System.out.println("start " + start);
//        System.out.println("end   " + end);
//        System.out.println("Timer running");
        end = System.currentTimeMillis();
        if (end - start > 1000) {
            start = System.currentTimeMillis();
            timer--;
//            System.out.println("Timer decreased");
        }
        repaint();
        if (timer == 0) {
            System.out.println("Time's up");
            timer = TIMER;
            return true;
        } else return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(null,Font.BOLD,20));
        g.drawString(timer + "", 25, 20);
        g.drawImage(image,0,3,20,20,null);
    }

    public void resetTimer() {
        start = System.currentTimeMillis();
        end = System.currentTimeMillis();
        timer = TIMER;
    }
}
