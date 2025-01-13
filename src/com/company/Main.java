package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton button;
    private static JLabel success;
    private static JFrame frame;
    private static JPanel panel;


    public static void main(String[] args) {

        frame = new JFrame("Login to Radio");
        panel = new JPanel();
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("User");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        userText = new JTextField();
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds(10,80,80,25);
        button.addActionListener(new Main());
        panel.add(button);

        success = new JLabel();
        success.setBounds(10,110,300,25);
        panel.add(success);

        frame.setVisible(true);


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.getSize().width/2 - frame.getSize().width/2, dim.getSize().height/2 - frame.getSize().height/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();
        System.out.println(user + ", " + password);

        if(user.equals("Someone") && password.equals("123")) {
            success.setText("Login successful");
            RadioVisualGUI radioGUI = new RadioVisualGUI();
            frame.dispose();
        }

    }


}
