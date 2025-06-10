
import java.sql.Date;

public class Allocation {
    private int allocationId;
    private int studentId;
    private int roomId;
    private Date allocatedDate;

    public Allocation(int allocationId, int studentId, int roomId, Date allocatedDate) {
        this.allocationId = allocationId;
        this.studentId = studentId;
        this.roomId = roomId;
        this.allocatedDate = allocatedDate;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getRoomId() {
        return roomId;
    }

    public Date getAllocatedDate() {
        return allocatedDate;
    }
}

