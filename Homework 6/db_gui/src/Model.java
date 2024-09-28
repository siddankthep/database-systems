import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

    public ResultSet executeQueryForResultSet(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
