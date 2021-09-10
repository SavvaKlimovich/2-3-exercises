package com.company;

import javax.swing.*;
import java.awt.*;

public class Interactivity extends JPanel {
    private final Methods methods;
    private final Chart chart;

    public Interactivity(Window window) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        methods = new Methods(1000, 0.1f, 5, 5, 0, 0, 0.1f, 0.01f);
        chart = window.getChart();

        JLabel label = new JLabel("y'' + 2γy' + ω₀²y = f₀cosωt");
        label.setFont(new Font("Verdana", Font.ITALIC, 20));
        add(label);

        Valuebox[] boxes = {
                new Valuebox("γ", methods.getGamma(), 0, 10, 0.001f),
                new Valuebox("ω₀", methods.getW0(), 0, 100, 0.01f),
                new Valuebox("ω", methods.getW(), 0, 100, 0.01f),
                new Valuebox("f₀", methods.getF0(), 0, 100, 0.01f),
                new Valuebox("y₀", methods.getY0(), -100, 100, 0.05f),
                new Valuebox("v₀", methods.getV0(), 0, 10, 0.01f)
        };

        Setters[] setters = {
                methods::setGamma,
                methods::setW0,
                methods::setW,
                methods::setF0,
                methods::setY0,
                methods::setV0
        };

        for(int i = 0; i != boxes.length; i++) {
            final int num = i;
            add(boxes[i]);
            boxes[i].getTextField().addActionListener(
                    e -> (setters[num]).setValue(Float.parseFloat(boxes[num].getTextField().getText()))
            );
            boxes[i].getScrollBar().addAdjustmentListener(
                    e -> (setters[num]).setValue(boxes[num].toValue(boxes[num].getScrollBar().getValue()))
            );
        }

        Valuebox numPointsBox = new Valuebox("N", methods.getNumPoints(), 10, 10000, 1);
        add(numPointsBox);
        numPointsBox.getTextField().addActionListener(
                e -> methods.setNumPoints(Integer.parseInt(numPointsBox.getTextField().getText()))
        );
        numPointsBox.getScrollBar().addAdjustmentListener(
                e -> methods.setNumPoints(Math.round(numPointsBox.toValue(numPointsBox.
                        getScrollBar().getValue())))
        );

        Valuebox dtBox = new Valuebox("dt", methods.getDt(), 0.0001f, 1, 0.0001f);
        add(dtBox);
        dtBox.getTextField().addActionListener(
                e -> methods.setDt(Float.parseFloat(dtBox.getTextField().getText()))
        );
        dtBox.getScrollBar().addAdjustmentListener(
                e -> methods.setDt(dtBox.toValue(dtBox.getScrollBar().getValue()))
        );

        JButton btnSin = new JButton("y(t)");
        add(btnSin);
        btnSin.addActionListener(
                e -> chart.setSolutionSin(methods.eilersMethod())
        );

        JButton btnFP = new JButton("v(y)");
        add(btnFP);
        btnFP.addActionListener(
                e -> chart.setSolutionFP(methods.eilersMethod())
        );
    }
}

interface Setters {
    void setValue(float x);
}
