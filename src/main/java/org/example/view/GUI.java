package org.example.view;

import org.example.database.DbUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    JButton loginButton;
    JButton createUserButton;
    JButton deleteUserButton;
    JButton updateUserButton;
    JTextField usernameField;
    JPasswordField passwordField;
    JRadioButton passwordRadio;

    public void LoginWindow(){
        MyFrame frame = new MyFrame();

        loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.setBounds(325, 25,100,25);
        loginButton.addActionListener(this); // Can use a lambda expression instead, effort though
        loginButton.setFocusable(false);
        frame.add(loginButton);

        createUserButton = new JButton();
        createUserButton.setText("Sign up");
        createUserButton.setBounds(325, 60, 100, 25);
        createUserButton.addActionListener(this);
        createUserButton.setFocusable(false);
        frame.add(createUserButton);

        updateUserButton = new JButton();
        updateUserButton.setText("Update");
        updateUserButton.setBounds(325, 95, 100, 25);
        updateUserButton.addActionListener(this);
        updateUserButton.setFocusable(false);
        frame.add(updateUserButton);

        deleteUserButton = new JButton();
        deleteUserButton.setText("Delete");
        deleteUserButton.setBounds(325, 130, 100,25);
        deleteUserButton.addActionListener(this);
        deleteUserButton.setFocusable(false);
        frame.add(deleteUserButton);

        passwordRadio = new JRadioButton();
        passwordRadio.setText("Show Password");
        passwordRadio.setBounds(190, 96, 130, 20);
        passwordRadio.addActionListener(this);
        frame.add(passwordRadio);

        JLabel username = new JLabel();
        JLabel password = new JLabel();
        username.setText("Username");
        password.setText("Password");

        username.setBounds(25, 25, 60, 15);
        password.setBounds(25, 75, 60, 15);
        frame.add(username);
        frame.add(password);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        usernameField.setBounds(25, 45, 150, 25);
        passwordField.setBounds(25, 95, 150, 25);
        username.setPreferredSize(new Dimension(150, 25));
        usernameField.setFocusable(true);
        passwordField.setVisible(true);
        frame.add(usernameField);
        frame.add(passwordField);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DbUser user = new DbUser();
        String username = usernameField.getText();
        char[] charPassword = passwordField.getPassword();
        String password = new String(charPassword);

        if (passwordRadio.isSelected()){
            passwordField.setEchoChar((char)0);
        } else {
            passwordField.setEchoChar('*');
        }

        if (e.getSource() == loginButton) {
            if (user.verifyUserLogin(username, password)) {
                System.out.println("Successfully logged in");
            } else {
                System.out.println("Could not login");
            }
        }

        if (e.getSource() == deleteUserButton) {
            user.deleteUser(username, password);
        }

        if (e.getSource() == createUserButton) {
            user.createUser(username, password);
        }
    }
}
