package models;

public class OrderCustomer {

    private int customerId;
    private String customerName;

    public OrderCustomer(int customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return customerId + " - " + customerName;
    }
}