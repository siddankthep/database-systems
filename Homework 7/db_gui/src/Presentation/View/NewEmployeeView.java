package Presentation.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class NewEmployeeView extends JFrame {
    private LabeledTextInput id = new LabeledTextInput("ID");
    private LabeledTextInput name = new LabeledTextInput("Name");
    private LabeledTextInput jobID = new LabeledTextInput("Job ID");
    private JButton createButton = new JButton("Create Employee");

    public NewEmployeeView() {
        this.setTitle("New Employee");
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
        this.add(jobID, c);

        c.gridx = 0;
        c.gridy = 3;
        this.add(createButton, c);

        // this.setVisible(true);
    }

    public String getID() {
        return id.getText();
    }

    public String getName() {
        return name.getText();
    }

    public String getJobID() {
        return jobID.getText();
    }

    public void setCreateEmployeeListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    // public static void main(String[] args) {
    //     new NewEmployeeView();
    // }
}
