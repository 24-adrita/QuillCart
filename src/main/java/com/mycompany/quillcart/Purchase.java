package com.mycompany.quillcart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a purchase transaction made by a customer.
 */
public class Purchase {
    final int id;
    final LinkedHashMap<String, Line> lines;
    final double totalTaka;
    final boolean promoApplied;
    final String promoCode;
    final String timestamp;

    final String customerName;
    final String customerPhone;
    final String customerAddress;

    
    static class Line {
        final int qty;
        final double unitTaka;

        Line(int q, double u) {
            qty = q;
            unitTaka = u;
        }
    }

  
    public Purchase(int id, Map<Book, Integer> cart, double totalTaka, boolean promoApplied,
                    String promoCode, String customerName, String customerPhone, String customerAddress) {
        this.id = id;
        this.lines = new LinkedHashMap<>();
        for (Map.Entry<Book, Integer> e : cart.entrySet()) {
            lines.put(e.getKey().title, new Line(e.getValue(), e.getKey().priceTaka));
        }
        this.totalTaka = totalTaka;
        this.promoApplied = promoApplied;
        this.promoCode = promoCode;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
    }

    
    public Purchase(int id, Map<Book, Integer> cart, double totalTaka, boolean promoApplied, String promoCode) {
        this(id, cart, totalTaka, promoApplied, promoCode, "", "", "");
    }
}