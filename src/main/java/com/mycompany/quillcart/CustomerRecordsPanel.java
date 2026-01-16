package com.mycompany.quillcart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Displays a summary table of all customers who have placed orders.
 * Main background updated to a medium dark blue (60, 80, 140).
 */
public class CustomerRecordsPanel extends JPanel {
    private final MainWindow frame;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JPanel tableContainer;
    private final JPanel headerBar;
    private final JCheckBox darkModeCheckbox;

    public CustomerRecordsPanel(MainWindow frame) {
        this.frame = frame;
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(60, 80, 140)); // main background updated
        setBorder(new EmptyBorder(16, 16, 16, 16));

        // Title
        JLabel title = new JLabel("Customer Info Records", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 28));
        title.setForeground(Color.WHITE); // text visible on dark background
        add(title, BorderLayout.NORTH);

        // Table container
        tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(true);
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

        // Header bar
        JLabel header = new JLabel("Customers (from purchases)");
        header.setFont(new Font("Georgia", Font.BOLD, 20));
        header.setForeground(Color.BLACK);
        header.setBorder(new EmptyBorder(4, 8, 4, 0));
        headerBar = new JPanel(new BorderLayout());
        headerBar.setBackground(new Color(230, 230, 230));
        headerBar.add(header, BorderLayout.WEST);
        tableContainer.add(headerBar, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Phone", "Address"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        darkModeCheckbox = new JCheckBox("Dark mode");
        darkModeCheckbox.setOpaque(false);
        darkModeCheckbox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        darkModeCheckbox.setForeground(Color.WHITE);
        darkModeCheckbox.addActionListener(e -> toggleDarkMode(darkModeCheckbox.isSelected()));
        bottom.add(darkModeCheckbox, BorderLayout.WEST);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Georgia", Font.BOLD, 16));
        backBtn.setBackground(new Color(100, 100, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> frame.switchPanel("OwnerPanel"));
        JPanel backWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backWrapper.setOpaque(false);
        backWrapper.add(backBtn);
        bottom.add(backWrapper, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);
    }

    void refresh() {
        tableModel.setRowCount(0);
        java.util.List<Purchase> history = frame.getPurchaseHistory();
        for (Purchase p : history) {
            String name = p.customerName == null ? "" : p.customerName;
            String phone = p.customerPhone == null ? "" : p.customerPhone;
            String addr = p.customerAddress == null ? "" : p.customerAddress;
            tableModel.addRow(new Object[]{name, phone, addr});
        }
    }

    private void toggleDarkMode(boolean dark) {
        if (dark) {
            setBackground(new Color(45, 45, 45));
            tableContainer.setBackground(new Color(55, 55, 55));
            tableContainer.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
            headerBar.setBackground(new Color(70, 70, 70));
            table.setBackground(new Color(30, 30, 30));
            table.setForeground(Color.WHITE);
            table.getTableHeader().setBackground(new Color(50, 50, 50));
            table.getTableHeader().setForeground(Color.WHITE);
            darkModeCheckbox.setForeground(Color.WHITE);
        } else {
            setBackground(new Color(60, 80, 140));
            tableContainer.setBackground(Color.WHITE);
            tableContainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
            headerBar.setBackground(new Color(230, 230, 230));
            table.setBackground(Color.WHITE);
            table.setForeground(Color.BLACK);
            table.getTableHeader().setBackground(new Color(240, 240, 240));
            table.getTableHeader().setForeground(Color.BLACK);
            darkModeCheckbox.setForeground(Color.WHITE);
        }
    }
}
