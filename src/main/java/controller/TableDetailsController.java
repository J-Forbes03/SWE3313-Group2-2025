package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.MainApp;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the TableDetails.fxml view.
 * Handles managing individual table orders, marking tables as dirty or clean,
 * and navigating between the table details and menu selection screens.
 */
public class TableDetailsController {

    @FXML
    private Label tableLabel;

    @FXML
    private ListView<String> orderListView;

    @FXML
    private Button markButton; // Button to mark table dirty or clean

    private static int selectedTableNumber;

    /**
     * Map to track orders for each table.
     * Key = table number, Value = list of ordered items.
     */
    private static final Map<Integer, ObservableList<String>> allTableOrders = new HashMap<>();

    /**
     * Sets the currently selected table number.
     *
     * @param tableNumber The table number selected.
     */
    public static void setSelectedTable(int tableNumber) {
        selectedTableNumber = tableNumber;
    }

    /**
     * Initializes the table details screen.
     * Loads the table's current orders and updates the mark button text.
     */
    @FXML
    private void initialize() {
        tableLabel.setText("Table " + selectedTableNumber + " Details");

        // If table doesn't have an order list yet, create one
        allTableOrders.putIfAbsent(selectedTableNumber, FXCollections.observableArrayList());

        orderListView.setItems(allTableOrders.get(selectedTableNumber));

        updateMarkButtonText();
    }

    /**
     * Updates the text on the mark button depending on table's status (dirty or clean).
     */
    private void updateMarkButtonText() {
        int status = FloorLayoutController.tableStatus.getOrDefault(selectedTableNumber, 0);
        if (status == 2) {
            markButton.setText("Mark Clean");
        } else {
            markButton.setText("Mark Dirty");
        }
    }

    /**
     * Handles navigating to the menu selection screen to add an item.
     */
    @FXML
    private void handleAddItem() {
        try {
            MainApp.changeScene("MenuSelection.fxml");
        } catch (Exception e) {
            System.out.println("Error opening Menu Selection: " + e.getMessage());
        }
    }

    /**
     * Handles marking the table as dirty or clean.
     * Updates the table status accordingly and returns to the floor layout.
     */
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

    /**
     * Handles navigating back to the floor layout view.
     */
    @FXML
    private void handleBackToFloor() {
        try {
            MainApp.changeScene("FloorLayout.fxml");
        } catch (Exception e) {
            System.out.println("Error returning to Floor Layout: " + e.getMessage());
        }
    }

    /**
     * Deletes the selected item from the table's order list.
     * If no items remain, the table is marked as open.
     */
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

    /**
     * Clears all items from the table's order list.
     * After clearing, the table is marked as open (green).
     */
    @FXML
    private void handleClearAllItems() {
        allTableOrders.get(selectedTableNumber).clear();

        // After clearing, set table back to open (green)
        FloorLayoutController.tableStatus.put(selectedTableNumber, 0); // open
    }

    /**
     * Adds a new item to the selected table's order list.
     * If the table is not dirty, it is marked as occupied.
     *
     * @param item The food item to add.
     */
    public static void addOrderItem(String item) {
        allTableOrders.putIfAbsent(selectedTableNumber, FXCollections.observableArrayList());
        allTableOrders.get(selectedTableNumber).add(item);

        int currentStatus = FloorLayoutController.tableStatus.getOrDefault(selectedTableNumber, 0);
        if (currentStatus != 2) { // don't overwrite dirty tables
            FloorLayoutController.tableStatus.put(selectedTableNumber, 1); // occupied
        }
    }
}
