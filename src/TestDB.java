import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to your database (change username/password if needed)
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hostel_db?useSSL=false", "root", "mysql");

            // Create statement to execute query
            Statement stmt = con.createStatement();

            // Execute query to get all students
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            // Print results
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id") +
                                   ", Name: " + rs.getString("name") +
                                   ", Email: " + rs.getString("email") +
                                   ", Contact: " + rs.getString("contact"));
            }

            // Close connection
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
