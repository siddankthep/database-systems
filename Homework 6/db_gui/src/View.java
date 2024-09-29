import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton[] queryButtons = new JButton[9];
    private JLabel resultLabel = new JLabel("Query result will be displayed here");
    public View() {
        setTitle("HW6 Query Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 2));

        for (int i = 0; i < queryButtons.length; i++) {
            queryButtons[i] = new JButton("Query " + (i + 1));
            buttonPanel.add(queryButtons[i]);
        }

        resultLabel.setVerticalAlignment(SwingConstants.TOP);
        add(buttonPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);
    }

    public void setButtonListener(int buttonIndex, ActionListener listener) {
        queryButtons[buttonIndex].addActionListener(listener);
    }

    public void setResultText(String text) {
        resultLabel.setText(text);
    }
}
