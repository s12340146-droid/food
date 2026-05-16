package models;

public class OrderDiscount {

    private int orderId;
    private int discountId;
    private String discountCode;
    private double percentage;
    private String customerName;
    private String restaurantName;
    private double totalPrice;

    public OrderDiscount(int orderId, int discountId, String discountCode,
                         double percentage, String customerName,
                         String restaurantName, double totalPrice) {
        this.orderId = orderId;
        this.discountId = discountId;
        this.discountCode = discountCode;
        this.percentage = percentage;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public double getPercentage() {
        return percentage;
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
}