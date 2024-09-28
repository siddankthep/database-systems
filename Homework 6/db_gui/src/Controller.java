import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private View view;

    private String[] queries = {
            "SELECT project.Name, project.leaderID, employee.Name, project.Cost FROM project INNER JOIN employee ON project.leaderID = employee.ID ORDER BY project.Cost DESC",
            "SELECT p.Name, AVG(a.Hours) AS AverageHours FROM assignment AS a INNER JOIN project AS p ON a.PID = p.ID GROUP BY p.Name",
            "SELECT e.Name, SUM(a.Hours) AS TotalHours FROM assignment AS a INNER JOIN employee AS e ON a.EID = e.ID GROUP BY e.Name",
            "SELECT e.Name, SUM(Charge) AS TotalCharge FROM assignment AS a INNER JOIN employee AS e ON a.EID = e.ID GROUP BY e.Name",
            "SELECT j.Title, COUNT(e.Name) AS NumberOfEmployees FROM job AS j INNER JOIN employee AS e ON j.ID = e.jobID GROUP BY j.Title",
            "SELECT e.Name, COUNT(e.Name) AS numProjects FROM assignment AS a INNER JOIN employee AS e ON a.EID = e.ID GROUP BY e.Name HAVING numProjects > 1",
            "SELECT Title, AVG(Charge) AS averagePayment FROM job AS j INNER JOIN employee AS e ON j.ID = e.jobID INNER JOIN assignment AS a ON e.ID = a.EID GROUP BY Title",
            "SELECT Title, AVG(Charge) AS averagePayment FROM job AS j INNER JOIN employee AS e ON j.ID = e.jobID INNER JOIN assignment AS a ON e.ID = a.EID GROUP BY Title ORDER BY averagePayment DESC LIMIT 1",
            "SELECT Name, SUM(Hours) AS totalHours FROM employee AS e INNER JOIN assignment AS a ON e.ID = a.EID GROUP BY Name ORDER BY totalHours DESC LIMIT 2",
    };

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        for (int i = 0; i < queries.length; i++) {
            int queryIndex = i;
            this.view.setButtonListener(i, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        ResultSet rs = model.executeQueryForResultSet(queries[queryIndex]);

                        Object[][] tableData = buildTableData(rs);
                        String[] columnNames = getColumnNames(rs);

                        view.updateTableData(tableData, columnNames);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    private Object[][] buildTableData(ResultSet rs) throws Exception {
        List<Object[]> rows = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i); 
            }
            rows.add(row);
        }

        Object[][] tableData = new Object[rows.size()][columnCount];
        return rows.toArray(tableData);
    }

    private String[] getColumnNames(ResultSet rs) throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];

        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        return columnNames;
    }
}
