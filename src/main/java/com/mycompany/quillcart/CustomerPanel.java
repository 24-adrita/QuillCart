package com.mycompany.quillcart;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerPanel extends JPanel {
    private final MainWindow frame;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> bookCombo;
    private JLabel stockLabel;
    private JLabel priceLabel;
    private JTextField quantityField;
    private JTextField promoCodeField;
    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;
    private final Map<Book, Integer> cart = new LinkedHashMap<>();

    public CustomerPanel(MainWindow frame) {
        this.frame = frame;

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(0x339999)); // medium-dark teal
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Customer Shopping", JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2, 20, 20));
        content.setOpaque(false);

        // Left side - Form
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Georgia", Font.BOLD, 16);
        Color labelColor = Color.WHITE;

        int row = 0;

        // Category
        gbc.gridx = 0; gbc.gridy = row;
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(labelFont);
        categoryLabel.setForeground(labelColor);
        topPanel.add(categoryLabel, gbc);

        categoryCombo = new JComboBox<>(frame.getCategoryBooks().keySet().toArray(new String[0]));
        categoryCombo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        topPanel.add(categoryCombo, gbc);

        // Book
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        JLabel bookLabel = new JLabel("Book:");
        bookLabel.setFont(labelFont);
        bookLabel.setForeground(labelColor);
        topPanel.add(bookLabel, gbc);

        bookCombo = new JComboBox<>();
        bookCombo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        topPanel.add(bookCombo, gbc);

        // Stock Available
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        JLabel stockLbl = new JLabel("Stock Available:");
        stockLbl.setFont(labelFont);
        stockLbl.setForeground(labelColor);
        topPanel.add(stockLbl, gbc);

        stockLabel = new JLabel("-");
        stockLabel.setFont(labelFont);
        stockLabel.setForeground(labelColor);
        gbc.gridx = 1;
        topPanel.add(stockLabel, gbc);

        // Price
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        JLabel priceLbl = new JLabel("Price (৳):");
        priceLbl.setFont(labelFont);
        priceLbl.setForeground(labelColor);
        topPanel.add(priceLbl, gbc);

        priceLabel = new JLabel("-");
        priceLabel.setFont(labelFont);
        priceLabel.setForeground(labelColor);
        gbc.gridx = 1;
        topPanel.add(priceLabel, gbc);

        // Quantity
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        JLabel quantityLbl = new JLabel("Quantity:");
        quantityLbl.setFont(labelFont);
        quantityLbl.setForeground(labelColor);
        topPanel.add(quantityLbl, gbc);

        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        topPanel.add(quantityField, gbc);

        // Promo Code
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        JLabel promoLbl = new JLabel("Promo Code:");
        promoLbl.setFont(labelFont);
        promoLbl.setForeground(labelColor);
        topPanel.add(promoLbl, gbc);

        promoCodeField = new JTextField();
        promoCodeField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        topPanel.add(promoCodeField, gbc);

        // Add to Cart Button
        row++;
        gbc.gridx = 1; gbc.gridy = row;
        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.setPreferredSize(new Dimension(160, 40));
        addToCartBtn.setFont(new Font("Georgia", Font.BOLD, 14));
        addToCartBtn.setBackground(new Color(0x00CED1));
        addToCartBtn.setForeground(Color.BLACK);
        addToCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addToCartBtn.setFocusPainted(false);
        addToCartBtn.addActionListener(e -> addToCartAction());
        topPanel.add(addToCartBtn, gbc);

        content.add(topPanel);

        // Right side - Cart Panel
        JPanel cartPanel = new JPanel(new BorderLayout(10, 10));
        cartPanel.setOpaque(false);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Georgia", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(cartList);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel cartBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartBtns.setOpaque(false);

        JButton removeBtn = new JButton("Remove");
        JButton checkoutBtn = new JButton("Checkout");
        JButton historyBtn = new JButton("View History");
        JButton backBtn = new JButton("Back");

        removeBtn.setPreferredSize(new Dimension(100, 30));
        checkoutBtn.setPreferredSize(new Dimension(120, 40));
        historyBtn.setPreferredSize(new Dimension(120, 30));
        backBtn.setPreferredSize(new Dimension(100, 30));

        removeBtn.setBackground(new Color(200, 50, 50));
        checkoutBtn.setBackground(new Color(0x00CED1));
        historyBtn.setBackground(new Color(200, 200, 200));
        backBtn.setBackground(Color.WHITE);

        removeBtn.setForeground(Color.WHITE);
        checkoutBtn.setForeground(Color.BLACK);
        historyBtn.setForeground(Color.BLACK);
        backBtn.setForeground(Color.BLACK);

        removeBtn.setFocusPainted(false);
        checkoutBtn.setFocusPainted(false);
        historyBtn.setFocusPainted(false);
        backBtn.setFocusPainted(false);

        removeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        historyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        removeBtn.addActionListener(e -> removeSelected());
        checkoutBtn.addActionListener(e -> checkoutAction());
        historyBtn.addActionListener(e -> frame.openHistory("CustomerPanel"));
        backBtn.addActionListener(e -> {
            clearCart();
            frame.switchPanel("Welcome");
        });

        cartBtns.add(historyBtn);
        cartBtns.add(removeBtn);
        cartBtns.add(checkoutBtn);
        cartBtns.add(backBtn);
        cartPanel.add(cartBtns, BorderLayout.SOUTH);

        content.add(cartPanel);
        add(content, BorderLayout.CENTER);

        categoryCombo.addActionListener(e -> updateBooks());
        bookCombo.addActionListener(e -> updateBookInfo());
        updateBooks();
    }

    public void clearCart() {
        cart.clear();
        if (cartListModel != null) cartListModel.clear();
        if (promoCodeField != null) promoCodeField.setText("");
        if (quantityField != null) quantityField.setText("");
    }

    void refreshAll() {
        categoryCombo.setModel(new DefaultComboBoxModel<>(frame.getCategoryBooks().keySet().toArray(new String[0])));
        updateBooks();
        refreshCartList();
    }

    private void updateBooks() {
        bookCombo.removeAllItems();
        String category = (String) categoryCombo.getSelectedItem();
        if (category == null) return;
        for (Book b : frame.getCategoryBooks().get(category)) bookCombo.addItem(b.title);
        updateBookInfo();
    }

    private void updateBookInfo() {
        String category = (String) categoryCombo.getSelectedItem();
        int index = bookCombo.getSelectedIndex();
        if (category == null || index < 0) {
            stockLabel.setText("-");
            priceLabel.setText("-");
            return;
        }
        Book book = frame.getCategoryBooks().get(category).get(index);
        stockLabel.setText(String.valueOf(getAvailableStock(book)));
        priceLabel.setText(String.format("%.2f", book.priceTaka));
    }

    private int getAvailableStock(Book book) {
        int inCart = cart.getOrDefault(book, 0);
        return book.stock - inCart;
    }

    private void addToCartAction() {
        String quantityStr = quantityField.getText().trim();
        if (!quantityStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Enter a valid positive quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String category = (String) categoryCombo.getSelectedItem();
        int index = bookCombo.getSelectedIndex();
        if (category == null || index < 0) {
            JOptionPane.showMessageDialog(this, "Please select a category and book first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Book book = frame.getCategoryBooks().get(category).get(index);
        int available = getAvailableStock(book);
        if (available < quantity) {
            JOptionPane.showMessageDialog(this, "Not enough stock available. Remaining: " + available,
                    "Stock Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cart.put(book, cart.getOrDefault(book, 0) + quantity);
        refreshCartList();
        quantityField.setText("");
        updateBookInfo();
    }

    private void removeSelected() {
        int idx = cartList.getSelectedIndex();
        if (idx < 0) return;

        int i = 0;
        Book toRemove = null;
        for (Book b : cart.keySet()) {
            if (i == idx) {
                toRemove = b;
                break;
            }
            i++;
        }
        if (toRemove != null) {
            cart.remove(toRemove);
            refreshCartList();
            updateBookInfo();
        }
    }

    private void refreshCartList() {
        cartListModel.clear();
        for (Map.Entry<Book, Integer> entry : cart.entrySet()) {
            Book b = entry.getKey();
            int qty = entry.getValue();
            cartListModel.addElement(b.title + " ×" + qty + " — " + String.format("%.2f", b.priceTaka * qty));
        }
    }

    private void checkoutAction() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String entered = promoCodeField.getText().trim();
        boolean promoApplied = false;

        if (!entered.isEmpty()) {
            if (entered.equalsIgnoreCase(frame.getPromoCode())) {
                promoApplied = true;
            } else {
                JOptionPane.showMessageDialog(this, "The promo code is incorrect.",
                        "Invalid Promo Code", JOptionPane.WARNING_MESSAGE);
                promoCodeField.setText("");
                return;
            }
        }

        frame.setPendingOrder(new LinkedHashMap<>(cart), promoApplied, entered);
        frame.switchPanel("Checkout");
    }
}
