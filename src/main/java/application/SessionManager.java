package application;

import database.Users;

public class SessionManager {
    private static SessionManager instance;
    private Users loggedInUser;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setLoggedInUser(Users user) {
        this.loggedInUser = user;
    }

    public Users getLoggedInUser() {
        return loggedInUser;
    }


    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public void logout() {
        this.loggedInUser = null;
    }
}