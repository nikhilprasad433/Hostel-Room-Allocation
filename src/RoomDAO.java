import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // Get all rooms
    public static List<String> getRooms() {
        List<String> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(String.format("ID: %d, Room No: %s, Capacity: %d, Available: %s",
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getInt("capacity"),
                        rs.getBoolean("available") ? "Yes" : "No"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving rooms: " + e.getMessage());
        }

        return rooms;
    }

    // Add a room
    public static boolean addRoom(String roomNumber, int capacity, boolean available) {
        String sql = "INSERT INTO rooms (room_number, capacity, available) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomNumber);
            pstmt.setInt(2, capacity);
            pstmt.setBoolean(3, available);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

    // ✅ Get room by ID (used before allocation)
    public static String getRoomById(int roomId) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String roomNo = rs.getString("room_number");
                    int capacity = rs.getInt("capacity");
                    boolean available = rs.getBoolean("available");

                    return "ID: " + roomId + ", Room No: " + roomNo + ", Capacity: " + capacity + ", Available: " + (available ? "Yes" : "No");
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving room by ID: " + e.getMessage());
            return null;
        }
    }

    // ✅ Optional: Check if a room exists
    public static boolean roomExists(int roomId) {
        String sql = "SELECT 1 FROM rooms WHERE room_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error checking room existence: " + e.getMessage());
            return false;
        }
    }
}
