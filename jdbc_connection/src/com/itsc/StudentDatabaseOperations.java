package com.itsc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentDatabaseOperations {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/StudentsDB";
        String username = "root";
        String password = "password";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
        	
        	connection.setAutoCommit(true);

            statement.execute("CREATE TABLE IF NOT EXISTS students (id INT PRIMARY KEY, firstname VARCHAR(255), lastname VARCHAR(255), grade INT)");
            
            System.out.println("Inserting sample data...");
            insertSampleData(connection);
            
            System.out.println("Retrieving data...");
            retrieveData(connection);
            
            System.out.println("Updating student name...");
            updateStudentName(connection, 1, "UpdatedFirstName");
            
            System.out.println("Retrieving data...");
            retrieveData(connection);
            
            System.out.println("Deleting student with id 2...");
            deleteStudent(connection, 2);
            
            System.out.println("Retrieving data...");
            retrieveData(connection);
            
            System.out.println("Calculating average grade...");
            calculateAverageGrade(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertSampleData(Connection connection) {
        try {
            // Insert a single row (with id 1), update if exists
            PreparedStatement insertSingle = connection.prepareStatement(
                "INSERT INTO students (id, firstname, lastname, grade) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE firstname = VALUES(firstname), lastname = VALUES(lastname), grade = VALUES(grade)"
            );
            insertSingle.setInt(1, 1);
            insertSingle.setString(2, "Firstname1");
            insertSingle.setString(3, "Lastname1");
            insertSingle.setInt(4, 90);
            insertSingle.executeUpdate();
            insertSingle.close();

            // Insert ten more rows using a loop, update if any of these rows exist
            PreparedStatement insertMultiple = connection.prepareStatement(
                "INSERT INTO students (id, firstname, lastname, grade) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE firstname = VALUES(firstname), lastname = VALUES(lastname), grade = VALUES(grade)"
            );

            for (int i = 2; i <= 11; i++) {
                insertMultiple.setInt(1, i);
                insertMultiple.setString(2, "Firstname" + i);
                insertMultiple.setString(3, "Lastname" + i);
                insertMultiple.setInt(4, 70 + i); // Assigning different grades for variety
                insertMultiple.executeUpdate();
            }
            insertMultiple.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void retrieveData(Connection connection) {
        String selectSQL = "SELECT * FROM students LIMIT 5";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                int grade = resultSet.getInt("grade");
                System.out.println("ID: " + id + ", Name: " + firstname + " " + lastname + ", Grade: " + grade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateStudentName(Connection connection, int id, String newFirstName) {
        String updateSQL = "UPDATE students SET firstname = ? WHERE id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
            updateStatement.setString(1, newFirstName);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent(Connection connection, int id) {
        String deleteSQL = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void calculateAverageGrade(Connection connection) {
        String averageSQL = "SELECT AVG(grade) AS average_grade FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(averageSQL)) {

            if (resultSet.next()) {
                double averageGrade = resultSet.getDouble("average_grade");
                System.out.println("Average Grade: " + averageGrade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
