package models;

public class CustomerNotificationView {

    private int notificationId;
    private String message;
    private String date;

    public CustomerNotificationView(int notificationId, String message, String date) {
        this.notificationId = notificationId;
        this.message = message;
        this.date = date;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}