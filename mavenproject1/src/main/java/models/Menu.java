package models;

public class Menu {

    private int menuId;
    private int restaurantId;
    private String restaurantName;

    public Menu(int menuId, int restaurantId, String restaurantName) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public String toString() {
        return menuId + " - " + restaurantName;
    }
}