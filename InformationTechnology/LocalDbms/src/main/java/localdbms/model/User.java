package localdbms.model;

import static localdbms.model.FieldValidator.*;

public class User {

    private static final String LOGIN_PATTERN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}";
    private static final String PASS_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}";
    private static final String EMAIL_PATTERN = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}";
    static final double MIN_SCORE = 60.0;
    static final double MAX_SCORE = 100.0;

    private int id;
    private double averageScore;
    private String login;
    private String password;
    private String email;
    private Role role;

    public User() {
        this.role = Role.VISITOR;
    }

    public User(String login, String password, String email) throws IllegalArgumentException {
        this.setLogin(login);
        this.setPassword(password);
        this.setEmail(email);
        this.setRole(Role.VISITOR);
    }

    public void setLogin(String login) throws IllegalArgumentException, UnsupportedOperationException {
        if (this.login != null) {
            throw new UnsupportedOperationException("Can not change login");
        }
        if (isTextFieldValid(login, LOGIN_PATTERN)) {
            this.login = login;
        } else {
            throw new IllegalArgumentException("Login is invalid");
        }
    }

    public void setPassword(String password) throws IllegalArgumentException {
        if (isTextFieldValid(password, PASS_PATTERN)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password is invalid");
        }
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (isTextFieldValid(email, EMAIL_PATTERN)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email is invalid");
        }
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
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

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) throws IllegalArgumentException {
        if (isNumberFieldValid(averageScore, MIN_SCORE, MAX_SCORE))
            this.averageScore = averageScore;
        else {
            throw new IllegalArgumentException("Average score is invalid");
        }
    }

    public void setId(int id) {
        this.id = id;
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
        if (this == o){
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return login.equals(user.login) && password.equals(user.password) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
