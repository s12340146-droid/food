package models;

public class Address {

    private int addressId;
    private int customerId;
    private String customerName;
    private int locationId;
    private String locationDetails;

    public Address(int addressId, int customerId, String customerName, int locationId, String locationDetails) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.locationId = locationId;
        this.locationDetails = locationDetails;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getLocationDetails() {
        return locationDetails;
    }
}