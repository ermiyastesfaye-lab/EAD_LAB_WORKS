package com.itsc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
public class DBConnectionManager {
    private String url = "jdbc:mysql://localhost:3306/BookstoreDB";
    private String username = "root";
    private String password = "password";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }

        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        DBConnectionManager dbConnectionManager = new DBConnectionManager();

        try (Connection connection = dbConnectionManager.getConnection()) {
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}