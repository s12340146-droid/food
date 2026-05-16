package utils;

public class Session {

    private static int userId;
    private static int customerId;
    private static String username;
    private static String roleName;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Session.userId = userId;
    }

    public static int getCustomerId() {
        return customerId;
    }

    public static void setCustomerId(int customerId) {
        Session.customerId = customerId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static String getRoleName() {
        return roleName;
    }

    public static void setRoleName(String roleName) {
        Session.roleName = roleName;
    }

    public static void clear() {
        userId = 0;
        customerId = 0;
        username = null;
        roleName = null;
    }
}