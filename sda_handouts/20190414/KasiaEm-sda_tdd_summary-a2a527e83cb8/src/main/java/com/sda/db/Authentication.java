package com.sda.db;

public class Authentication {
    private String loggedIn;
    private Database database;

    public Authentication(Database database) {
        this.database = database;
        this.loggedIn = null;
        this.loggedIn = null;
    }

    public void login(User user) {
        User userFromDB = null;
        if (user != null) {
            userFromDB = database.findUser(user.getLogin());
        }
        if (userFromDB == null) {
            throw new UserNotFoundException("User with this login not found.");
        }
        if (!userFromDB.getPassword().equals(user.getPassword())) {
            throw new AuthenticationException("Authentication failed. Wrong password.");
        }
        loggedIn = user.getLogin();
    }

    public void logout() {
        loggedIn = null;
    }

    public String getLoggedIn() {
        return loggedIn;
    }

}
