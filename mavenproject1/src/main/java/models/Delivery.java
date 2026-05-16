package models;

public class Delivery {

    private int deliveryId;
    private int orderId;
    private int driverId;
    private String driverName;
    private String customerName;
    private String restaurantName;
    private String deliveryStatus;
    private String estimatedTime;
    private String actualTime;

    public Delivery(int deliveryId, int orderId, int driverId, String driverName,
                    String customerName, String restaurantName,
                    String deliveryStatus, String estimatedTime, String actualTime) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.deliveryStatus = deliveryStatus;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public String getActualTime() {
        return actualTime;
    }
}