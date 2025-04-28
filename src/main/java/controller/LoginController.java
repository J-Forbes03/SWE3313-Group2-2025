package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.MainApp;
import model.Waiter;
import model.WaiterDataLoader;

import java.util.List;


public class LoginController {

    @FXML
    private TextField idField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final List<Waiter> waiters;

    public LoginController() {
        waiters = WaiterDataLoader.loadWaiters();
    }


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
            errorLabel.setText("Invalid ID or Password.");
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid ID number.");
        } catch (Exception e) {
            errorLabel.setText("Error: " + e.getMessage());
        }
    }
}