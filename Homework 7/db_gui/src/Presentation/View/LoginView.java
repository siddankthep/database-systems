package Presentation.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private LabeledTextInput usernameInput = new LabeledTextInput("Username");
    private LabeledTextInput passwordInput = new LabeledTextInput("Password");
    private JButton loginButton = new JButton("Login");

    public LoginView() {
        // JFrame frame = new JFrame();
        this.setTitle("Login");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        c.insets = new Insets(5, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        this.add(usernameInput, c);

        c.gridx = 0;
        c.gridy = 1;
        this.add(passwordInput, c);

        c.gridx = 0;
        c.gridy = 2;
        this.add(loginButton, c);

        // this.setVisible(true);
    }

    public String getUsername() {
        return usernameInput.getText();
    }

    public String getPassword() {
        return passwordInput.getText();
    }

    public void setLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    // public static void main(String[] args) {
    //     new LoginView();
    // }
}
