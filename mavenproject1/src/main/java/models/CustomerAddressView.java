package models;

public class CustomerAddressView {

    private int addressId;
    private int locationId;
    private String city;
    private String street;
    private String building;
    private String locationDetails;

    public CustomerAddressView(int addressId, int locationId,
                               String city, String street, String building,
                               String locationDetails) {
        this.addressId = addressId;
        this.locationId = locationId;
        this.city = city;
        this.street = street;
        this.building = building;
        this.locationDetails = locationDetails;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getLocationDetails() {
        return locationDetails;
    }
}