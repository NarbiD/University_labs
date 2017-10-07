package localdbms.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private int id;
    private String login;
    private String password;
    private String email;

    private Role role;

    public User() {
    }

    public User(String login, String password, String email) throws IllegalArgumentException {
    }

    public void setLogin(String login) throws IllegalArgumentException, UnsupportedOperationException {
    }

    public void setPassword(String password) {
    }

    public void setEmail (String email) {
    }

    public void setRole(Role role) {
    }

    private static boolean checkField(String field, String pattern) {
        return false;
    }

    public String getEmail() {
        return "";
    }

    public String getLogin() {
        return "";
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
