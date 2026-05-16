package models;

public class Driver {

    private int driverId;
    private String name;
    private String phone;
    private String vehicleType;
    private String availabilityStatus;

    public Driver(int driverId, String name, String phone, String vehicleType, String availabilityStatus) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.vehicleType = vehicleType;
        this.availabilityStatus = availabilityStatus;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    @Override
    public String toString() {
        return driverId + " - " + name + " | " + vehicleType;
    }
}