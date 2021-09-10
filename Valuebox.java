package com.company;

import javax.swing.*;
import java.awt.*;

public class Valuebox extends JPanel {
    private final float min;
    private final float max;

    private final int numPoints;

    private final JTextField textField;
    private final JScrollBar scrollBar;

    public Valuebox(String quantity, float value, float min, float max, float step) {
        super();
        this.min = min;
        this.max = max;

        this.numPoints = Math.round((max - min) / step);

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;

        JLabel label = new JLabel(quantity);
        label.setPreferredSize(new Dimension(30, 30));
        label.setFont(new Font("Dialog", Font.PLAIN, 20));
        constraints.weightx = 0.3;
        this.add(label, constraints);

        textField = new JTextField(Float.toString(value));
        textField.setPreferredSize(new Dimension(40, 30));
        constraints.weightx = 0.4;
        this.add(textField, constraints);

        scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, toPoint(value), 0, 0, numPoints);
        scrollBar.setPreferredSize(new Dimension(100, 30));
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(scrollBar, constraints);

        scrollBar.addAdjustmentListener(
                e -> textField.setText(Float.toString(toValue(e.getValue())))
        );

        textField.addActionListener(
                e -> {
                    float res = Float.parseFloat(textField.getText());
                    if (res > max)
                        res = max;
                    else if (res < min)
                        res = max;
                    textField.setText(Float.toString(res));
                    scrollBar.setValue(toPoint(res));
                }
        );
    }

    public int toPoint(float value) {
        return Math.round((value - min) / (max - min) * numPoints);
    }

    public float toValue(int point) {
        return (point * (max - min) / numPoints) + min;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JScrollBar getScrollBar() {
        return scrollBar;
    }
}
