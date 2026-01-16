package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;

public class OwnerLoginPanel extends JPanel {

    private final MainWindow frame;

    // Primary button color
    private final Color PRIMARY_BTN_BG = new Color(0x00CED1);

    public OwnerLoginPanel(MainWindow frame) {
        this.frame = frame;

        // --- Background color ---
        setLayout(new BorderLayout());
        setBackground(new Color(0x339999)); // #339999

        // --- Left menu ---
        JPanel leftMenu = new JPanel();
        leftMenu.setBackground(new Color(0x004040)); // #004040
        leftMenu.setPreferredSize(new Dimension(260, 0));
        leftMenu.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 15, 25, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Title
        gbc.gridy = 0;
        JLabel welcomeTitle = new JLabel("Welcome", SwingConstants.CENTER);
        welcomeTitle.setFont(new Font("Georgia", Font.BOLD, 24));
        welcomeTitle.setForeground(Color.WHITE);
        leftMenu.add(welcomeTitle, gbc);

        // Spacer
        gbc.gridy++;
        gbc.weighty = 1;
        leftMenu.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;

        // Back Button (white background, black text)
        gbc.gridy++;
        JButton backBtn = new JButton("Back");
        backBtn.setBackground(Color.WHITE); // white background
        backBtn.setOpaque(true);
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1)); // subtle border
        backBtn.setForeground(Color.BLACK); // black text
        backBtn.setPreferredSize(new Dimension(180, 50)); // professional size
        backBtn.setFont(new Font("Georgia", Font.BOLD, 18)); // slightly larger font
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> frame.switchPanel("Welcome"));
        leftMenu.add(backBtn, gbc);

        add(leftMenu, BorderLayout.WEST);

        // --- Right login form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0x339999)); // #339999
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(15, 20, 15, 20);
        fgbc.gridx = 0;
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        // Owner Login Title
        fgbc.gridy = 0;
        JLabel title = new JLabel("Owner Login", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        fgbc.gridwidth = 2;
        formPanel.add(title, fgbc);

        // Password Label
        fgbc.gridy++;
        fgbc.gridwidth = 1;
        JLabel pwdLabel = new JLabel("Password:");
        pwdLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
        pwdLabel.setForeground(Color.WHITE);
        formPanel.add(pwdLabel, fgbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Georgia", Font.PLAIN, 18));
        fgbc.gridx = 1;
        formPanel.add(passwordField, fgbc);

        // Login Button
        fgbc.gridy++;
        fgbc.gridx = 0;
        fgbc.gridwidth = 2;
        JButton loginBtn = new JButton("Login");
        styleSideButton(loginBtn, PRIMARY_BTN_BG, PRIMARY_BTN_BG.darker()); // Primary color with hover
        loginBtn.setPreferredSize(new Dimension(160, 45));
        loginBtn.setForeground(Color.BLACK); // black text

        loginBtn.addActionListener(e -> {
            String typed = new String(passwordField.getPassword());
            if (typed.equals(frame.getOwnerPassword())) {
                passwordField.setText("");
                frame.switchPanel("OwnerPanel");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        });
        formPanel.add(loginBtn, fgbc);

        add(formPanel, BorderLayout.CENTER);
    }

    // Button styling
    private void styleSideButton(JButton button, Color normal, Color hover) {
        button.setFont(new Font("Georgia", Font.BOLD, 16));
        button.setForeground(button.getForeground()); // keep current text color
        button.setBackground(normal);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
            }
        });
    }
}
