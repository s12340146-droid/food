package models;

public class CustomerReviewView {

    private int reviewId;
    private int restaurantId;
    private String restaurantName;
    private int rating;
    private String comment;
    private String date;

    public CustomerReviewView(int reviewId, int restaurantId, String restaurantName,
                              int rating, String comment, String date) {
        this.reviewId = reviewId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getReviewId() {
        return reviewId;
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

    public String getDate() {
        return date;
    }
}