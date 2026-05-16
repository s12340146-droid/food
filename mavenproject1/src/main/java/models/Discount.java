package models;

public class Discount {

    private int discountId;
    private String code;
    private String description;
    private double percentage;
    private String startDate;
    private String endDate;

    public Discount(int discountId, String code, String description,
                    double percentage, String startDate, String endDate) {
        this.discountId = discountId;
        this.code = code;
        this.description = description;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDiscountId() {
        return discountId;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return discountId + " - " + code + " | " + percentage + "%";
    }
}