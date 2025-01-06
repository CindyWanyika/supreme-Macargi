import java.util.ArrayList;
import java.util.List;

public class Passenger extends User {
    private Location location;
    private String idNumber;
    private String phoneNumber;
    private int loyaltyPoints;
    private Ride currentRide=null;
    private List<Ride> rideHistory;
    private static ArrayList<Passenger> passengers=new ArrayList<>();
    static ArrayList<String> passengerNames=new ArrayList<>();


    // Constructor
    public Passenger(String name, String password, String role, Location location, String idNumber,String phone) {
        super(name, password,"Passenger"); // Call to User constructor
        this.location = location;
        this.idNumber = idNumber;
        this.phoneNumber=phone;
        rideHistory= new ArrayList<>();
        passengerNames.add(name);
    }
    public static Passenger getPassengerByUsername(String username){
        for(Passenger passenger:passengers){
            if (passenger.getName().equals(username)){
                return passenger;
            }
        }
        return null;
    }


    public static void addPassengers(Passenger passenger) {
        passengers.add(passenger);
        System.out.println("new passenger added");
    }
    public static void removePassenger(Passenger passenger){
        passengers.remove(passenger);
        System.out.println("passenger removed");
    }


    // Getter and Setter methods for Passenger class

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {

        this.location=location;

    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public void deductLoyaltyPoints(int points) {
        this.loyaltyPoints -= points;
    }


    //find a cab
    public ArrayList<Ride> findCab(Location startLocation, Location endLocation) {
        // Look for available drivers near the passenger's location
        ArrayList<Ride> rides = RideManager.findRide(this, startLocation, endLocation);
        System.out.println("Rides Near you");
        for (Ride ride : rides) {
            System.out.println("\n" + ride.toString());
        }
        return rides;
    }

    //cancelRide
    public void cancelRide(Ride ride, String reason) {

        //  Remove the ride from active rides
        Ride.activeRides.remove(ride);
        // Step 2: Add the ride to canceled rides
        Ride.cancelledRides.put(ride, reason);
        ride.getDriver().setAvailable(true);
        ride.setStatus("Cancelled");

        System.out.println("Ride canceled. Reason: " + reason);
    }


    //viewCurrentRide
    public void bookRide(Ride ride) {

        //sends a ride request to the driver
        Driver driver=ride.getDriver();
        driver.addRideRequests(ride);
        System.out.println("Waiting for driver to accept the ride...");

    }


    // Getter for current ride
    public Ride getCurrentRide() {
        return currentRide;
    }

   //give feedback maybe

    public List<Ride> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(Ride ride) {
        this.rideHistory.add(ride);
    }

    public void setCurrentRide(Ride currentRide) {
        this.currentRide = currentRide;
    }


    public void setCurrentLocation(Location location) {
        this.location=location;
    }
    @Override
    public String toString(){
        return "Name: "+this.name+
                "\nID: "+this.idNumber+
                "\nCurrent Location: "+this.location.toString()+
                "\nLoyalty Points: "+this.loyaltyPoints;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
