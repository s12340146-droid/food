package models;

public class Payment {

    private int paymentId;
    private int orderId;
    private String customerName;
    private String restaurantName;
    private double totalPrice;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;

    public Payment(int paymentId, int orderId, String customerName, String restaurantName,
                   double totalPrice, String paymentMethod, String paymentStatus, String paymentDate) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }
}