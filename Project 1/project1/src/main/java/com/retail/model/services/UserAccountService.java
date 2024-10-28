package com.retail.model.services;

import java.sql.SQLException;

import com.retail.model.dao.UserAccountDAO;
import com.retail.model.entities.UserAccount;

public class UserAccountService {
    private final UserAccountDAO userDAO;

    public UserAccountService(UserAccountDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Authenticate user by checking username and password
    public boolean authenticate(String username, String password) throws SQLException {
        UserAccount user = userDAO.getUserByUsername(username);
        if (user != null) {
            return user.getPasswordHash().equals(password);
        }
        return false;
    }

    // Get the role of the authenticated user
    public int getUserRole(String username) throws SQLException {
        UserAccount user = userDAO.getUserByUsername(username);
        return user.getRoleId();
    }

    // Create a new user
    public void createUser(UserAccount user) throws SQLException {
        userDAO.insert(user);
    }
}
