import java.sql.*;

public class TestAddStudent {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hostel_db";
        String user = "root";
        String password = "mysql";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO students (name, email, contact) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "John Doe");
            stmt.setString(2, "john@example.com");
            stmt.setString(3, "1234567890");

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
