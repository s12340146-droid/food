package models;

public class UserAccount {

    private int userId;
    private String username;
    private String email;
    private String password;
    private int roleId;
    private String roleName;
    private Integer customerId;
    private Integer managerId;
    private Integer driverId;

    public UserAccount(int userId, String username, String email, String password,
                       int roleId, String roleName,
                       Integer customerId, Integer managerId, Integer driverId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.roleName = roleName;
        this.customerId = customerId;
        this.managerId = managerId;
        this.driverId = driverId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public Integer getDriverId() {
        return driverId;
    }
}