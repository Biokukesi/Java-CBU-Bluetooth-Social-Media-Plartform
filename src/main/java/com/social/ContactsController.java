package com.social;

import java.io.IOException;
import java.util.Vector;

import javax.bluetooth.RemoteDevice;
import com.social.Networking.BluetoothManager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class ContactsController {
    @FXML
    private TextField SEARCH_TXT;

    @FXML
    private Button ADD_NEW_BTN;

    @FXML
    private ScrollPane SCROLL_BAR_CONTACTS;

    @FXML
    private VBox MAIN_CONTACTS;

    @FXML
    private ScrollPane SCROLL_BAR;

    @FXML
    private VBox MSGS_CONTAINER;

    @FXML
    private HBox TITLE_FINAL_CONTAINER;

    @FXML
    private AnchorPane ADDUSER_PANE;

    @FXML
    private Button ADDUSER_BTN;

    @FXML
    private TextField USERNAME_TXT;

    @FXML
    private Button CLOSE_ADD_BTN;

    @FXML
    private Label ERR_NUM_LBL;

    @FXML
    private TextField PHONENUMBER_TXT;

    @FXML
    private Label IMAGE_NAME_LBL;

    @FXML
    private StackPane IMAGE_CONTAINER;

    @FXML
    private TextArea MSG_TXT;

    @FXML
    private Button SEND_BTN;

    @FXML
    private Button FILES_BTN;

    @FXML
    private HBox MORE_BTNS;

    @FXML
    private Button EMOJI_BTN;

    @FXML
    void showNewDialog(ActionEvent event) {
        // TODO: implement
    }

    @FXML
    void closeNewDialog(ActionEvent event) {
        // TODO: implement
    }

    @FXML
    void closeAddPane(ActionEvent event) {
        // TODO: implement
    }

    @FXML
    void sendMessage(ActionEvent event) {
        // TODO: implement
    }

    @FXML
    void receiveMessage(ActionEvent event) {
        // TODO: implement
    }
    @FXML
    private void updateContacts() {
        Platform.runLater(() -> MAIN_CONTACTS.getChildren().clear()); // Clear existing contacts
    
        Task<Vector<RemoteDevice>> discoverDevicesTask = new Task<Vector<RemoteDevice>>() {
            @Override
            protected Vector<RemoteDevice> call() throws Exception {
               // It becomes
BluetoothManager manager = new BluetoothManager();
return manager.discoverDevices().join();
            }
        };


        MAIN_CONTACTS.setSpacing(10); // Add some space between the VBox children
        MAIN_CONTACTS.setStyle("-fx-padding: 10;"); // Add some padding to the VBox
        
        discoverDevicesTask.setOnSucceeded(event -> {
            Vector<RemoteDevice> devices = discoverDevicesTask.getValue();
            System.out.println("Device discovery task succeeded. Devices found: " + devices.size());
            Platform.runLater(() -> {
                for (RemoteDevice device : devices) {
                    try {
                        String friendlyName = device.getFriendlyName(false);
                        // Create a new button for each device
                        Button deviceButton = new Button(friendlyName);
                        deviceButton.setMinSize(70, 30); // Set a smaller size
                        deviceButton.setStyle("-fx-background-color: ; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 2;"); // Add some color and border
        
                        HBox buttonBox = new HBox(); // Create an HBox for each set of buttons
                        buttonBox.setSpacing(10); // Add some space between the buttons
        
                        deviceButton.setOnAction(e -> {
                            if (buttonBox.getChildren().size() > 1) {
                                // If "Connect" and "Cancel" buttons already exist, hide them by removing the buttonBox
                                MAIN_CONTACTS.getChildren().remove(buttonBox);
                                return;
                            }
        
                            // Create "Connect" and "Cancel" buttons
                            Button connectButton = new Button("Connect");
                            connectButton.setMinSize(70, 30); // Set a smaller size
                            connectButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 2;"); // Add some color and border
                            connectButton.setOnAction(ev -> {
                                System.out.println("Connect clicked for: " + friendlyName);
                                // TODO: Implement connection logic
                            });
        
                            Button cancelButton = new Button("Cancel");
                            cancelButton.setMinSize(70, 30); // Set a smaller size
                            cancelButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 2;"); // Add some color and border
                            cancelButton.setOnAction(ev -> {
                                System.out.println("Cancel clicked for: " + friendlyName);
                                // Hide the "Connect" and "Cancel" buttons by removing the buttonBox
                                MAIN_CONTACTS.getChildren().remove(buttonBox);
                                ev.consume(); // Consume the event to prevent it from propagating to the deviceButton
                            });
                            
        
                            // Add the "Connect" and "Cancel" buttons to the HBox
                            buttonBox.getChildren().addAll(connectButton, cancelButton);
                        });
        
                        // Add the device button to the HBox
                        buttonBox.getChildren().add(deviceButton);
                        // Add the HBox to the parent container
                        MAIN_CONTACTS.getChildren().add(buttonBox);
                    } catch (IOException ex) {
                        System.err.println("Error getting friendly name for device: " + ex.getMessage());
                    }
                }
            });
        });
        
        
    
        // Handle any exceptions
        discoverDevicesTask.setOnFailed(event -> {
            Throwable exception = discoverDevicesTask.getException();
            System.err.println("Device discovery task failed with exception: " + exception);
            Platform.runLater(() -> {
                exception.printStackTrace();
                Label errorLabel = new Label("Error: " + exception.getMessage());
                MAIN_CONTACTS.getChildren().add(errorLabel);
            });
        });
    
        // Start the task in a new thread
        new Thread(discoverDevicesTask).start();
    }
    
   
}


