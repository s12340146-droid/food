package models;

public class Customer {

    private int customerId;
    private String username;
    private String name;
    private String phone;
    private String email;
    private String password;

    public Customer(int customerId, String username, String name, String phone, String email, String password) {
        this.customerId = customerId;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return customerId + " - " + name + " (" + username + ")";
    }
}