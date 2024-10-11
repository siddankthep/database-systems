package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JOptionPane;

import java.sql.PreparedStatement;

public class DB {
    private Connection connection;
    private static DB instance;

    private DB() {
    }

    public static synchronized DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public boolean login(String username, String password) {
        System.out.println(username + " " + password);
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw4_db", username, password);
            System.out.println("Connected to the database");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error: User not found or invalid credentials. Please try again.",
                    "ERROR: Invalid credentials", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean createProject(int id, String name, int leaderID, float cost) {
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO project (ID, Name, leaderID, Cost) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, leaderID);
            stmt.setFloat(4, cost);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Project added successfully");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not add project, please try again");
            e.printStackTrace();
            return false;
        }
    }

    public boolean createEmployee(int id, String name, int jobID) {
        try {
            // Check if jobID exists in the job table
            PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM job WHERE ID = ?");
            checkStmt.setInt(1, jobID);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // jobID exists, proceed with insertion
                PreparedStatement stmt = connection
                        .prepareStatement("INSERT INTO employee (ID, Name, jobID) VALUES (?, ?, ?)");
                stmt.setInt(1, id);
                stmt.setString(2, name);
                stmt.setInt(3, jobID);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Employee added successfully");
                return true;
            } else {
                // jobID does not exist
                JOptionPane.showMessageDialog(null, "Invalid jobID. Please provide a valid jobID.");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not add employee, please try again");
            e.printStackTrace();
            return false;
        }
    }

    public String listEmployees(String projectName) {

        StringBuilder result = new StringBuilder();
        result.append("<html>");
        result.append("Employees working on project: ").append(projectName).append("<br><br>");
        result.append("Name | Hours | Payment <br>");

        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT  e.Name, a.Hours, a.Charge AS Payment FROM  employee e JOIN  assignment a ON e.ID = a.EID JOIN  project p ON p.ID = a.PID WHERE p.Name = ?;");
            stmt.setString(1, projectName);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    result.append(rs.getString(i)).append(" | ");
                }
                result.append("<br>");
            }
            result.append("</html>");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error executing query: " + e.getMessage());
        }
        return result.toString();
    }
}
