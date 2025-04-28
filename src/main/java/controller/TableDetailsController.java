package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.MainApp;

import java.util.HashMap;
import java.util.Map;

public class TableDetailsController {

    @FXML
    private Label tableLabel;

    @FXML
    private ListView<String> orderListView;

    @FXML
    private Button markButton; // mark dirty or clean button

    private static int selectedTableNumber;


    private static final Map<Integer, ObservableList<String>> allTableOrders = new HashMap<>();

    public static void setSelectedTable(int tableNumber) {
        selectedTableNumber = tableNumber;
    }

    @FXML
    private void initialize() {
        tableLabel.setText("Table " + selectedTableNumber + " Details");

        // If table doesn't have an order list yet, create one
        allTableOrders.putIfAbsent(selectedTableNumber, FXCollections.observableArrayList());

        orderListView.setItems(allTableOrders.get(selectedTableNumber));

        updateMarkButtonText();
    }

    private void updateMarkButtonText() {
        int status = FloorLayoutController.tableStatus.getOrDefault(selectedTableNumber, 0);
        if (status == 2) {
            markButton.setText("Mark Clean");
        } else {
            markButton.setText("Mark Dirty");
        }
    }

    @FXML
    private void handleAddItem() {
        try {
            MainApp.changeScene("MenuSelection.fxml");
        } catch (Exception e) {
            System.out.println("Error opening Menu Selection: " + e.getMessage());
        }
    }

    @FXML
    private void handleMarkDirty() {
        int currentStatus = FloorLayoutController.tableStatus.getOrDefault(selectedTableNumber, 0);
        if (currentStatus == 2) {
            FloorLayoutController.tableStatus.put(selectedTableNumber, 0); // mark clean
        } else {
            FloorLayoutController.tableStatus.put(selectedTableNumber, 2); // mark dirty
        }
        handleBackToFloor();
    }

    @FXML
    private void handleBackToFloor() {
        try {
            MainApp.changeScene("FloorLayout.fxml");
        } catch (Exception e) {
            System.out.println("Error returning to Floor Layout: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteItem() {
        String selectedItem = orderListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            allTableOrders.get(selectedTableNumber).remove(selectedItem);

            // If no more orders left, mark table open
            if (allTableOrders.get(selectedTableNumber).isEmpty()) {
                FloorLayoutController.tableStatus.put(selectedTableNumber, 0); // open (green)
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleClearAllItems() {
        allTableOrders.get(selectedTableNumber).clear();

        // After clearing, set table back to open (green)
        FloorLayoutController.tableStatus.put(selectedTableNumber, 0); // open
    }


    public static void addOrderItem(String item) {
        allTableOrders.putIfAbsent(selectedTableNumber, FXCollections.observableArrayList());
        allTableOrders.get(selectedTableNumber).add(item);

        int currentStatus = FloorLayoutController.tableStatus.getOrDefault(selectedTableNumber, 0);
        if (currentStatus != 2) { // don't overwrite dirty tables
            FloorLayoutController.tableStatus.put(selectedTableNumber, 1); // occupied
        }
    }
}
