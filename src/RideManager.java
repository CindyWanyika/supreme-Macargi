import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RideManager {

    // Method to find a ride based on passenger's location
    public static ArrayList<Ride> findRide(Passenger passenger, Location passengerLocation, Location endLocation) {
        ArrayList<Ride> availableRides = new ArrayList<>();
        LocationAPI api = new LocationAPI();

        for (Driver driver:Driver.getAvailableDrivers()){
            if (api.calculateDistance(driver.getLocation(),passengerLocation)<=5){
                Ride ride=new Ride(LocalDate.now(),passengerLocation,endLocation,LocalTime.now(),driver,passenger);
                availableRides.add(ride);
            }
        }

        return availableRides;
    }
}