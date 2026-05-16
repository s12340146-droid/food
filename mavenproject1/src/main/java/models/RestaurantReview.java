package models;

public class RestaurantReview {

    private int reviewId;
    private int customerId;
    private String customerName;
    private int restaurantId;
    private String restaurantName;
    private int rating;
    private String comment;

    public RestaurantReview(int reviewId, int customerId, String customerName,
                            int restaurantId, String restaurantName,
                            int rating, String comment) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
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

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}