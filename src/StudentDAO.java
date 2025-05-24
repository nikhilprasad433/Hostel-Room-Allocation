import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hostel_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "mysql";

    // ADD student
    public static boolean addStudent(String name, String email, String contact) {
    String sql = "INSERT INTO students (name, email, contact) VALUES (?, ?, ?)";
    
    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, contact);
        
        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0; // true if insert succeeded
    
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // insert failed
    }
}


  // UPDATE student
public static boolean updateStudent(int id, String name, String email, String contact) {
    String sql = "UPDATE students SET name=?, email=?, contact=? WHERE student_id=?";  // use student_id

    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, contact);
        pstmt.setInt(4, id);

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// DELETE student
public static boolean deleteStudent(int id) {
    String sql = "DELETE FROM students WHERE student_id=?";  // use student_id

    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id);

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// GET all students
public static List<String> getStudents() {
    List<String> students = new ArrayList<>();
    String sql = "SELECT * FROM students";

    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            int id = rs.getInt("student_id");  // use student_id here
            String name = rs.getString("name");
            String email = rs.getString("email");
            String contact = rs.getString("contact");

            students.add("ID: " + id + ", Name: " + name + ", Email: " + email + ", Contact: " + contact);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return students;
}
}