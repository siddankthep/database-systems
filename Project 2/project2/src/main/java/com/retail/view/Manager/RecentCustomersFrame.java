package com.retail.view.Manager;

import javax.swing.*;
import com.retail.controller.RecentCustomerController;
import java.awt.*;

public class RecentCustomersFrame extends JFrame {
    private RecentCustomerController controller = new RecentCustomerController();

    public RecentCustomersFrame() {

        // Frame properties
        setTitle("Recent Customer Purchases");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTable table = controller.createRecentCustomersTable();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

}
