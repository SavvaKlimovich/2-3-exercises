package com.company;

import javax.swing.*;
import java.awt.*;

public class Chart extends JPanel {
    private int[] arrX, arrY;
    private int numPoints;
    private final Window window;
    private int displayWidth;
    private int displayHeight;

    private int aX, aY;
    private String aXName, aYName;

    public Chart(Window window) {
        setBackground(new Color(0xEFEFEF));
        this.window = window;
    }

    public void setSolutionSin(Methods.Solution solution) {
        aXName = "t";
        aYName = "y";
        numPoints = solution.getNumPoints();
        arrX = new int[numPoints];
        arrY = new int[numPoints];

        displayWidth = window.getChart().getWidth();
        displayHeight = window.getChart().getHeight();

        float y0 = solution.getMinY() - 0.1f * (solution.getMaxY() - solution.getMinY());
        float y_ = solution.getMaxY() + 0.1f * (solution.getMaxY() - solution.getMinY());

        float c = displayHeight / (y_ - y0);

        for (int i = 0; i != numPoints; i++) {
            arrX[i] = Math.round(((float)i / numPoints + 0.1f) * 10.f / 12.f * displayWidth);
            arrY[i] = Math.round(c * (y_ - solution.getYArray()[i]));
        }

        aX = findAX(c, y_);
        aY = Math.round(displayWidth / 12.f);
    }

    public void setSolutionFP(Methods.Solution solution) {
        aXName = "y";
        aYName = "v";
        numPoints = solution.getNumPoints();
        arrX = new int[numPoints];
        arrY = new int[numPoints];

        displayWidth = window.getChart().getWidth();
        displayHeight = window.getChart().getHeight();

        float x0 = solution.getMinY() - 0.1f * (solution.getMaxY() - solution.getMinY());
        float x_ = solution.getMaxY() + 0.1f * (solution.getMaxY() - solution.getMinY());

        float y0 = solution.getMinV() - 0.1f * (solution.getMaxV() - solution.getMinV());
        float y_ = solution.getMaxV() + 0.1f * (solution.getMaxV() - solution.getMinV());

        float c1 = displayWidth / (x_ - x0);
        float c2 = displayHeight / (y_ - y0);

        for (int i = 0; i != numPoints; i++) {
            arrX[i] = Math.round(c1 * (solution.getYArray()[i] - x0));
            arrY[i] = Math.round(c2 * (y_ - solution.getVArray()[i]));
        }

        aX = findAX(c2, y_);
        aY = findAY(c1, x0);
    }

    public int findAY(float c, float x0) {
        int res = Math.round(-c * x0);
        if (res < 25) res = 25;
        if (res > displayWidth - 25) res = displayWidth - 25;
        return res;
    }

    public int findAX(float c, float y_) {
        int res = Math.round(c * y_);
        if (res < 25) res = 25;
        if (res > displayHeight - 25) res = displayHeight - 25;
        return res;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        if (0 != numPoints) {
            setBackground(new Color(0xEFEFEF));

            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(new Color(0x7F7F7F));

            g2d.drawLine(0, aX, displayWidth, aX);
            g2d.drawLine(displayWidth, aX, displayWidth - 20, aX - 5);
            g2d.drawLine(displayWidth, aX, displayWidth - 20, aX + 5);

            //добавить разметку

            g2d.drawLine(aY, 0, aY, displayHeight);
            g2d.drawLine(aY, 0, aY - 5, 20);
            g2d.drawLine(aY, 0, aY + 5, 20);

            g2d.setColor(Color.DARK_GRAY);

            g2d.setFont(new Font("Italic", Font.BOLD, 20));
            g2d.drawString(aXName, displayWidth - 15, aX + 20);
            g2d.drawString(aYName, aY - 20, 15);

            g2d.setColor(Color.RED);
            g2d.fillOval(arrX[0] - 5, arrY[0] - 5, 10, 10);

            g2d.setColor(new Color(0xEF5F20));
            for (int i = 1; i != numPoints; i++) {
                //выводить произвольное количество графиков

                g2d.drawLine(arrX[i - 1], arrY[i - 1], arrX[i], arrY[i]);
            }
        }

        super.repaint();
    }
}
