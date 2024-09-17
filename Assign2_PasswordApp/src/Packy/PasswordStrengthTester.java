package Packy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A GUI application that evaluates password strength based on the length of the largest block of consecutive identical characters.
 * The password must be between 8 and 12 characters long without spaces.
 */
public class PasswordStrengthTester extends JFrame {

    //Text field for entering the password. 
    private JTextField txtPassword;

    //Text area for displaying feedback on password strength. 
    private JTextArea txtFeedback;

    //Button to trigger the password strength check. */
    private JButton btnCheck;

    //Constructs the PasswordStrengthTester GUI and initializes its components. */
    public PasswordStrengthTester() {
        setTitle("Password Strength Checker");
        setBounds(100, 100, 400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Password input field
        txtPassword = new JTextField();
        txtPassword.setPreferredSize(new Dimension(200, 30));
        getContentPane().add(txtPassword, BorderLayout.NORTH);

        // Feedback area
        txtFeedback = new JTextArea();
        txtFeedback.setEditable(false);
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtFeedback);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Button to trigger password check
        btnCheck = new JButton("Check Password Strength");
        getContentPane().add(btnCheck, BorderLayout.SOUTH);

        // Action listener for the button
        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkPassword();
            }
        });
    }

    //Checks the strength of the entered password by analyzing the largest block of identical characters. 
    private void checkPassword() {
        String password = txtPassword.getText().trim();

        // Password validation
        if (password.length() < 8 || password.length() > 12) {
            txtFeedback.setText("Error: Password must be between 8 and 12 characters.");
            return;
        }

        if (password.contains(" ")) {
            txtFeedback.setText("Error: Password cannot contain spaces.");
            return;
        }

        // Find the largest block of adjacent identical characters
        int maxBlockLength = findLargestBlock(password);

        // Display feedback based on the block length
        if (maxBlockLength <= 2) {
            txtFeedback.setText("The largest block in the password is " + maxBlockLength + ". This is a decent password.");
        } else {
            txtFeedback.setText("The largest block in the password is " + maxBlockLength + 
                                ". This password can be made stronger by reducing this block by " + (maxBlockLength - 2) + ".");
        }
    }

    // Finds the largest block of adjacent identical characters in the password.
    private int findLargestBlock(String password) {
        int maxBlockLength = 1;
        int currentBlockLength = 1;

        // Loop through the password to find consecutive blocks
        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) == password.charAt(i - 1)) {
                currentBlockLength++;
            } else {
                maxBlockLength = Math.max(maxBlockLength, currentBlockLength);
                currentBlockLength = 1; // Reset current block length
            }
        }

        // Final check for the last block
        return Math.max(maxBlockLength, currentBlockLength);
    }

    // Main method to run the PasswordStrengthTester.
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PasswordStrengthTester frame = new PasswordStrengthTester();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

/*
 * Test 1: Strong
 * BlickyBlock
 * The largest block in the password is 1. This is a decent password.
 
 * Test 2: Too Long
 * BlickyBlockandahladk
 * Error: Password must be between 8 and 12 characters.
 
 * Test 3: Too Short
 * Blick
 * Error: Password must be between 8 and 12 characters.
 
 * Test 4: Weak
 * AAAAAAbbbbbb
 * The largest block in the password is 6. This password can be made stronger by reducing this block by 4.
 */

