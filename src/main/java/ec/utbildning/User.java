package ec.utbildning;

public class User {
    private String username;
    private String password;
    private Enum<UserRole> userRoleEnum;

    public User(String username, String password, Enum<UserRole> userRoleEnum) {
        this.username = username;
        this.password = password;
        this.userRoleEnum = userRoleEnum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Enum<UserRole> getUserRoleEnum() {
        return userRoleEnum;
    }

    public void setUserRoleEnum(Enum<UserRole> userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }
}
