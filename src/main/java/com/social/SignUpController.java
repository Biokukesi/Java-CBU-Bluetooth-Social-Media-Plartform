package com.social;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import com.social.Models.DatabaseManager;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        // add your initialization logic here if any
    }

    // Method called when signUpButton is clicked
    @FXML
    private void signUpButtonAction(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            System.out.println("Please fill all the fields.");
            return;
        }

        // Call your signup method from DatabaseManager here.
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            boolean isRegistered = db.registerUser(username, password, email, phoneNumber);
            if (isRegistered) {
                System.out.println("Registration successful!");
                // Clear the text fields
                usernameField.clear();
                passwordField.clear();
                emailField.clear();
                phoneNumberField.clear();
                // Navigate to login screen or open welcome screen, you'll need to define this
            } else {
                System.out.println("Registration failed, please try again!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
