package models;

public class CustomerReviewRestaurant {

    private int restaurantId;
    private String restaurantName;

    public CustomerReviewRestaurant(int restaurantId, String restaurantName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public String toString() {
        return restaurantId + " - " + restaurantName;
    }
}