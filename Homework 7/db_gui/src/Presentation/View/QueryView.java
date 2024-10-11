package Presentation.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class QueryView extends JFrame {
    private JButton executeQueryButton = new JButton("Execute Query");
    private JButton homeButton = new JButton("Home");
    private LabeledTextInput projectName = new LabeledTextInput("Project Name");
    private JLabel resultLabel = new JLabel("Query result will be displayed here");

    public QueryView() {
        setTitle("List Employees");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        executeQueryButton.setAlignmentX(Component.BOTTOM_ALIGNMENT);
        executeQueryButton.setPreferredSize(new Dimension(150, 50));
        controlPanel.add(projectName);
        controlPanel.add(executeQueryButton);
        controlPanel.add(homeButton);

        resultLabel.setVerticalAlignment(SwingConstants.TOP);
        add(controlPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);

        // setVisible(true);
    }

    public String getProjectName() {
        return projectName.getText();
    }


    public void setQueryButtonListener(ActionListener listener) {
        executeQueryButton.addActionListener(listener);
    }

    public void setHomeButtonListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }

    public void setResultText(String text) {
        resultLabel.setText(text);
    }
}
