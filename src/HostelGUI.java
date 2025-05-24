import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HostelGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hostel Room Allocation");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Hostel Room Allocation");

        // Input fields
        JLabel studentIdLabel = new JLabel("Student ID:");
        JTextField studentIdField = new JTextField(5);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(10);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);
        JLabel contactLabel = new JLabel("Contact:");
        JTextField contactField = new JTextField(10);
        JLabel roomIdLabel = new JLabel("Room ID:");
        JTextField roomIdField = new JTextField(5);

        // Buttons
        JButton addStudentBtn = new JButton("Add Student");
        JButton updateStudentBtn = new JButton("Update Student");
        JButton deleteStudentBtn = new JButton("Delete Student");
        JButton allocateBtn = new JButton("Allocate Room");
        JButton showStudentsBtn = new JButton("Show Students");
        JButton showRoomsBtn = new JButton("Show Rooms");
        JButton showAllocationsBtn = new JButton("Show Allocations");

        // Output area
        JTextArea displayArea = new JTextArea(12, 45);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add Student
        addStudentBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            if (name.isEmpty() || email.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Fill all student details.");
            } else {
                boolean success = StudentDAO.addStudent(name, email, contact);
                JOptionPane.showMessageDialog(frame, success ? "Student added!" : "Failed to add.");
            }
        });

        // Update Student
        updateStudentBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(studentIdField.getText());
                String name = nameField.getText();
                String email = emailField.getText();
                String contact = contactField.getText();
                boolean success = StudentDAO.updateStudent(id, name, email, contact);
                JOptionPane.showMessageDialog(frame, success ? "Student updated!" : "Failed to update.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input for update.");
            }
        });

        // Delete Student
        deleteStudentBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(studentIdField.getText());
                boolean success = StudentDAO.deleteStudent(id);
                JOptionPane.showMessageDialog(frame, success ? "Student deleted!" : "Failed to delete.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ID.");
            }
        });

        // Allocate Room
        allocateBtn.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int roomId = Integer.parseInt(roomIdField.getText());
                AllocationDAO.allocateRoom(studentId, roomId);
                JOptionPane.showMessageDialog(frame, "Room allocated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid IDs for allocation.");
            }
        });

        // Display buttons
        showStudentsBtn.addActionListener(e -> {
            displayArea.setText("");
            List<String> students = StudentDAO.getStudents();
            students.forEach(s -> displayArea.append(s + "\n"));
        });

        showRoomsBtn.addActionListener(e -> {
            displayArea.setText("");
            List<String> rooms = RoomDAO.getRooms();
            rooms.forEach(r -> displayArea.append(r + "\n"));
        });

        showAllocationsBtn.addActionListener(e -> {
            displayArea.setText("");
            List<String> allocations = AllocationDAO.getAllocations();
            allocations.forEach(a -> displayArea.append(a + "\n"));
        });

        // Add to frame
        frame.add(welcomeLabel);
        frame.add(studentIdLabel);
        frame.add(studentIdField);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(contactLabel);
        frame.add(contactField);
        frame.add(roomIdLabel);
        frame.add(roomIdField);
        frame.add(addStudentBtn);
        frame.add(updateStudentBtn);
        frame.add(deleteStudentBtn);
        frame.add(allocateBtn);
        frame.add(showStudentsBtn);
        frame.add(showRoomsBtn);
        frame.add(showAllocationsBtn);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}
