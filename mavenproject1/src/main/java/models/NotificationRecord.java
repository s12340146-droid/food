package models;

public class NotificationRecord {

    private int notificationId;
    private int customerId;
    private String customerName;
    private String message;
    private String date;

    public NotificationRecord(int notificationId, int customerId, String customerName, String message, String date) {
        this.notificationId = notificationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.message = message;
        this.date = date;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}