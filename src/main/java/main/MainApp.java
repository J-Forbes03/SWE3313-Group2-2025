package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the Restaurant Automation application.
 * Handles launching the application and switching between scenes.
 */
public class MainApp extends Application {

    /**
     * Primary stage used for displaying scenes.
     */
    private static Stage primaryStage;

    /**
     * Starts the application by loading the login scene.
     *
     * @param stage The primary stage provided by the JavaFX runtime.
     * @throws Exception if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("*Restaurant Name*");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Changes the current scene to a new one based on the provided FXML file.
     *
     * @param fxml The name of the FXML file to load (e.g., "FloorLayout.fxml").
     * @throws Exception if the FXML file cannot be loaded.
     */
    public static void changeScene(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/" + fxml));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
