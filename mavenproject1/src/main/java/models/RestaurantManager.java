package models;

public class RestaurantManager {

    private int managerId;
    private int restaurantId;
    private String restaurantName;
    private String name;
    private String phone;

    public RestaurantManager(int managerId, int restaurantId, String restaurantName, String name, String phone) {
        this.managerId = managerId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.name = name;
        this.phone = phone;
    }

    public int getManagerId() {
        return managerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}