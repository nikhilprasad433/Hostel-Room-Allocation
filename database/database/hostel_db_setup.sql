-- Create the students table
CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    contact VARCHAR(15)
);

-- Create the rooms table
CREATE TABLE IF NOT EXISTS rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10),
    capacity INT,
    available TINYINT(1) DEFAULT 1
);

-- Create the allocations table
CREATE TABLE IF NOT EXISTS allocations (
    allocation_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    room_id INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- Insert some sample students
INSERT INTO students (name, email, contact) VALUES
('Amit Sharma', 'amit@example.com', '1234567890'),
('Priya Mehta', 'priya@example.com', '0987654321'),
('Rahul Verma', 'rahul@example.com', '1112223333');

-- Insert some sample rooms
INSERT INTO rooms (room_number, capacity, available) VALUES
('101', 2, 0),
('102', 2, 1),
('103', 1, 0);

-- Insert sample room allocations
INSERT INTO allocations (student_id, room_id) VALUES
(1, 1),
(2, 3);
