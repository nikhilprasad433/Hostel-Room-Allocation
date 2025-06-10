import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

public class HostelGUI {
    private static JFrame frame;
    private static JTextField studentIdField, nameField, emailField, contactField, roomIdField;
    private static JTextArea displayArea;

    public static void main(String[] args) {
        frame = new JFrame("Hostel Room Allocation");
        frame.setSize(650, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Hostel Room Allocation");

        // Input fields
        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField(5);
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(10);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);
        JLabel contactLabel = new JLabel("Contact:");
        contactField = new JTextField(10);
        JLabel roomIdLabel = new JLabel("Room ID:");
        roomIdField = new JTextField(5);

        // Buttons
        JButton addStudentBtn = new JButton("Add Student");
        JButton updateStudentBtn = new JButton("Update Student");
        JButton deleteStudentBtn = new JButton("Delete Student");
        JButton searchStudentBtn = new JButton("Search Student");
        JButton clearFormBtn = new JButton("Clear Form");

        JButton addRoomBtn = new JButton("Add Room");
        JButton allocateBtn = new JButton("Allocate Room");

        JButton showStudentsBtn = new JButton("Show Students");
        JButton showRoomsBtn = new JButton("Show Rooms");
        JButton showAllocationsBtn = new JButton("Show Allocations");

        // Output area
        displayArea = new JTextArea(15, 55);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add Student
        addStudentBtn.addActionListener(e -> {
            if (!validateStudentInputs(false)) return;
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String contact = contactField.getText().trim();

            try {
                boolean success = StudentDAO.addStudent(name, email, contact);
                if (success) {
                    showMessage("Student added!");
                    clearInputs();
                } else {
                    showMessage("Failed to add student.");
                }
            } catch (Exception ex) {
                showMessage("Error while adding student: " + ex.getMessage());
            }
        });

        // Update Student
        updateStudentBtn.addActionListener(e -> {
            if (!validateStudentInputs(true)) return;
            try {
                int id = Integer.parseInt(studentIdField.getText().trim());
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String contact = contactField.getText().trim();

                boolean success = StudentDAO.updateStudent(id, name, email, contact);
                if (success) {
                    showMessage("Student updated!");
                    clearInputs();
                } else {
                    showMessage("Failed to update student.");
                }
            } catch (Exception ex) {
                showMessage("Error while updating student: " + ex.getMessage());
            }
        });

        // Delete Student
        deleteStudentBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(studentIdField.getText().trim());
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete student with ID " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = StudentDAO.deleteStudent(id);
                    if (success) {
                        showMessage("Student deleted!");
                        clearInputs();
                    } else {
                        showMessage("Failed to delete student.");
                    }
                }
            } catch (NumberFormatException ex) {
                showMessage("Invalid Student ID.");
            } catch (Exception ex) {
                showMessage("Error while deleting student: " + ex.getMessage());
            }
        });

        // Search Student
        searchStudentBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(studentIdField.getText().trim());
                String result = StudentDAO.getStudentById(id);
                if (result != null) {
                    displayArea.setText("Student Found:\n" + result);
                } else {
                    displayArea.setText("No student found with ID: " + id);
                }
            } catch (NumberFormatException ex) {
                showMessage("Enter a valid Student ID for search.");
            }
        });

        // Clear Form
        clearFormBtn.addActionListener(e -> clearInputs());

        // Add Room
        addRoomBtn.addActionListener(e -> {
            String roomNo = roomIdField.getText().trim();
            if (roomNo.isEmpty()) {
                showMessage("Enter a Room Number.");
                return;
            }
            try {
                // For demo, assume capacity 1 and available true by default
                boolean success = RoomDAO.addRoom(roomNo, 1, true);
                if (success) {
                    showMessage("Room added!");
                    clearInputs();
                } else {
                    showMessage("Failed to add room.");
                }
            } catch (Exception ex) {
                showMessage("Error adding room: " + ex.getMessage());
            }
        });

        // Allocate Room
        allocateBtn.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText().trim());
                int roomId = Integer.parseInt(roomIdField.getText().trim());
                boolean success = AllocationDAO.allocateRoom(studentId, roomId);
                showMessage(success ? "Room allocated!" : "Allocation failed.");
            } catch (NumberFormatException ex) {
                showMessage("Invalid IDs for allocation.");
            } catch (Exception ex) {
                showMessage("Error during allocation: " + ex.getMessage());
            }
        });

        // Show Students
        showStudentsBtn.addActionListener(e -> {
            displayArea.setText("");
            List<String> students = StudentDAO.getStudents();
            students.forEach(s -> displayArea.append(s + "\n"));
            displayArea.append("\nTotal Students: " + students.size());
        });

        // Show Rooms
        showRoomsBtn.addActionListener(e -> {
            displayArea.setText("");
            List<String> rooms = RoomDAO.getRooms();
            rooms.forEach(r -> displayArea.append(r + "\n"));
            displayArea.append("\nTotal Rooms: " + rooms.size());
        });

        // Show Allocations
        showAllocationsBtn.addActionListener(e -> {
            displayArea.setText("");
            List<String> allocations = AllocationDAO.getAllocations();
            allocations.forEach(a -> displayArea.append(a + "\n"));
            displayArea.append("\nTotal Allocations: " + allocations.size());
        });

        // Add components to frame
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
        frame.add(searchStudentBtn);
        frame.add(clearFormBtn);

        frame.add(addRoomBtn);
        frame.add(allocateBtn);

        frame.add(showStudentsBtn);
        frame.add(showRoomsBtn);
        frame.add(showAllocationsBtn);

        frame.add(scrollPane);

        frame.setVisible(true);
    }

    // Helper method to validate student input, with option to check if ID required (for update)
    private static boolean validateStudentInputs(boolean requireId) {
        if (requireId) {
            if (studentIdField.getText().trim().isEmpty()) {
                showMessage("Student ID is required.");
                return false;
            }
            try {
                Integer.parseInt(studentIdField.getText().trim());
            } catch (NumberFormatException e) {
                showMessage("Student ID must be a number.");
                return false;
            }
        }
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String contact = contactField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            showMessage("Please fill all student details.");
            return false;
        }

        if (!isValidEmail(email)) {
            showMessage("Enter a valid email address.");
            return false;
        }

        if (!isValidContact(contact)) {
            showMessage("Enter a valid 10-digit contact number.");
            return false;
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        // Simple regex for email validation
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }

    private static boolean isValidContact(String contact) {
        // Check if contact is 10 digits number
        return contact.matches("\\d{10}");
    }

    private static void clearInputs() {
        studentIdField.setText("");
        nameField.setText("");
        emailField.setText("");
        contactField.setText("");
        roomIdField.setText("");
    }

    private static void showMessage(String msg) {
        JOptionPane.showMessageDialog(frame, msg);
    }
}
