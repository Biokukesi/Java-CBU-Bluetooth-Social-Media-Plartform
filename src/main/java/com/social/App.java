package com.social;

import com.social.Models.DatabaseManager;
import com.social.Networking.BluetoothManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            boolean isConnected = db.testConnection();
            System.out.println("Connected to the database: " + isConnected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Instantiate and test BluetoothManager
        BluetoothManager bm = new BluetoothManager();
        bm.discoverDevices();

        // Note that discovering devices might take some time and is asynchronous
        // Therefore, you might want to check the devicesDiscovered at a later time
        
        scene = new Scene(loadFXML("Login"), 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
