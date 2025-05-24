import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllocationDAO {
    public static void allocateRoom(int studentId, int roomId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO allocations (student_id, room_id, allocated_date) VALUES (?, ?, CURDATE())";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, studentId);
            insertStmt.setInt(2, roomId);
            insertStmt.executeUpdate();

            String updateRoomQuery = "UPDATE rooms SET available = false WHERE room_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateRoomQuery);
            updateStmt.setInt(1, roomId);
            updateStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllocations() {
        List<String> allocations = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT a.allocation_id, s.name, r.room_number, a.allocated_date FROM allocations a JOIN students s ON a.student_id = s.student_id JOIN rooms r ON a.room_id = r.room_id";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                allocations.add(String.format("Alloc ID: %d, Student: %s, Room: %s, Date: %s",
                        rs.getInt("allocation_id"),
                        rs.getString("name"),
                        rs.getString("room_number"),
                        rs.getDate("allocated_date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allocations;
    }
}
