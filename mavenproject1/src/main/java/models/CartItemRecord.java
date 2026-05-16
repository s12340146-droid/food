package models;

public class CartItemRecord {

    private int cartItemId;
    private int cartId;
    private int itemId;
    private String itemName;
    private String restaurantName;
    private double price;
    private int quantity;
    private double subtotal;

    public CartItemRecord(int cartItemId, int cartId, int itemId,
                          String itemName, String restaurantName,
                          double price, int quantity, double subtotal) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.restaurantName = restaurantName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public int getCartId() {
        return cartId;
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

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }
}