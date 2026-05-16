package models;

public class MenuItem {

    private int itemId;
    private String itemName;
    private String description;
    private double price;
    private String availability;
    private int menuId;
    private String restaurantName;
    private int categoryId;
    private String categoryName;

    public MenuItem(int itemId, String itemName, String description, double price,
                    String availability, int menuId, String restaurantName,
                    int categoryId, String categoryName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.menuId = menuId;
        this.restaurantName = restaurantName;
        this.categoryId = categoryId;
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

    public String getAvailability() {
        return availability;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}