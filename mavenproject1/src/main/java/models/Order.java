package models;

public class Order {

    private int orderId;
    private int customerId;
    private String customerName;
    private int restaurantId;
    private String restaurantName;
    private String orderDate;
    private String status;
    private double totalPrice;

    public Order(int orderId, int customerId, String customerName,
                 int restaurantId, String restaurantName,
                 String orderDate, String status, double totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRestaurantId() {
        return restaurantId;
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
    @Override
public String toString() {
    return orderId + " - " + customerName + " | " + restaurantName;
}
}