package gui;

import controller.GameController;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Inpriron on 3/30/2017.
 */
public class WheelPanel extends JPanel {
    private static int PANEL_WIDTH = 450;
    private static int PANEL_HEIGHT = 500;
    //    private Image backGroundImage;
    private Image image;
    private Image imagePointer;
    private BufferedImage backBufferImage;
    private Graphics backGraphics;
    private JButton powerBar;
    private Point startingPoint;
    private Point endPoint;
    private Thread thread;
    int i;
    private int counter;
    private int currentDegree;
    private int calculator;
    public static WheelPanel instance = new WheelPanel();
    private String result;
    public String monitor = "";

    public WheelPanel() {
        setLayout(null);
        setOpaque(false);
        this.setBounds(570, 50, PANEL_WIDTH, PANEL_HEIGHT);
        this.setBackground(Color.blue);
        powerBar = new JButton();
        powerBar.setBounds(50, 50, 500, 250);
        this.add(powerBar);
        powerBar.setVisible(true);
        powerBar.setOpaque(true);
        powerBar.setEnabled(true);
        powerBar.setContentAreaFilled(false);
        powerBar.setBorderPainted(false);
        powerBar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        powerBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                PowerBarMousePressed(evt);
            }

            public void mouseReleased(MouseEvent evt) {
                PowerBarMouseReleased(evt);
            }
        });
        backBufferImage = new BufferedImage(
                PANEL_WIDTH,
                PANEL_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        backGraphics = backBufferImage.getGraphics();
        image = Utils.loadImageFromRes("Wheel.png");
        imagePointer = Utils.loadImageFromRes("Pointer.png");
    }

    public void spinWheel(int power_) {
        powerBar.setEnabled(false);
        thread = new Thread(new Runnable() {
            int power = power_;

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();
                    counter++;
                    currentDegree += power;
                    if (counter > 60) {
                        counter = 0;
                        if (power > 0)
                            power--;
                        if (power < 0)
                            power++;
                    }
                    if (power == 0) {
                        calculator = currentDegree;
                        while (calculator < 0)
                            calculator += 360;
                        while (calculator > 360)
                            calculator -= 360;
                        calculator = calculator / 24;
                        switch (calculator) {
                            case 0:
                                System.out.println("900");
                                result = "900";
                                break;
                            case 1:
                                System.out.println("get turn");
                                result = "get turn";
                                break;
                            case 2:
                                System.out.println("500");
                                result = "500";
                                break;
                            case 3:
                                System.out.println("1000");
                                result = "1000";
                                break;
                            case 4:
                                System.out.println("400");
                                result = "400";
                                break;
                            case 5:
                                System.out.println("lose turn");
                                result = "lose turn";
                                break;
                            case 6:
                                System.out.println("600");
                                result = "600";
                                break;
                            case 7:
                                System.out.println("Prize");
                                result = "Prize";
                                break;
                            case 8:
                                System.out.println("300");
                                result = "300";
                                break;
                            case 9:
                                System.out.println("800");
                                result = "800";
                                break;
                            case 10:
                                System.out.println("1100");
                                result = "1100";
                                break;
                            case 11:
                                System.out.println("100");
                                result = "100";
                                break;
                            case 12:
                                System.out.println("200");
                                result = "200";
                                break;
                            case 13:
                                System.out.println("700");
                                result = "700";
                                break;
                            case 14:
                                System.out.println("bankrupt");
                                result = "bankrupt";
                                break;

                        }
                        synchronized (monitor) {
                            monitor.notify();
                        }
//                        thread.stop();
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (backBufferImage != null) {
            AffineTransform at = AffineTransform.getTranslateInstance(50, 110);
            at.rotate(Math.toRadians(currentDegree), image.getWidth(null) * 0.75 / 2, image.getHeight(null) * 0.75 / 2);
            Graphics2D g2d = (Graphics2D) backGraphics;
            at.scale(0.75, 0.75);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(image, at, null);
            g2d.drawImage(imagePointer, 55, 50 + image.getHeight(null) / 2 - imagePointer.getHeight(null) / 2, null);
            graphics.drawImage(backBufferImage, 0, 0, null);

        }
    }

    private void PowerBarMousePressed(MouseEvent evt) {
        // TODO add your handling code here:
        GameController.gamePanel.setGuessTrue(false);
        startingPoint = evt.getPoint();
    }

    private void PowerBarMouseReleased(MouseEvent evt) {
        // TODO add your handling code here:
        if (powerBar.isEnabled()) {
            endPoint = evt.getPoint();
            int x1 = (int) Math.round(startingPoint.getX());
            int x2 = (int) Math.round(endPoint.getX());
            if (Math.abs(x1 - x2) > 80)

            {
                if (x2 > 500)
                    x2 = 500;
                WheelPanel.instance.spinWheel((x2 - x1) / 40);
            }

        }
    }


    public void setPowerBar(boolean status) {
        this.powerBar.setEnabled(status);
    }

    public String getResult() {
        return result;
    }
}
