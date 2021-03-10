package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final Chart chartPanel;

    public Window(int width, int height) {
        super("Second Exercise");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        container.setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = insets;
        constraints.weightx = 1;
        constraints.weighty = 1;

        chartPanel = new Chart(this);
        container.add(chartPanel, constraints);

        constraints.weightx = 0.4;
        constraints.gridx = 1;

        Interactivity interactivity = new Interactivity(this);
        container.add(interactivity, constraints);
    }

    public Chart getChart() { return chartPanel; }

    public static void main(String[] args) {
        Window window = new Window(1000, 600);
        window.setVisible(true);
    }
}
