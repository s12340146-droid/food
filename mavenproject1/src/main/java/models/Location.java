package models;

public class Location {

    private int locationId;
    private String city;
    private String street;
    private String building;

    public Location(int locationId, String city, String street, String building) {
        this.locationId = locationId;
        this.city = city;
        this.street = street;
        this.building = building;
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

    @Override
    public String toString() {
        String buildingText = building == null || building.isEmpty() ? "" : " / " + building;
        return locationId + " - " + city + " / " + street + buildingText;
    }
}