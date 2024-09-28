import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton[] queryButtons = new JButton[9];
    private JTable resultTable;
    private JScrollPane scrollPane;
    private String[] columnNames = {}; 
    private Object[][] data = {}; 

    public View() {
        setTitle("HW6 Query Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 2));

        for (int i = 0; i < queryButtons.length; i++) {
            queryButtons[i] = new JButton("Query " + (i + 1));
            buttonPanel.add(queryButtons[i]);
        }

        resultTable = new JTable(data, columnNames);
        scrollPane = new JScrollPane(resultTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setButtonListener(int buttonIndex, ActionListener listener) {
        queryButtons[buttonIndex].addActionListener(listener);
    }

    public void updateTableData(Object[][] newData, String[] newColumnNames) {
        resultTable.setModel(new DefaultTableModel(newData, newColumnNames));
    }
}
