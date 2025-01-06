import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RideDashApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Root pane
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #2C2C2C;");  // Dark background

        // Welcome message
        Text welcomeText = new Text("WELCOME TO RIDEDASH");
        welcomeText.setFont(Font.font("roboto", 30));
        welcomeText.setFill(Color.ORANGE);

        Text taglineText = new Text("Connecting You to the Ride You Need, When You Need It!");
        taglineText.setFont(Font.font("Arial", 10));
        taglineText.setFill(Color.ORANGE);

        // VBox for login and account creation options
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #2C2C2C;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        vbox.getChildren().addAll(welcomeText, taglineText,loginButton, createAccountButton);

        root.getChildren().add(vbox);

        // Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Ridedash App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Login Button Action
        loginButton.setOnAction(e -> openLoginPage(primaryStage));

        // Create Account Button Action
        createAccountButton.setOnAction(e -> openCreateAccountPage(primaryStage));
    }
    // Open Create Account page
    private void openCreateAccountPage(Stage stage) {
        VBox createAccountVBox = new VBox(20);
        createAccountVBox.setAlignment(Pos.CENTER);
        createAccountVBox.setStyle("-fx-background-color: #2C2C2C;");

        Text createAccountTitle = new Text("Create Account");
        createAccountTitle.setFont(Font.font("Arial", 30));
        createAccountTitle.setFill(Color.ORANGE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Driver", "Admin", "Passenger");
        roleComboBox.setPromptText("Select Role");

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Button backButton=new Button("back to login");
        backButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Label feedbackLabel = new Label();
        feedbackLabel.setFont(Font.font("Arial", 16));
        feedbackLabel.setTextFill(Color.RED);


        createAccountVBox.getChildren().addAll(createAccountTitle, usernameField, passwordField, confirmPasswordField, roleComboBox, createAccountButton,feedbackLabel,backButton);

        Scene createAccountScene = new Scene(createAccountVBox, 500, 600);
        stage.setScene(createAccountScene);

        createAccountButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            String role = roleComboBox.getValue();

            // Clear feedback label
            feedbackLabel.setText("");

            // Check if all fields are filled
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
                feedbackLabel.setText("Error: All fields must be filled.");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                feedbackLabel.setText("Error: Passwords do not match.");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }

            // Check if username already exists
            if (User.userPasswords.containsKey(username)){
                feedbackLabel.setText("Error: Account with that username already exists.");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }

            // Create account
            User newUser = new User(username, password, role);
            User.createAccount(username,password,confirmPassword,role);

            // Provide success feedback
            feedbackLabel.setText("Account created successfully.");
            feedbackLabel.setTextFill(Color.GREEN);
            System.out.println("Account created successfully.");
        });

        backButton.setOnAction(e-> {
            try {
                start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    // Open Login page
    private void openLoginPage(Stage stage) {
        // Create the main layout for the login page
        VBox loginVBox = new VBox(20);
        loginVBox.setAlignment(Pos.CENTER);
        loginVBox.setStyle("-fx-background-color: #2C2C2C;");

        // Create and style title
        Text loginTitle = new Text("Login");
        loginTitle.setFont(Font.font("Arial", 30));
        loginTitle.setFill(Color.ORANGE);

        // Create input fields for username and password
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        // Create the login button
        Button loginSubmitButton = new Button("Login");
        loginSubmitButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 10px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        // Label for feedback messages
        Label feedbackLabel = new Label();
        feedbackLabel.setFont(Font.font("Arial", 16));
        feedbackLabel.setTextFill(Color.RED);

        // Back button action (return to home or previous screen)
        backButton.setOnAction(e -> {
            try {
                start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }); // Replace with actual navigation logic

        // Add components to the layout
        loginVBox.getChildren().addAll(loginTitle, usernameField, passwordField, loginSubmitButton, feedbackLabel, backButton);

        // Set the scene for the stage
        Scene loginScene = new Scene(loginVBox, 400, 300);
        stage.setScene(loginScene);

        // Login Button Action
        loginSubmitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate inputs
            if (username.isEmpty() || password.isEmpty()) {
                feedbackLabel.setText("Please enter both username and password.");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }

            boolean isAuthenticated = User.login(username, password);
            if (isAuthenticated) {
                feedbackLabel.setText("Login successful!");
                feedbackLabel.setTextFill(Color.GREEN);  // Success message in green
                System.out.println("Login successful!");

                // Retrieve the user's role and proceed accordingly
                String role = User.userRoles.get(username);
                System.out.println(role);// Get the role of the logged-in user
                System.out.println("User role: " + role);  // Debugging: Check role

                // Check the user's role and redirect accordingly
                if (role.equals("Driver")) {
                    if (Driver.driverNames.contains(username)) {
                        System.out.println("Driver is registered.");//
                        Driver currentdriver=Driver.getDriverByUsername(username);// Debugging
                        openDriverDashboard(stage,currentdriver);  // Open driver dashboard if registered
                    } else {
                        System.out.println("Driver is not registered.");  // Debugging: Check if not registered
                        User dUser=new User(username,password,role);
                        openDriverSetupPage(dUser,stage);  // Open driver setup if not registered
                    }
                }  else if (role.equals("Passenger")) {
                    if (Passenger.passengerNames.contains(username)) {
                        System.out.println("Passenger is registered.");  // Debugging
                        Passenger current = Passenger.getPassengerByUsername(username);
                        openPassengerDashboard(stage,current);  // Open passenger dashboard if registered
                    } else {
                        System.out.println("Passenger is not registered.");  // Debugging: Check if not registered
                        User curr = new User(username, password, role);
                        openPassengerSetupPage(curr, stage);  // Open passenger setup if not registered
                    }
                } /**else if (role.equals("Admin")) {
                    if (Admin.isRegisteredAdmin(username)) {
                        System.out.println("Admin is registered.");  // Debugging: Check registration status
                        Admin currentAdmin=Admin.getAdminByID(username);
                        openAdminDashboard(stage,currentAdmin);  // Open admin dashboard if registered
                    } else {
                        System.out.println("Admin is not registered.");  // Debugging: Check if not registered
                        User curr = new User(username, password, role);
                        openAdminSetup(curr,stage);  // Open admin setup if not registered
                    }
                } else {
                    feedbackLabel.setText("Unknown role. Please contact support.");
                    feedbackLabel.setTextFill(Color.RED);
                }*/
            } else {
                feedbackLabel.setText("Invalid credentials. Try again.");
                feedbackLabel.setTextFill(Color.RED);  // Show error in red color
                System.out.println("Invalid credentials. Try again.");
            }
        });


    }
    private void openPassengerSetupPage(User curr,Stage stage) {
        VBox driverSetupVBox = new VBox(20);
        driverSetupVBox.setAlignment(Pos.CENTER);
        driverSetupVBox.setStyle("-fx-background-color: #2C2C2C;");

        Text driverSetupTitle = new Text("Setup Your account");
        driverSetupTitle.setFont(Font.font("Arial", 30));
        driverSetupTitle.setFill(Color.ORANGE);


        TextField idNumberField = new TextField();
        idNumberField.setPromptText("Enter your ID number");

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter your phone number");


        TextField locationField = new TextField();
        locationField.setPromptText("Enter your location");

        Button saveButton = new Button("Save and Proceed");
        saveButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Label feedbackLabel = new Label();
        feedbackLabel.setFont(Font.font("Arial", 16));
        feedbackLabel.setTextFill(Color.RED);

        driverSetupVBox.getChildren().addAll(driverSetupTitle,phoneNumberField,idNumberField, locationField, saveButton, feedbackLabel, backButton);

        Scene driverSetupScene = new Scene(driverSetupVBox, 500, 600);
        stage.setScene(driverSetupScene);

        saveButton.setOnAction(e -> {

            String id= idNumberField.getText();
            String rawLocation= locationField.getText();
            Location location=Location.createLocation(rawLocation);
            String phone=phoneNumberField.getText();

            if (id.isEmpty()||rawLocation.isEmpty()) {
                feedbackLabel.setText("Please fill out all fields.");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }

            Passenger passenger=new Passenger(curr.getName(),curr.getPassword(),curr.getRole(),location,id,phone);
            Passenger.addPassengers(passenger);

            feedbackLabel.setText("Profile setup complete.");
            feedbackLabel.setTextFill(Color.GREEN);

            // Proceed to the driver dashboard
            openPassengerDashboard(stage,passenger);
        });

        backButton.setOnAction(e -> openLoginPage(stage));  // Go back to login page
    }
    private void openPassengerDashboard(Stage stage,Passenger passenger) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #2C2C2C;");

        VBox passengerDashboardVBox = new VBox(20);
        passengerDashboardVBox.setAlignment(Pos.CENTER);
        passengerDashboardVBox.setStyle("-fx-background-color: #2C2C2C;");

        // Display passenger's name on the dashboard
        Text passengerDashboardTitle = new Text("Hello " + passenger.getName() + "\nWelcome to your Dashboard");
        passengerDashboardTitle.setFont(Font.font("Arial", 30));
        passengerDashboardTitle.setFill(Color.ORANGE);

        TextField locationQuery = new TextField();
        locationQuery.setPromptText("What is your current location? Let us help you find drivers near you!");
        locationQuery.setStyle("-fx-font-size: 16px; -fx-background-color: #333333; -fx-text-fill: white;");

// Event to handle location entry on pressing Enter
        locationQuery.setOnAction(e -> {
            String locationInput = locationQuery.getText().trim();
            if (!locationInput.isEmpty()) {
                // Reverse search and update the driver's location
                Location location = Location.createLocation(locationInput); // Assuming createLocation returns a Location object
                passenger.setLocation(location);
                locationQuery.setText("Location set to: " + locationInput); // Feedback to user
                locationQuery.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;"); // Change background to indicate success
            } else {
                locationQuery.setText("Please enter a valid location!");
                locationQuery.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;"); // Indicate error
            }
        });

        // Create a profile circle for the passenger
        Circle profileCircle = new Circle(30, Color.ORANGE);
        profileCircle.setOnMouseClicked(e -> showAccountDetails(stage,passenger)); // Show account details

        // Buttons for passenger actions
        Button bookRideButton = new Button("Book a Ride");
        bookRideButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");
        bookRideButton.setOnAction(e -> findAndBookRide(passenger)); // Opens ride booking page

        Button viewHistoryButton = new Button("View Ride History");
        viewHistoryButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFFF99; -fx-text-fill: black; -fx-padding: 10px 20px;");
        viewHistoryButton.setOnAction(e -> showRideHistory(passenger)); // Opens ride history page

        // Display current ride status (e.g., no current ride or active ride)
        Text currentRideStatus = new Text();
        currentRideStatus.setFont(Font.font("Arial", 20));
        currentRideStatus.setFill(Color.WHITE);

        if (passenger.getCurrentRide()!=null){
            currentRideStatus.setText("Your current Ride:\n"+passenger.getCurrentRide().toString());
        }else currentRideStatus.setText("No Current Ride");

        // Cancel Ride Button (only shows if there is an active ride)
        Button cancelRideButton = new Button("Cancel Ride");
        cancelRideButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");
        cancelRideButton.setVisible(false); // Initially hidden
        cancelRideButton.setOnAction(e -> {
            // Cancel the current ride if there is one
            TextField cancelRideField = new TextField();
            cancelRideField.setPromptText("Reason");
            String reason = cancelRideField.getText();
            passenger.cancelRide(passenger.getCurrentRide(), reason); // Cancel the ride (assuming method exists in Passenger class)
            currentRideStatus.setText("No current ride");
            cancelRideButton.setVisible(false); // Hide the cancel button after the ride is canceled
        });

        // Logout Button
        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");
        logOutButton.setOnAction(e -> openLoginPage(stage)); // Redirect to login page



        // Add components to the VBox layout
        passengerDashboardVBox.getChildren().addAll(
                profileCircle,
                passengerDashboardTitle,
                locationQuery,
                bookRideButton,
                viewHistoryButton,
                currentRideStatus,
                cancelRideButton,
                logOutButton
        );

        ScrollPane scrollPane = new ScrollPane(passengerDashboardVBox);
        scrollPane.setFitToWidth(true); // Ensures the content fits the ScrollPane width
        scrollPane.setStyle("-fx-background-color: #2C2C2C;");

        root.getChildren().add(scrollPane);

        // Create a new scene and set it to the stage
        Scene passengerDashboardScene = new Scene(root, 800, 600);
        stage.setScene(passengerDashboardScene);
    }
    private void findAndBookRide(Passenger passenger) {
        Stage rideStage = new Stage();
        rideStage.setTitle("Find & Book Rides");

        VBox rideBox = new VBox(10);
        rideBox.setAlignment(Pos.CENTER);
        rideBox.setStyle("-fx-background-color: black; -fx-padding: 20;");

        Label titleLabel = new Label("Available Rides");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: yellow;");

        TextField destinationField = new TextField();
        destinationField.setPromptText("Enter your destination");

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: red;");

        Button searchButton = new Button("Search Rides");
        searchButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");

        searchButton.setOnAction(e -> {
            String end = destinationField.getText().trim();

            if (end.isEmpty()) {
                feedbackLabel.setText("Please enter a valid destination.");
                return;
            }

            Location destination = Location.createLocation(end);

            ArrayList<Ride> availableRides = RideManager.findRide(passenger, passenger.getLocation(), destination);

            rideBox.getChildren().clear(); // Clear previous content
            rideBox.getChildren().add(titleLabel);

            if (availableRides.isEmpty()) {
                feedbackLabel.setText("No rides available nearby.");
                rideBox.getChildren().add(feedbackLabel);
            } else {
                for (Ride ride : availableRides) {
                    Label rideLabel = new Label(ride.toString());
                    rideLabel.setStyle("-fx-text-fill: yellow;");

                    Button bookButton = new Button("Book Ride");
                    bookButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                    bookButton.setOnAction(ev -> {
                        // Notify the passenger to wait for driver acceptance
                        Alert waitingAlert = new Alert(Alert.AlertType.INFORMATION);
                        waitingAlert.setTitle("Ride Status");
                        waitingAlert.setHeaderText(null);
                        waitingAlert.setContentText("Your ride request has been sent. Please wait for the driver to accept.");
                        waitingAlert.showAndWait();

                        passenger.bookRide(ride);  // Proceed with booking the ride
                        rideStage.close();  // Close the ride booking stage
                    });

                    rideBox.getChildren().addAll(rideLabel, bookButton);
                }
            }
        });

        rideBox.getChildren().addAll(titleLabel, destinationField, searchButton, feedbackLabel);

        Scene scene = new Scene(rideBox, 300, 400);
        rideStage.setScene(scene);
        rideStage.show();
    }

    private void showRideHistory(Passenger passenger) {
        Stage historyStage = new Stage();
        historyStage.setTitle("Ride History");

        VBox historyBox = new VBox(10);
        historyBox.setAlignment(Pos.CENTER);
        historyBox.setStyle("-fx-background-color: black; -fx-padding: 20;");

        Label titleLabel = new Label("Your Ride History");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: yellow;");

        if (passenger.getRideHistory().isEmpty()) {
            Label emptyLabel = new Label("You have no rides yet.");
            emptyLabel.setStyle("-fx-text-fill: yellow;");
            historyBox.getChildren().addAll(titleLabel, emptyLabel);
        } else {
            for (Ride ride : passenger.getRideHistory()) {
                Label rideLabel = new Label(ride.toString());
                rideLabel.setStyle("-fx-text-fill: yellow;");
                historyBox.getChildren().add(rideLabel);
            }
        }

        Scene scene = new Scene(historyBox, 400, 500);
        historyStage.setScene(scene);
        historyStage.show();
    }

    public void showAccountDetails(Stage stage,Passenger passenger) {
        Stage accountDetailsStage = new Stage();
        accountDetailsStage.setTitle("Account Details");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))); // Black background

        // Create labels and text fields
        Label nameLabel = new Label("Name:");
        nameLabel.setTextFill(Color.ORANGE);
        TextField nameField = new TextField(passenger.getName());

        Label idNumberLabel = new Label("ID Number:");
        idNumberLabel.setTextFill(Color.ORANGE);
        TextField idNumberField = new TextField(passenger.getIdNumber());

        Label phoneNumberLabel = new Label("Phone Number:");
        phoneNumberLabel.setTextFill(Color.ORANGE);
        TextField phoneNumberField = new TextField(passenger.getPhoneNumber());

        Label locationLabel = new Label("Location:");
        locationLabel.setTextFill(Color.ORANGE);
        TextField locationField = new TextField(passenger.getLocation().toString()); // Assuming Location has a meaningful toString method

        // Create buttons
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;"); // Orange background with black text
        Button logoutButton = new Button("Delete account");
        logoutButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;"); // Orange background with black text

        // Add components to the grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(idNumberLabel, 0, 1);
        grid.add(idNumberField, 1, 1);
        grid.add(locationLabel, 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(phoneNumberLabel, 0, 3);
        grid.add(phoneNumberField, 1, 3);
        grid.add(saveButton, 0, 4);
        grid.add(logoutButton, 1, 4);


        // Set the action for the save button
        saveButton.setOnAction(e -> {
            // Update passenger details
            passenger.setName(nameField.getText());
            passenger.setIdNumber(idNumberField.getText());
            passenger.setLocation(Location.createLocation(locationField.getText()));
            passenger.setPhoneNumber(phoneNumberField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Account details updated successfully.");
            alert.showAndWait();
        });

        // Set the action for the logout button
        logoutButton.setOnAction(e -> {
            accountDetailsStage.close();
            deleteAccount(stage,passenger);
            openLoginPage(stage); // Call method to go back to login
        });

        // Create a Scene and display the stage
        Scene scene = new Scene(grid, 400, 200);
        accountDetailsStage.setScene(scene);
        accountDetailsStage.initModality(Modality.APPLICATION_MODAL);
        accountDetailsStage.showAndWait();
    }


    private void deleteAccount(Stage primaryStage,User user) {
        // Step 1: Ask the user to confirm the deletion
        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.setTitle("Confirm Account Deletion");
        confirmDelete.setHeaderText("Are you sure you want to delete your account?");
        confirmDelete.setContentText("This action cannot be undone.");

        confirmDelete.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Step 2: If confirmed, ask for the password
                TextInputDialog passwordDialog = new TextInputDialog();
                passwordDialog.setTitle("Enter Password");
                passwordDialog.setHeaderText("Please enter your password to confirm account deletion.");
                passwordDialog.setContentText("Password:");

                passwordDialog.showAndWait().ifPresent(password -> {
                    // Step 3: Call deleteAccount method from User class (password validation is inside it)
                    user.deleteAccount(password);

                    // Check if account deletion was successful (based on user.deleteAccount's output or message)
                    if (User.userPasswords.containsKey(user.getName())) {
                        // If the account still exists, password validation failed
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Password Incorrect");
                        errorAlert.setHeaderText("The password you entered is incorrect.");
                        errorAlert.setContentText("Please try again.");
                        errorAlert.showAndWait();
                    } else {
                        // Step 4: If deletion is successful, show disappointment message and redirect to login page
                        Alert disappointmentAlert = new Alert(Alert.AlertType.INFORMATION);
                        disappointmentAlert.setTitle("Account Deletion");
                        disappointmentAlert.setHeaderText("We're really sad to see you go.");
                        disappointmentAlert.setContentText("Goodbye and take care!");

                        disappointmentAlert.showAndWait().ifPresent(response2 -> {
                            // Use your existing method to go back to the login page
                            openLoginPage(primaryStage);  // Calls your method to show the login page
                        });
                    }
                });
            }
        });
    }
    // Open Driver Setup page (for first-time login)
    private void openDriverSetupPage(User now,Stage stage) {
        VBox driverSetupVBox = new VBox(20);
        driverSetupVBox.setAlignment(Pos.CENTER);
        driverSetupVBox.setStyle("-fx-background-color: #2C2C2C;");

        Text driverSetupTitle = new Text("Setup Your driver account");
        driverSetupTitle.setFont(Font.font("Arial", 30));
        driverSetupTitle.setFill(Color.ORANGE);

        TextField vehicleField = new TextField();
        vehicleField.setPromptText("Enter Vehicle Registration");

        TextField idNumberField = new TextField();
        idNumberField.setPromptText("Enter your ID number");

        TextField licenseField = new TextField();
        licenseField.setPromptText("Enter Phone Number");

        TextField locationField = new TextField();
        locationField.setPromptText("Enter your location");

        Button saveButton = new Button("Save and Proceed");
        saveButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-font-size: 20px; -fx-background-color: #FFA500; -fx-text-fill: white;");

        Label feedbackLabel = new Label();
        feedbackLabel.setFont(Font.font("Arial", 16));
        feedbackLabel.setTextFill(Color.RED);

        driverSetupVBox.getChildren().addAll(driverSetupTitle, vehicleField,idNumberField, licenseField,locationField, saveButton, feedbackLabel, backButton);

        Scene driverSetupScene = new Scene(driverSetupVBox, 500, 600);
        stage.setScene(driverSetupScene);

        saveButton.setOnAction(e -> {
            String vehicle = vehicleField.getText();
            String license = licenseField.getText();
            String id= idNumberField.getText();
            String rawLocation= locationField.getText();
            Location location=Location.createLocation(rawLocation);

            if (vehicle.isEmpty() || license.isEmpty()||id.isEmpty()||rawLocation.isEmpty()) {
                feedbackLabel.setText("Please fill out all fields.");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }


            Driver current=new Driver(now.getName(),now.getPassword(),vehicle,id,license,location);
            //Driver.addDriver(current);
            Driver.createDriverAccount(now.getName(),now.getPassword(),vehicle,id,license,location);
            feedbackLabel.setText("Profile setup complete.");
            feedbackLabel.setTextFill(Color.GREEN);

            // Proceed to the driver dashboard
            openDriverDashboard(stage,current);
        });

        backButton.setOnAction(e -> openLoginPage(stage));  // Go back to login page
    }
    // Open Driver Dashboard
    private void openDriverDashboard(Stage stage, Driver curr) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #2C2C2C;");

        VBox driverDashboardVBox = new VBox(20);
        driverDashboardVBox.setAlignment(Pos.CENTER);
        driverDashboardVBox.setStyle("-fx-background-color: #2C2C2C;");

        // Title with driver name
        Text driverDashboardTitle = new Text("Hello " + curr.getName() + "\nWelcome to your Dashboard");
        driverDashboardTitle.setFont(Font.font("Arial", 30));
        driverDashboardTitle.setFill(Color.ORANGE);

        // Location query field
        TextField locationQuery = new TextField();
        locationQuery.setPromptText("What is your current location? Your clients may be looking for you!");
        locationQuery.setStyle("-fx-font-size: 16px; -fx-background-color: #333333; -fx-text-fill: white;");

