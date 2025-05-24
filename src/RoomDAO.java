import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    public static List<String> getRooms() {
        List<String> rooms = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM rooms";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(String.format("ID: %d, Room No: %s, Capacity: %d, Available: %s",
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getInt("capacity"),
                        rs.getBoolean("available") ? "Yes" : "No"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
