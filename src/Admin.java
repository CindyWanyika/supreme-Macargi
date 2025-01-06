import java.io.*;
import java.util.*;

public class Admin extends User {
    private String adminID; // Unique ID for the admin
    private static double currentRate=6.0;
    private static ArrayList<Admin> alladmins=new ArrayList<>();
    private static final String DEFAULT_ADMIN_ID = "1234"; // Default ID for the first login
    private static String systemAdminID = "1234";// rate per km for rides
    private static File admins=new File("registeredAdmins.csv");

    // Constructor for setting up the admin, and initialize currentRate if needed
    public Admin(String username, String password, String adminID) {
        super(username, password, "Admin"); // Inherit from User class
        this.adminID = adminID;
    }
    public static String getSystemAdminID(){return systemAdminID;}

    public void saveAdminToFile() {
        try (FileWriter writer = new FileWriter(admins, true)) {
            // Add a header if the file is empty
            if (admins.length() == 0) {
                writer.append("Username,Password,AdminID\n");
            }

            // Append admin details
            writer.append(this.getName()).append(",")
                    .append(this.getPassword()).append(",")
                    .append(this.getAdminID()).append("\n");

            System.out.println("Admin data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving admin data: " + e.getMessage());
        }
    }
    public static Admin getAdminByID(String adminID) {
        // Check if the file exists
        if (!admins.exists()) {
            System.err.println("Admin file not found.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(admins))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming file format: username,password,role,adminID
                String[] fields = line.split(",");
                if (fields.length == 4 && fields[3].equals(adminID)) {
                    String username = fields[0];
                    String password = fields[1];
                    String role = fields[2];
                    return new Admin(username, password, adminID);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the admin file: " + e.getMessage());
        }

        System.out.println("Admin with ID " + adminID + " not found.");
        return null;
    }
    public static void createAdminAccount(String username, String password, String adminID) {
        // Create a new admin object
        Admin newAdmin = new Admin(username, password, adminID);

        // Save the admin to file
        newAdmin.saveAdminToFile();

        // Optionally add the admin to the list for runtime access
        addAdmins(newAdmin);

        System.out.println("Admin account created successfully!");
    }



    // Set the custom system-wide admin ID
    public static void setSystemAdminID(String newID) {
        systemAdminID = newID;
    }

    public static ArrayList<Admin> getAlladmins() {
        return alladmins;
    }

    public static void addAdmins(Admin admin) {
        alladmins.add(admin);
    }
    public static boolean isRegisteredAdmin(String name) {
        // Check if the file exists
        if (!admins.exists()) {
            System.out.println("Admin records file not found.");
            return false;
        }

        try (Scanner scanner = new Scanner(admins)) {
            // Skip the header row if present
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read each line and check the username
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");

                // Assuming the username is the first column
                if (details[0].equalsIgnoreCase(name)) {
                    return true; // Admin is registered
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading admin records: " + e.getMessage());
        }

        return false; // Admin not found
    }


    // Set the ride rates (Admin-only functionality)
    public void setRideRates(double newRate) {
        if (newRate > 0) {
            this.currentRate = newRate; // Update the current rate
            System.out.println("New ride rate set to: " + newRate);
        } else {
            System.out.println("Rate must be positive.");
        }
    }

    // View all rides (from a file)
    public void viewAllRides() {
        System.out.println("All rides:");
        try (Scanner scanner = new Scanner(new File("Rides.csv"))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Error loading rides: " + e.getMessage());
        }
    }

    // View all drivers (from a file)
    public List<String> viewAllDrivers() {
        List<String> drivers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("drivers.csv"))) {
            while (scanner.hasNextLine()) {
                drivers.add(scanner.nextLine());
            }
        } catch (IOException e) {
            drivers.add("Error loading drivers: " + e.getMessage());
        }
        return drivers;
    }



    // Manage driver account (Disable/Delete)
    public void manageDriverAccount(String driverID, boolean disable) {
        System.out.println((disable ? "Disabling" : "Deleting") + " driver account: " + driverID);
        try (Scanner scanner = new Scanner(new File("drivers.txt"))) {
            List<String> updatedDrivers = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(driverID)) {
                    if (disable) {
                        updatedDrivers.add(line + " [Disabled]");
                    } else {
                        continue; // Skip this driver to delete them
                    }
                } else {
                    updatedDrivers.add(line);
                }
            }
            try (FileWriter writer = new FileWriter("drivers.txt")) {
                for (String updatedDriver : updatedDrivers) {
                    writer.write(updatedDriver + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error managing driver account: " + e.getMessage());
        }
    }

    // Getter for adminID
    public String getAdminID() {
        return adminID;
    }
    public void changeAdminId(String id){
        this.adminID=id;
    }

    // Getter for currentRate
    public static double getCurrentRate() {
        return currentRate;
    }
}
