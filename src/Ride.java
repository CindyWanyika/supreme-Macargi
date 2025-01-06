

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ride {
    //instance variable
    private String rideID;
    private Location startLocation;
    private Location endLocation;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Driver driver;
    private Passenger passenger;
    private String status;//(Ongoing, Completed, Cancelled).
    private Double cost;
    private String feedback;
    //an arraylist to contain all rides that are currently in session
    protected static ArrayList<Ride> activeRides=new ArrayList<>();
    protected static HashMap<Ride,String> cancelledRides=new HashMap<>();
    protected static ArrayList<Ride> allRides=new ArrayList<>();

    public Ride( LocalDate date, Location startLocation, Location endLocation, LocalTime startTime, Driver driver, Passenger passenger) {
        this.rideID = generateRideID();
        this.driver = driver;
        this.passenger = passenger;
        this.date = date;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTime = startTime;
        this.cost=calculateCost(startLocation,endLocation);
    }
    public Ride( LocalDate date, Location startLocation, Location endLocation, LocalTime startTime, Driver driver, Passenger passenger,String feedback) {
        this.rideID = generateRideID();
        this.driver = driver;
        this.passenger = passenger;
        this.date = date;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTime = startTime;
        this.cost=calculateCost(startLocation,endLocation);
        this.feedback=feedback;
    }

    public static Ride retrieveRidesByName(String name) {
        Ride ride1 = null;
        for (Ride ride:allRides){
            if(ride.getPassenger().getName().equals(name)||ride.getDriver().getName().equals(name))return ride;
        }
        return ride1;
    }

    // Getters and Setters

    public String getRideId() {
        return rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {this.endLocation = endLocation;}

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    public LocalTime getStartTime() {return startTime;}

    public void setStartTime(LocalTime startTime) {this.startTime = startTime;}

    public LocalTime getEndTime() {return endTime;}

    public void setEndTime(LocalTime endTime) {this.endTime = endTime;}

    public Driver getDriver() {return driver;}

    public void setDriver(Driver driver) {this.driver = driver;}

    public Passenger getPassenger() {return passenger;}

    public void setPassenger(Passenger passenger) {this.passenger = passenger;}

    public String getStatus() {return this.status;}

    public void setStatus(String status) {this.status = status;}

    public Double getCost() {return this.cost;}

    public void setCost(Double cost) {this.cost = cost;}

    public String getFeedback() {return this.feedback;}

    public void setFeedback(String status) {this.feedback = feedback;}

    // Methods for updating status and calculating the cost

    private static String generateRideID() {
        return "RIDE" + System.currentTimeMillis(); // Generate a unique ID based on time
    }
    public double calculateCost(Location start,Location end) {
        double distance = LocationAPI.calculateDistance(start, end);
        return Admin.getCurrentRate() * distance;
    }

    // Method to check ongoing rides
    public List<Ride> getActiveRides() {
        return activeRides;
    }
    @Override
    public String toString() {
        return
                "rideID='" + rideID + '\'' +
                        "\n startLocation=" + startLocation +
                        "\n endLocation=" + endLocation +
                        "\n date=" + date +
                        "\n startTime=" + startTime +
                        "\nCar registration="+driver.getCarRegistration()+
                        "\n driver=" + driver.getName() +
                        "\n Contact=" + driver.getPhoneNumber() +
                        "\n passenger=" + passenger.getName() +
                        "\n contact=" + passenger.getPhoneNumber() +
                        "\n status='" + status + '\'' +
                        "\n cost=" + cost
                ;
    }

    public static void main (String[] args){
        Passenger passenger;
        Driver driver;
        Location startLocation;
        Location endLocation;
        Ride ride;


        // Initialize concrete objects for testing
        startLocation = new Location("Ashesi University", "5.757", "-0.213");
        endLocation = new Location("Kotoka International Airport", "5.607", "-0.174");
        driver =new Driver("John Doe","password123","123435","32423","dl12345",startLocation);
        passenger = new Passenger("Cindy", "password123", "Passenger", startLocation, "ID123","999647");

        ride = new Ride(LocalDate.now(), startLocation, endLocation, LocalTime.now(), driver, passenger);
        ride.setFeedback("Great Ride");
        System.out.println(ride.getFeedback());

    }



}