package org.example.database;

import org.example.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DbUser {
    DbConnection dbConnection = new DbConnection();

    public void createUser(User user){
        Connection con = dbConnection.getConnectionFromPool();

        if (con != null) {
            try{
                // Setup of SQL Query
                String createUserSql = "INSERT INTO [User] (username, password) VALUES (?, ?)";
                // Execution of SQL Query
                PreparedStatement createUserStatement = con.prepareStatement(createUserSql);
                // Setting up parameter values
                createUserStatement.setString(1, user.getUsername());
                createUserStatement.setString(2, hashPassword(user.getPassword())); // Hashes the password before insertion
                // Saves the rows affected for checking
                int rowsAffected = createUserStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User "+ user.getUsername() + " was succesfully created");
                } else {
                    System.out.println("Failed to create the following user: " + user.getUsername()+ " with the following passowrd: " + user.getPassword());
                }

                createUserStatement.close();
                con.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean deleteUser(User user){
        Connection con = dbConnection.getConnectionFromPool();

        if (con != null) {
            try{
                String checkUserSql = "SELECT password FROM [User] WHERE username = ?";
                PreparedStatement checkUserStatement = con.prepareStatement(checkUserSql);
                checkUserStatement.setString(1, user.getUsername());
                ResultSet resultSet = checkUserStatement.executeQuery();

                if (resultSet.next()) {
                    String storedHashedPassword = resultSet.getString("password");

                    if (hashPassword(user.getPassword()).equals(storedHashedPassword)) {
                        String deleteUserSql = "DELETE FROM [User] WHERE username = ?";
                        PreparedStatement deleteUserStatement = con.prepareStatement(deleteUserSql);
                        deleteUserStatement.setString(1, user.getUsername());

                        int rowsAffected = deleteUserStatement.executeUpdate();

                        checkUserStatement.close();
                        deleteUserStatement.close();
                        con.close();

                        if (rowsAffected > 0) {
                            System.out.println("User " + user.getUsername() + " was succesfully eradicated");
                            return true;
                        } else {
                            System.out.println("Error occurred while trying to delete user");
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            catch(SQLException e){
                System.out.println("Error while trying to delete the user - Catch");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean verifyUserLogin(User user){
        Connection con = dbConnection.getConnectionFromPool();

        if (con != null) {
            try{
                String checkUserSql = "SELECT password FROM [User] WHERE username = ?";
                PreparedStatement checkUserStatement = con.prepareStatement(checkUserSql);
                checkUserStatement.setString(1, user.getUsername());
                ResultSet resultSet = checkUserStatement.executeQuery();

                if (resultSet.next()) {
                    String storedHashedPassword = resultSet.getString("password");

                    return hashPassword(user.getPassword()).equals(storedHashedPassword);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public String hashPassword(String password) {
        try{
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = sha256.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            // Could use BCrypt for a more secure hashing
            // Allowing the usage of salting and adaptive
            // That would be considered best practice
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}