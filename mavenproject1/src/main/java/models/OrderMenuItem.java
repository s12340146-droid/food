package models;

public class OrderMenuItem {

    private int itemId;
    private String itemName;
    private String restaurantName;
    private double price;

    public OrderMenuItem(int itemId, String itemName, String restaurantName, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.restaurantName = restaurantName;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return itemId + " - " + itemName + " | " + restaurantName + " | " + price;
    }
}