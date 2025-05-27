-- Create the students table
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL
);

-- Create the rooms table
CREATE TABLE IF NOT EXISTS rooms (
    room_number INT PRIMARY KEY,
    capacity INT NOT NULL,
    occupied INT DEFAULT 0
);

-- Create the allocations table
CREATE TABLE IF NOT EXISTS allocations (
    allocation_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    room_number INT,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (room_number) REFERENCES rooms(room_number) ON DELETE CASCADE
);

-- Insert some sample students
INSERT INTO students (name, age, gender) VALUES
('Amit Sharma', 19, 'Male'),
('Priya Mehta', 20, 'Female'),
('Rahul Verma', 18, 'Male');

-- Insert some sample rooms
INSERT INTO rooms (room_number, capacity, occupied) VALUES
(101, 2, 1),
(102, 2, 0),
(103, 1, 1);

-- Insert sample room allocations
INSERT INTO allocations (student_id, room_number) VALUES
(1, 101),
(2, 103);
