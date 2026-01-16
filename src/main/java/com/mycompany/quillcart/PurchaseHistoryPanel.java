package com.mycompany.quillcart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class PurchaseHistoryPanel extends JPanel {
    private final MainWindow frame;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> list = new JList<>(listModel);
    private final JTextArea details = new JTextArea();
    private final String titleText;

    public PurchaseHistoryPanel(MainWindow frame, String titleText) {
        this.frame = frame;
        this.titleText = titleText;

        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(60, 80, 140)); 
        setBorder(new EmptyBorder(16, 16, 16, 16));

       
        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        add(titleLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 12, 12));
        center.setOpaque(false);

       
        JPanel left = new JPanel(new BorderLayout(6, 6));
        left.setOpaque(false);
        JLabel ordersLabel = new JLabel("Orders", SwingConstants.CENTER);
        ordersLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        ordersLabel.setForeground(Color.WHITE);
        left.add(ordersLabel, BorderLayout.NORTH);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Georgia", Font.PLAIN, 16));
        list.setBackground(new Color(230, 230, 230));
        left.add(new JScrollPane(list), BorderLayout.CENTER);

        
        JPanel right = new JPanel(new BorderLayout(6, 6));
        right.setOpaque(false);
        JLabel detailsLabel = new JLabel("Details", SwingConstants.CENTER);
        detailsLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        detailsLabel.setForeground(Color.WHITE);
        right.add(detailsLabel, BorderLayout.NORTH);

        details.setEditable(false);
        details.setFont(new Font("Monospaced", Font.PLAIN, 16));
        details.setBackground(new Color(245, 245, 245)); 
        right.add(new JScrollPane(details), BorderLayout.CENTER);

        center.add(left);
        center.add(right);
        add(center, BorderLayout.CENTER);

       
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);

        JButton print = new JButton("Print Selected");
        styleDarkBlueButton(print);

        JButton back = new JButton("Back");
        styleDarkBlueButton(back);

        bottom.add(print);
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showDetails(list.getSelectedIndex());
        });

        back.addActionListener(e -> frame.goBackFromHistory());
        print.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx < 0) return;
            Purchase p = frame.getPurchaseHistory().get(idx);
            
           
            String receiptText = buildReceiptText(p);
            ReceiptDialog receiptDialog = new ReceiptDialog(frame, receiptText);
            receiptDialog.setVisible(true);
        });
    }

    private void styleDarkBlueButton(JButton button) {
        button.setBackground(new Color(40, 80, 160)); // Dark blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Georgia", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 60, 120));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(40, 80, 160));
            }
        });
    }

    void refresh() {
        listModel.clear();
        details.setText("");
        java.util.List<Purchase> ph = frame.getPurchaseHistory();
        for (Purchase p : ph) {
            listModel.addElement("#" + p.id + " • " + p.timestamp + " • " + UIX.formatTaka(p.totalTaka) +
                    (p.promoApplied ? " • Promo" : ""));
        }
        if (!ph.isEmpty()) {
            list.setSelectedIndex(0);
            showDetails(0);
        }
    }

    private void showDetails(int idx) {
        if (idx < 0 || idx >= frame.getPurchaseHistory().size()) {
            details.setText("");
            return;
        }
        Purchase p = frame.getPurchaseHistory().get(idx);
        details.setText(buildReceiptText(p));
        details.setCaretPosition(0);
    }

    
    
    public static String buildReceiptText(Purchase p) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("QuillCart\n");
        sb.append("Purchase Receipt\n");
        sb.append("-----------------------------------------\n");
        
        sb.append("Order  : #").append(p.id).append("\n");
        sb.append("Date   : ").append(p.timestamp).append("\n");
        sb.append("\n");
        
        sb.append("Customer Info:\n");
        sb.append("Name    : ").append(p.customerName == null ? "" : p.customerName).append("\n");
        sb.append("Address : ").append(p.customerAddress == null ? "" : p.customerAddress).append("\n");
        sb.append("Phone   : ").append(p.customerPhone == null ? "" : p.customerPhone).append("\n");
        sb.append("\n");
        
        sb.append("Items:\n");
        sb.append(String.format("%-30s %5s %12s\n", "Title", "Qty", "Line Total"));
        sb.append("-----------------------------------------\n");
        
        for (Map.Entry<String, Purchase.Line> entry : p.lines.entrySet()) {
            String title = entry.getKey();
            int quantity = entry.getValue().qty;
            double unitPrice = entry.getValue().unitTaka;
            double lineTotal = unitPrice * quantity;
            sb.append(String.format("%-30s %5d %12s\n", title, quantity, UIX.formatTaka(lineTotal)));
        }
        sb.append("-----------------------------------------\n");
      
        sb.append(String.format("%-30s %12s\n", "TOTAL:", UIX.formatTaka(p.totalTaka)));
        sb.append("\nThank you for shopping!\n");
        return sb.toString();
    }
}
