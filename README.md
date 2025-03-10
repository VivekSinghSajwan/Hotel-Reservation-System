# Hotel Management System

## Description
This is a simple Hotel Management System implemented in Java with MySQL as the database. It allows users to perform basic reservation-related operations such as reserving a room, viewing reservations, retrieving room numbers, updating reservation details, and deleting reservations.

## Features
- Reserve a room
- View all reservations
- Get room number by guest name
- Update reservation details
- Delete a reservation
- Exit the system gracefully

## Technologies Used
- Java
- MySQL
- JDBC (Java Database Connectivity)

## Prerequisites
Before running the project, ensure that you have the following installed:
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- MySQL JDBC Driver (`mysql-connector-java`)

## Database Setup
1. Create a MySQL database named `hotel_DB`.
2. Use the following SQL command to create the `Reservations` table:
   ```sql
   CREATE TABLE Reservations (
       Reservation_ID INT PRIMARY KEY,
       Room_No INT NOT NULL,
       Guest_Name VARCHAR(255) NOT NULL,
       Mobile_Number VARCHAR(20) NOT NULL,
       Reservation_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   ```
3. Update the database connection details in `Main.java`:
   ```java
   private static final String url = "jdbc:mysql://localhost:3306/hotel_DB";
   private static final String username = "root";
   private static final String password = "your_password";
   ```

## How to Run
1. Compile the Java file:
   ```sh
   javac Main.java
   ```
2. Run the program:
   ```sh
   java Main
   ```

## Usage
Once the program is running, you will see a menu with the following options:
1. Reserve a Room
2. View Reservations
3. Get Room Number
4. Update Reservations
5. Delete Reservations
0. Exit

Enter the corresponding number to perform the desired action.

## Code Structure
- `Main.java`: Contains the core logic for interacting with the database and handling user input.
- `reserveRoom()`: Adds a new reservation.
- `viewReservations()`: Displays all reservations.
- `getRoomNo()`: Retrieves the room number by guest name.
- `updateReservations()`: Updates reservation details.
- `deleteReservations()`: Deletes a reservation.
- `exit()`: Exits the program.

## Notes
- Make sure your MySQL server is running before executing the program.
- Use prepared statements instead of concatenated SQL queries to prevent SQL injection.
- Handle exceptions properly to ensure a smooth user experience.

