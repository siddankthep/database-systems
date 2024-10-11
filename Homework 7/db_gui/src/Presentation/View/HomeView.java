package Presentation.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {
    private JButton newProjectButton = new JButton("New Project");
    private JButton newEmployeeButton = new JButton("New Employee");
    private JButton queryButton = new JButton("Query");

    public HomeView() {
        setTitle("HW7 Home");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        add(newProjectButton, c);

        c.gridx = 0;
        c.gridy = 1;
        add(newEmployeeButton, c);

        c.gridx = 0;
        c.gridy = 2;
        add(queryButton, c);

        // setVisible(true);
    }

    public void setNewProjectListener(ActionListener listener) {
        newProjectButton.addActionListener(listener);
    }

    public void setNewEmployeeListener(ActionListener listener) {
        newEmployeeButton.addActionListener(listener);
    }

    public void setQueryListener(ActionListener listener) {
        queryButton.addActionListener(listener);
    }

    // public static void main(String[] args) {
    // new HomeView();
    // }
}
