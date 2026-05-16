package models;

public class CustomerOrderView {

    private int orderId;
    private String restaurantName;
    private String orderDate;
    private String status;
    private double totalPrice;

    public CustomerOrderView(int orderId, String restaurantName, String orderDate, String status, double totalPrice) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}