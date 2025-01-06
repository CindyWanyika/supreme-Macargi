import java.util.ArrayList;

public class Driver extends User {
    private String carRegistration;
    private String idNumber;
    private String phoneNumber;
    private Location location;
    private ArrayList<Ride> rideHistory;
    private boolean isAvailable=true;
    private static ArrayList<Driver> availableDrivers=new ArrayList<>();
    private Ride currentRide=null;
    private ArrayList<Ride> rideRequests;
    private static ArrayList<Driver> allDrivers=new ArrayList<>();
    public static ArrayList<String> driverNames=new ArrayList<>();

    // Constructor
    public Driver(String name,String Password,String carRegistration, String idNumber, String dlNumber, Location location) {
        super(name, Password, "Driver");
        this.carRegistration = carRegistration;
        this.idNumber = idNumber;
        this.phoneNumber = dlNumber;
        this.location = location;
        rideRequests=new ArrayList<>();
        rideHistory=new ArrayList<>();
    }
    public static void createDriverAccount(String name,String password,String carRegistration,String idNumber,String dlNumber,Location location) {
        // Creating a new driver object
        Driver newDriver = new Driver(name, password, carRegistration, idNumber, dlNumber, location);
        availableDrivers.add(newDriver);
        driverNames.add(name);
        allDrivers.add(newDriver);
        System.out.println("Driver account created successfully!");
    }

    //methods to accept and decline ride
    public static Driver getDriverByUsername(String username){
        for(Driver driver:allDrivers){
            if (driver.getName().equals(username)){
                return driver;
            }
        }
        return null;
    }
    public void acceptRide(Ride ride) {
        if (!isAvailable) {
            return;
        }
        this.setAvailable(false);  // Mark as unavailable once the ride is accepted
        ride.setStatus("Active");
        this.currentRide=ride;
        ride.getPassenger().setCurrentRide(ride);
        Ride.activeRides.add(ride);
        this.rideRequests.remove(ride);

    }
    //decline ride
    // Decline a ride and remain available
    public void declineRide(Ride ride) {
        this.setAvailable(true);
        this.rideRequests.remove(ride);
        ride.setStatus("Declined");

    }
    public void endRide(Ride ride){
        Ride newR=null;
        Passenger passenger=ride.getPassenger();
        passenger.setCurrentRide(newR);
        passenger.setRideHistory(ride);
        this.currentRide=null;
        this.rideHistory.remove(ride);
        ride.setStatus("Completed");
    }


    public static ArrayList<Driver> getAvailableDrivers() {
        return availableDrivers;
    }

    public static void setAvailableDrivers(ArrayList<Driver> availableDrivers) {
        Driver.availableDrivers = availableDrivers;
    }

    public static ArrayList<Driver> getAllDrivers() {
        return allDrivers;
    }

    public static void setAllDrivers(ArrayList<Driver> allDrivers) {
        Driver.allDrivers = allDrivers;
    }

    public String getCarRegistration() {
        return carRegistration;
    }

    public void setCarRegistration(String carRegistration) {
        this.carRegistration = carRegistration;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Ride> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(ArrayList<Ride> rideHistory) {
        this.rideHistory = rideHistory;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Set Availability
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        if (isAvailable) {
            availableDrivers.add(this);
        } else {
            availableDrivers.remove(this);
        }
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Ride currentRide) {
        this.currentRide = currentRide;
    }

    public ArrayList<Ride> getRideRequests() {
        return rideRequests;
    }

    public void addRideRequests(Ride ride) {
        this.rideRequests.add(ride);
    }
    @Override
    public String toString() {
        return "Driver{" +
                "name='" + getName() + '\'' +
                ", carRegistration='" + carRegistration + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", location=" + location +
                ", isAvailable=" + isAvailable +
                ", rideHistory=" + rideHistory +
                '}';
    }
}
