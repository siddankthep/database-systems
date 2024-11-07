package com.retail.view.components;

import javax.swing.*;

import java.awt.*;

public class LabeledTextInput extends JPanel {
    private JLabel label = new JLabel();
    private JTextField text = new JTextField();

    public LabeledTextInput(String label) {
        // this.setPreferredSize(new Dimension(300, 60));
        this.label.setText(label);
        this.text.setPreferredSize(new Dimension(200, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.label.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.text.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(this.label);
        add(Box.createRigidArea(new Dimension(0, 3)));
        add(this.text);
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

}
