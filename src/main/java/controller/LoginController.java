package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MainApp;
import model.Waiter;
import model.WaiterDataLoader;

import java.util.List;

/**
 * Controller for the Login.fxml view.
 * Handles waiter login authentication by verifying ID and password.
 */
public class LoginController {

    @FXML
    private TextField idField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final List<Waiter> waiters;

    /**
     * Constructor that loads waiter data from the file using WaiterDataLoader.
     */
    public LoginController() {
        waiters = WaiterDataLoader.loadWaiters();
    }

    /**
     * Handles the login button action.
     * Checks entered ID and password against loaded waiter data.
     * If successful, switches scene to Floor Layout.
     * If failed, displays an appropriate error message.
     */
    @FXML
    private void handleLogin() {
        String idText = idField.getText().trim();
        String password = passwordField.getText();

        try {
            int id = Integer.parseInt(idText);

            for (Waiter waiter : waiters) {
                if (waiter.getId() == id && waiter.getPassword().equals(password)) {
                    MainApp.changeScene("FloorLayout.fxml");
                    return;
                }
            }
            // No matching waiter found
            errorLabel.setText("Invalid ID or Password.");
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid ID number.");
        } catch (Exception e) {
            errorLabel.setText("Error: " + e.getMessage());
        }
    }
}
