package models;

public class CustomerMenuItemView {

    private int itemId;
    private String itemName;
    private String description;
    private double price;
    private int availability;
    private String restaurantName;
    private String categoryName;

    public CustomerMenuItemView(int itemId, String itemName, String description,
                                double price, int availability,
                                String restaurantName, String categoryName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.restaurantName = restaurantName;
        this.categoryName = categoryName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}