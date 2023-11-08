package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    Connection dbConnection = null;
    public Connection getConnection(){
        // Establishing database connection -- adjust values below to match your database
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url =
                    "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=Hashmap-Test;" +
                    "encrypt=false";

            String username = "sa";
            String password = "Database123";

            dbConnection = DriverManager.getConnection(url, username, password);

            System.out.println("Connection to the database was established");

            return dbConnection;
        }
        catch (SQLException | ClassNotFoundException e ){
            System.out.println("Couldn't establish a connection to the database");
            e.printStackTrace();
        }
        return null;
    }
}