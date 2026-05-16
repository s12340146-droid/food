package models;

public class CartRecord {

    private int cartId;
    private int customerId;
    private String customerName;

    public CartRecord(int cartId, int customerId, String customerName) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public int getCartId() {
        return cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return cartId + " - " + customerName;
    }
}