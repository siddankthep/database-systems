import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Model {
    private Connection connection;

    public Model() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw4_db", "sid", "Siddankthep03!");
            System.out.println("Connected to the database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String executeQuery(String query) {
        StringBuilder result = new StringBuilder();
        result.append("<html>");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
            result.append("Error executing query: ").append(e.getMessage());
        }
        return result.toString();
    }
}
