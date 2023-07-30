package com.social;
import java.io.IOException;
import java.sql.SQLException;

import com.social.Models.DatabaseManager;

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

    try {
        DatabaseManager db = DatabaseManager.getInstance();
        boolean isRegistered = db.loginUser(username, password);
        if (isRegistered) {
            // Navigate to the signup screen
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/social/ContactsUI.fxml"));
            Parent root = fxmlLoader.load();            
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Hide the current (login) window
        ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            System.out.println("Login failed, please try again!");
        }
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
private void signUpButtonAction(ActionEvent event) {
    try {
        // Navigate to the signup screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Hide the current (login) window
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        System.err.println("Error loading SignUp.fxml: " + e.getMessage());
    }
}
}
