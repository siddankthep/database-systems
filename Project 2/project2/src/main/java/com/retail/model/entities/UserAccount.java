package com.retail.model.entities;

import java.util.Date;

public class UserAccount {
    private int userId;
    private String username;
    private String passwordHash;
    private int roleId;
    private Date createdAt;
    private Date lastLogin;

    public UserAccount(int userId, String username, String passwordHash, int roleId, Date createdAt, Date lastLogin) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public UserAccount(String username, String passwordHash, int roleId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.createdAt = new Date();
        this.lastLogin = null;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
