package Presentation.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class NewProjectView extends JFrame {
    private LabeledTextInput id = new LabeledTextInput("ID");
    private LabeledTextInput name = new LabeledTextInput("Name");
    private LabeledTextInput leaderID = new LabeledTextInput("Leader ID");
    private LabeledTextInput cost = new LabeledTextInput("Cost");
    private JButton createButton = new JButton("Create Project");

    public NewProjectView() {
        // JFrame frame = new JFrame();
        this.setTitle("New Project");
        this.setSize(400, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        c.insets = new Insets(5, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        this.add(id, c);

        c.gridx = 0;
        c.gridy = 1;
        this.add(name, c);

        c.gridx = 0;
        c.gridy = 2;
        this.add(leaderID, c);

        c.gridx = 0;
        c.gridy = 3;
        this.add(cost, c);

        c.gridx = 0;
        c.gridy = 4;
        this.add(createButton, c);

        // this.setVisible(true);
    }

    public String getID() {
        return id.getText();
    }

    public String getName() {
        return name.getText();
    }

    public String getLeaderID() {
        return leaderID.getText();
    }

    public String getCost() {
        return cost.getText();
    }

    public void setCreateProjectListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    // public static void main(String[] args) {
    //     new NewProjectView();
    // }
}
