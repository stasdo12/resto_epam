package com.epam.donetc.restaurant.database.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * User entity enum. Matches table 'users' in database.
 *
 * @author Stanislav Donetc
 * @version 1.0
 */
public class User implements Serializable  {
    private int id;
    private String login;
    private final String password;
    private final int roleId;

    private String email;




    public User(int id, String login, String password, int roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
       }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", roleId=" + roleId + email+
                '}';
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && roleId == user.roleId && login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roleId);
    }
}