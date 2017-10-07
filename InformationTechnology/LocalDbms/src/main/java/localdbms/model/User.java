package localdbms.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private static final String LOGIN_PATTERN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}";
    private static final String PASS_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}";
    private static final String EMAIL_PATTERN = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}";

    private int id;
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
        if (checkField(login, LOGIN_PATTERN)) {
            this.login = login;
        } else {
            throw new IllegalArgumentException("Login is invalid");
        }
    }

    public void setPassword(String password) {
        if (checkField(password, PASS_PATTERN)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password is invalid");
        }
    }

    public void setEmail (String email) {
        if (checkField(email, EMAIL_PATTERN)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email is invalid");
        }
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private static boolean checkField(String field, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(field);
        return matcher.matches();
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
        if (this == o) return true;
        if (!(o instanceof User)) return false;

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
