# 🏨 Hostel Room Allocation System

A Java Swing + MySQL project for efficiently managing hostel room allocations for students. Built with a clean GUI, robust backend, and database integration.

---

## 🔧 Tech Stack

- **Java** (JDK 8+)
- **Swing** (Java GUI)
- **JDBC** (Java Database Connectivity)
- **MySQL** (Database)
- **Visual Studio Code / IntelliJ** (Recommended IDEs)

---

## 🧠 Features

✅ Add, update, and delete student records  
✅ View all students, rooms, and room allocations  
✅ Allocate rooms to students  
✅ Realtime GUI with input validation  
✅ Robust error handling and database connectivity  
✅ Modular DAO structure (StudentDAO, RoomDAO, AllocationDAO)

---

## 📁 Project Structure

Hostel-Room-Allocation/
├── src/
│ ├── Student.java
│ ├── Room.java
│ ├── Allocation.java
│ ├── StudentDAO.java
│ ├── RoomDAO.java
│ ├── AllocationDAO.java
│ ├── DatabaseConnection.java
│ └── HostelGUI.java
├── database.sql
├── README.md

---

## 🗃️ Database Setup

1. Ensure **MySQL** is running (e.g., via **XAMPP** or standalone).
2. Open MySQL Command Line or phpMyAdmin.
3. Run the following commands:

CREATE DATABASE hostel_db;
USE hostel_db;

🚀 How to Run the Project
1. Open the project folder in VS Code or IntelliJ.

2. Ensure your MySQL is running and the database is set up.

3. Open and run:
src/HostelGUI.java
4. Interact via the GUI to manage students, rooms, and allocations.

👨‍💻 Contributors
-Nikhil Prasad
-Satyendra Tripathi
-Mandeep Singh
-Siddharth Singh

📌 Notes
* The database.sql file must be used to restore the correct schema and data.
* Avoid editing .java files without understanding DAO dependencies.
* For any issues, ensure database credentials are correct in DatabaseConnection.java.




   

