package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class DbConnection {
    // Determining the maximum number of connections in the pool
    private static final int MAX_POOL_SIZE = 5;
    LinkedList<Connection> connectionPool = new LinkedList<>();

    public DbConnection() {
        connectionPool = new LinkedList<>();
        initializeConnectionPool();
    }

    public void initializeConnectionPool(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url =
                    "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=Hashmap-Test;" +
                    "encrypt=false";

            String username = "sa";
            String password = "Database123";

            for (int i = 0; i < MAX_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                connectionPool.add(connection);
            }
            System.out.println("Connection pool initialized successfully");
        }
        catch (SQLException | ClassNotFoundException e ){
            System.out.println("Error occurred while initializing connection pool");
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnectionFromPool(){
        while (connectionPool.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Error occurred while getting connection from pool");
                e.printStackTrace();
            }
        }
        return connectionPool.removeFirst();
    }

    public synchronized void releaseConnection(Connection connection){
        connectionPool.addLast(connection);
        notify();  // Lets the waiting threads know that a connection is available
    }
}