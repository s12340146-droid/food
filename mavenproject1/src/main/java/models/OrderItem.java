package models;

public class OrderItem {

    private int orderItemId;
    private int orderId;
    private int itemId;
    private String itemName;
    private String restaurantName;
    private int quantity;
    private double itemPrice;
    private double subtotal;

    public OrderItem(int orderItemId, int orderId, int itemId, String itemName,
                     String restaurantName, int quantity, double itemPrice, double subtotal) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.restaurantName = restaurantName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.subtotal = subtotal;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getOrderId() {
        return orderId;
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

    public int getQuantity() {
        return quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }
}