// Event to handle location entry on pressing Enter
        locationQuery.setOnAction(e -> {
            String locationInput = locationQuery.getText().trim();
            if (!locationInput.isEmpty()) {
                // Reverse search and update the driver's location
                Location location = Location.createLocation(locationInput); // Assuming createLocation returns a Location object
                curr.setLocation(location);
                locationQuery.setText("Location set to: " + locationInput); // Feedback to user
                locationQuery.setStyle("-fx-background-color: #228B22; -fx-text-fill: white;"); // Change background to indicate success
            } else {
                locationQuery.setText("Please enter a valid location!");
                locationQuery.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;"); // Indicate error
            }
        });

        // Profile picture
        Circle profileCircle = new Circle(50, Color.ORANGE); // Larger circle for better visibility
        profileCircle.setStroke(Color.BLACK);
        profileCircle.setStrokeWidth(2);
        profileCircle.setOnMouseClicked(e -> showDriverInfoWindow(stage,curr));

        // Availability toggle button
        ToggleButton availabilityToggle = new ToggleButton("Update availability");
        availabilityToggle.setStyle(
                "-fx-font-size: 16px; -fx-background-color: #FFA500; -fx-text-fill: white; -fx-padding: 10px;");
        availabilityToggle.setOnAction(e -> {
            if (availabilityToggle.isSelected()) {
                availabilityToggle.setText("Available");
                curr.setAvailable(true); // Set the driver as available
            } else {
                availabilityToggle.setText("Not Available");
                curr.setAvailable(false); // Set the driver as not available
            }
        });

        // Ride options with updated styles
        HBox rideSection = new HBox(20);
        rideSection.setAlignment(Pos.CENTER);


        Button viewHistoryButton = new Button("View Ride History");
        viewHistoryButton.setStyle(
                "-fx-font-size: 16px; -fx-background-color: linear-gradient(#FFFF99, #FFD700); -fx-text-fill: black; -fx-padding: 10px 20px;");
        //viewHistoryButton.setOnAction(e -> showRideHistoryWindow(curr));

        Text currentRideStatus = new Text();
        currentRideStatus.setFont(Font.font("Arial", 18));
        currentRideStatus.setFill(Color.WHITE);

        Button endRideButton = new Button("End Ride");
        endRideButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF6347; -fx-text-fill: white; -fx-padding: 10px;");
        endRideButton.setVisible(false); // Initially hidden

        Ride currentRide = curr.getCurrentRide();

        if (currentRide == null) {
            System.out.println("No 56789076542@222222435@#@45");
            currentRideStatus.setText("No Current Ride"); // Update text for no current ride
            endRideButton.setVisible(false); // Hide the "End Ride" button
        } else {
            currentRideStatus.setText("Current Ride: " + currentRide.toString()); // Update text for ongoing ride
            endRideButton.setVisible(true); // Show the "End Ride" button
        }

