package com.social.Controllers;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    public void initialize() {
        loginButton.setDisable(true);
        
        // Use a ChangeListener to listen for text changes in userNameField and passwordField
        userNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Username field changed from " + oldValue + " to " + newValue);
            checkFieldsForEmptyValues();
        });
    
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Password field changed from " + oldValue + " to " + newValue);
            checkFieldsForEmptyValues();
        });
    }
    
    private void checkFieldsForEmptyValues() {
        if (userNameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
            loginButton.setDisable(true);
        } else {
            loginButton.setDisable(false);
        }
    }
    
    // Method called when loginButton is clicked
  @FXML
private void loginButtonAction(ActionEvent event) {
    String username = userNameField.getText();
    String password = passwordField.getText();

    // Create a Connection object
    Connection conn = null;

    // Database connection parameters
    String url = "jdbc:mysql://localhost:3306/cbu_social_network";
    String dbUsername = "root";
    String dbPassword = "Biokukesi";

    try {
        // Connect to the database
        conn = DriverManager.getConnection(url, dbUsername, dbPassword);

        // Create a Statement object
        Statement stmt = conn.createStatement();

        // Execute a SELECT query
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'");

        // If the query returns a result, then the username and password combination is valid
        if (rs.next()) {
            System.out.println("Login successful!");

            // Clear the text fields
            userNameField.clear();
            passwordField.clear();

            // Navigate to the next screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContactsUi.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Hide the current (login) window
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } else {
            System.out.println("Invalid username or password.");

            // Clear the text fields
            userNameField.clear();
            passwordField.clear();
        }

        // Close the connection
        conn.close();
    } catch (SQLException e) {
        // Handle any errors that may occur during the connection or query execution
        System.out.println(e.getMessage());
    } catch (IOException e) {
        // Handle any errors that may occur while loading the FXML file
        System.out.println(e.getMessage());
    }
}

    // Method called when signUpButton is clicked
    @FXML
    private void signUpButtonAction() {
        // Add your signup code here
    }
}
