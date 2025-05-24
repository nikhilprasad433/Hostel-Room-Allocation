public class Room {
    private int roomId;
    private String roomNumber;
    private int capacity;
    private boolean available;

    public Room(int roomId, String roomNumber, int capacity, boolean available) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.available = available;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
