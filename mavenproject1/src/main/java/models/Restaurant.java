package models;

public class Restaurant {

    private int restaurantId;
    private String name;
    private String phone;
    private String location;
    private double rating;

    public Restaurant(int restaurantId, String name, String phone, String location, double rating) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.rating = rating;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return restaurantId + " - " + name;
    }
}