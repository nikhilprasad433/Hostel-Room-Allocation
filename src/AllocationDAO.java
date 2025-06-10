import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllocationDAO {

    // ✅ Allocate room to student
    public static boolean allocateRoom(int studentId, int roomId) {
        String insertAllocation = "INSERT INTO allocations (student_id, room_id) VALUES (?, ?)";
        String updateRoomAvailability = "UPDATE rooms SET available = FALSE WHERE room_id = ? AND available = TRUE";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // 1. Mark room as unavailable (only if available)
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateRoomAvailability)) {
                pstmtUpdate.setInt(1, roomId);
                if (pstmtUpdate.executeUpdate() == 0) {
                    conn.rollback();
                    System.err.println("Allocation failed: Room not available or doesn't exist.");
                    return false;
                }
            }

            // 2. Add allocation
            try (PreparedStatement pstmtInsert = conn.prepareStatement(insertAllocation)) {
                pstmtInsert.setInt(1, studentId);
                pstmtInsert.setInt(2, roomId);
                if (pstmtInsert.executeUpdate() == 0) {
                    conn.rollback();
                    System.err.println("Allocation failed: Unable to insert allocation.");
                    return false;
                }
            }

            conn.commit(); // All successful
            return true;

        } catch (SQLException e) {
            System.err.println("SQL Error during allocation: " + e.getMessage());
            return false;
        }
    }

    // ✅ Get all allocations
    public static List<String> getAllocations() {
        List<String> allocations = new ArrayList<>();
        String sql = "SELECT a.allocation_id, s.name AS student_name, r.room_number " +
                     "FROM allocations a " +
                     "JOIN students s ON a.student_id = s.student_id " +
                     "JOIN rooms r ON a.room_id = r.room_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                allocations.add(String.format("Allocation ID: %d, Student: %s, Room: %s",
                        rs.getInt("allocation_id"),
                        rs.getString("student_name"),
                        rs.getString("room_number")));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching allocations: " + e.getMessage());
        }

        return allocations;
    }

    // ✅ (Optional) Check if student is already allocated
    public static boolean isStudentAllocated(int studentId) {
        String sql = "SELECT 1 FROM allocations WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error checking student allocation: " + e.getMessage());
            return false;
        }
    }

    // ✅ (Optional) Deallocate room (innovation/future use)
    public static boolean deallocateRoom(int studentId) {
        String getRoomIdSql = "SELECT room_id FROM allocations WHERE student_id = ?";
        String deleteAllocationSql = "DELETE FROM allocations WHERE student_id = ?";
        String updateRoomSql = "UPDATE rooms SET available = TRUE WHERE room_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            int roomId = -1;
            try (PreparedStatement getStmt = conn.prepareStatement(getRoomIdSql)) {
                getStmt.setInt(1, studentId);
                ResultSet rs = getStmt.executeQuery();
                if (rs.next()) {
                    roomId = rs.getInt("room_id");
                } else {
                    conn.rollback();
                    System.err.println("Deallocation failed: No allocation found for student.");
                    return false;
                }
            }

            try (PreparedStatement delStmt = conn.prepareStatement(deleteAllocationSql)) {
                delStmt.setInt(1, studentId);
                if (delStmt.executeUpdate() == 0) {
                    conn.rollback();
                    System.err.println("Deallocation failed: Could not delete allocation.");
                    return false;
                }
            }

            try (PreparedStatement updStmt = conn.prepareStatement(updateRoomSql)) {
                updStmt.setInt(1, roomId);
                updStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error during deallocation: " + e.getMessage());
            return false;
        }
    }
}