// Action to end the current ride when the driver clicks the button

        endRideButton.setOnAction(e -> {
            curr.endRide(currentRide); // End the current ride
            endRideButton.setVisible(false);
            currentRideStatus.setText("No Current Ride");// Hide the button after the ride ends


        });

        Button viewRequestsButton = new Button("View Ride Requests");
        viewRequestsButton.setStyle(
                "-fx-font-size: 16px; -fx-background-color: linear-gradient(#FFA500, #FF8C00); -fx-text-fill: white; -fx-padding: 10px 20px;");
        viewRequestsButton.setOnAction(e -> viewRideRequests(curr, currentRideStatus, endRideButton));

        rideSection.getChildren().addAll(viewRequestsButton, viewHistoryButton);
        // Logout button
        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle(
                "-fx-font-size: 16px; -fx-background-color: linear-gradient(#FFA500, #FF8C00); -fx-text-fill: white; -fx-padding: 10px;");
        logOutButton.setOnAction(e -> openLoginPage(stage));



        // Add all components to VBox
        driverDashboardVBox.getChildren().addAll(
                profileCircle,
                driverDashboardTitle,
                locationQuery,
                availabilityToggle,
                rideSection,
                currentRideStatus,
                endRideButton,
                logOutButton);

        ScrollPane scrollPane = new ScrollPane(driverDashboardVBox);
        scrollPane.setFitToWidth(true); // Ensures the content fits the ScrollPane width
        scrollPane.setStyle("-fx-background-color: #2C2C2C;");

        root.getChildren().add(scrollPane);

        Scene driverDashboardScene = new Scene(root, 800, 600);
        stage.setScene(driverDashboardScene);
    }
    private void showDriverInfoWindow(Stage stage,Driver driver) {
        Stage accountDetailsStage = new Stage();
        accountDetailsStage.setTitle("Account Details");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))); // Black background

        // Create labels and text fields
        Label nameLabel = new Label("Name:");
        nameLabel.setTextFill(Color.ORANGE);
        TextField nameField = new TextField(driver.getName());

        Label idNumberLabel = new Label("ID Number:");
        idNumberLabel.setTextFill(Color.ORANGE);
        TextField idNumberField = new TextField(driver.getIdNumber());

        Label carNumberLabel = new Label("Car Registration Number:");
        carNumberLabel.setTextFill(Color.ORANGE);
        TextField carNumberField = new TextField(driver.getCarRegistration());

        Label phoneNumberLabel = new Label("Phone Number:");
        phoneNumberLabel.setTextFill(Color.ORANGE);
        TextField phoneNumberField = new TextField(driver.getPhoneNumber());

        Label availabilityLabel = new Label("Status:");
        availabilityLabel.setTextFill(Color.ORANGE);
        Label availability = new Label();
        availability.setTextFill(Color.WHITE);
        if (driver.isAvailable())availability.setText("Available");
        else availability.setText("Not Available");

        Label locationLabel = new Label("Location:");
        locationLabel.setTextFill(Color.ORANGE);
        TextField locationField = new TextField(driver.getLocation().toString()); // Assuming Location has a meaningful toString method

        // Create buttons
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;"); // Orange background with black text
        Button logoutButton = new Button("Delete account");
        logoutButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;"); // Orange background with black text

        // Add components to the grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(idNumberLabel, 0, 1);
        grid.add(idNumberField, 1, 1);
        grid.add(locationLabel, 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(phoneNumberLabel, 0, 3);
        grid.add(phoneNumberField, 1, 3);
        grid.add(carNumberLabel, 0, 4);
        grid.add(carNumberField, 1, 4);
        grid.add(availabilityLabel, 0, 5);
        grid.add(availability, 1, 5);
        grid.add(saveButton, 0, 6);
        grid.add(logoutButton, 1, 6);


        // Set the action for the save button
        saveButton.setOnAction(e -> {
            // Update passenger details
            driver.setName(nameField.getText());
            driver.setIdNumber(idNumberField.getText());
            driver.setLocation(Location.createLocation(locationField.getText()));
            driver.setPhoneNumber(phoneNumberField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Account details updated successfully.");
            alert.showAndWait();
        });

        // Set the action for the logout button
        logoutButton.setOnAction(e -> {
            accountDetailsStage.close();
            deleteAccount(stage,driver);
            openLoginPage(stage); // Call method to go back to login
        });

        // Create a Scene and display the stage
        Scene scene = new Scene(grid, 400, 300);
        accountDetailsStage.setScene(scene);
        accountDetailsStage.initModality(Modality.APPLICATION_MODAL);
        accountDetailsStage.showAndWait();
    }

    private void viewRideRequests(Driver driver, Text currentRideStatus, Button endRideButton) {
        ArrayList<Ride> rideRequests = driver.getRideRequests();

        if (rideRequests.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Ride Requests");
            alert.setHeaderText(null);
            alert.setContentText("There are no ride requests available at the moment.");
            alert.showAndWait();
        } else {
            Stage requestStage = new Stage();
            VBox layout = new VBox(10);
            layout.setStyle("-fx-padding: 20px;");

            for (Ride request : rideRequests) {
                HBox requestBox = new HBox(10);
                Label requestLabel = new Label(request.toString());

                // Accept Button
                Button acceptButton = new Button("Yes");
                acceptButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                acceptButton.setOnAction(e -> {
                    driver.acceptRide(request); // Accept the ride
                    driver.setCurrentRide(request); // Set as current ride
                    rideRequests.remove(request); // Remove the request from the list

                    // Update dashboard components
                    currentRideStatus.setText("Current Ride: " + request.toString());
                    endRideButton.setVisible(true);

                    requestStage.close(); // Close the requests window
                });

                // Decline Button
                Button declineButton = new Button("No");
                declineButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                declineButton.setOnAction(e -> {
                    driver.declineRide(request); // Decline the ride
                    rideRequests.remove(request); // Remove the request
                    requestStage.close();
                });

                // Add the label and buttons to the layout
                requestBox.getChildren().addAll(requestLabel, acceptButton, declineButton);
                layout.getChildren().add(requestBox);
            }

            Scene requestScene = new Scene(layout, 400, 300);
            requestStage.setTitle("Ride Requests");
            requestStage.setScene(requestScene);
            requestStage.show();
        }
    }





}
