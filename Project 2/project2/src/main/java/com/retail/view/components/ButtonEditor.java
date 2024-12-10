package com.retail.view.components;

import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ReviewDAO;
import com.retail.model.entities.Product;
import com.retail.model.services.ProductService;
import com.retail.model.services.ReviewService;
import com.retail.view.Customer.ReviewFrame;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;
    private String customerPhone;
    private Product product;
    private ProductService productService = new ProductService(new ProductDAO());
    private ReviewService reviewService = new ReviewService(new ReviewDAO());

    public ButtonEditor(JButton button, String customerPhone) {
        super();
        this.button = button;
        this.button.setOpaque(true);
        this.customerPhone = customerPhone;

        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "Review" : value.toString();
        button.setText(label);
        clicked = true;

        // Fetch product details from the table row
        try {

            int productID = Integer.parseInt(table.getValueAt(row, 0).toString());
            product = productService.getProductById(productID);
            String paymentStatus = table.getValueAt(row, 6).toString();
            if (paymentStatus.equals("Unpaid")) {
                JOptionPane.showMessageDialog(null, "Please pay for the product before reviewing.");
                clicked = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            // Check if the product has already been reviewed
            if (reviewService.isProductReviewed(product, customerPhone)) {
                JOptionPane.showMessageDialog(null, "You have already reviewed this product!");
            } else {
                // Open the ReviewFrame if not reviewed
                new ReviewFrame(customerPhone, product).setVisible(true);
            }
        }
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

}
