package models;

public class CustomerOrderItemView {

    private int orderItemId;
    private int itemId;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private double subtotal;

    public CustomerOrderItemView(int orderItemId, int itemId, String itemName,
                                 double itemPrice, int quantity, double subtotal) {
        this.orderItemId = orderItemId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }
}