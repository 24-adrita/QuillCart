package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public class ReceiptDialog extends JDialog {

    public ReceiptDialog(Frame owner, String receiptText) {
        super(owner, "Receipt", true);
        setLayout(new BorderLayout(10, 10));

        // === Receipt Text Area ===
        JTextArea receiptArea = new JTextArea(receiptText, 20, 40);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setBackground(new Color(255, 255, 255)); // White background
        receiptArea.setForeground(new Color(0, 0, 0)); // Black text
        JScrollPane receiptScroll = new JScrollPane(receiptArea);

        add(receiptScroll, BorderLayout.CENTER);

        // === Buttons Panel ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(255, 192, 203)); // light pink bar

        JButton copyButton = new JButton("Copy");
        JButton printButton = new JButton("Print");
        JButton closeButton = new JButton("Close");

        // Style buttons to match the sample (pink background, white text)
        JButton[] buttons = {copyButton, printButton, closeButton};
        for (JButton b : buttons) {
            b.setFont(new Font("Georgia", Font.BOLD, 14));
            b.setBackground(new Color(255, 105, 180)); // hot pink
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            buttonPanel.add(b);
        }

        // Attach actions
        printButton.addActionListener(e -> {
            try {
                receiptArea.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ReceiptDialog.this,
                        "Printing failed: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        copyButton.addActionListener(e -> {
            receiptArea.selectAll();
            receiptArea.copy();
            JOptionPane.showMessageDialog(ReceiptDialog.this, "Receipt copied to clipboard.");
        });
        closeButton.addActionListener(e -> dispose());

        // Place button panel at the bottom of the dialog
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }
}
