package org.example.controller;

import org.example.database.DbUser;
import org.example.model.User;

public class UserController {
    private DbUser dbUser;

    public UserController() {
        this.dbUser = new DbUser();
    }

    public void createUser(User user){
        dbUser.createUser(user);
    }

    public boolean deleteUser(User user){
        return dbUser.deleteUser(user);
    }

    // TODO: Implement update user

    public boolean verifyUserLogin(User user){
        return dbUser.verifyUserLogin(user);
    }
}