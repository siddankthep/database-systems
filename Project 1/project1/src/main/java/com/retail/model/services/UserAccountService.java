package com.retail.model.services;

public class UserAccountService {
    public static void main(String[] args) {
        String subtotalLabel = "Subtotal: $999.99";
        String[] subtotalStrings = subtotalLabel.split("\\$");
        String subtotalString = subtotalStrings[subtotalStrings.length - 1];
        Double totalAmount = Double.parseDouble(subtotalString);
        System.out.println(totalAmount);
    }
}
