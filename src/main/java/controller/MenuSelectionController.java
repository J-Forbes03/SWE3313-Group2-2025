package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.MainApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the MenuSelection.fxml view.
 * Handles displaying food categories, listing menu items,
 * and adding selected items to a table's order.
 */
public class MenuSelectionController {

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ListView<String> itemListView;

    /**
     * Map to store the restaurant menu.
     * Key = category name, Value = list of menu items under that category.
     */
    private final Map<String, List<String>> menuMap = new HashMap<>();

    /**
     * Initializes the menu selection screen.
     * Sets up menu categories and handles category selection changes.
     */
    @FXML
    private void initialize() {
        setupMenu();
        categoryComboBox.setItems(FXCollections.observableArrayList(menuMap.keySet()));

        categoryComboBox.setOnAction(event -> {
            String selectedCategory = categoryComboBox.getValue();
            if (selectedCategory != null) {
                List<String> items = menuMap.get(selectedCategory);
                itemListView.setItems(FXCollections.observableArrayList(items));
            }
        });
    }

    /**
     * Populates the menuMap with predefined categories and menu items.
     */
    private void setupMenu() {
        menuMap.put("Soups/Salads", List.of("Caesar Salad", "Garden Salad", "Tomato Soup", "Chicken Noodle Soup"));
        menuMap.put("Appetizers", List.of("Mozzarella Sticks", "Wings", "Queso Dip", "Spinach Dip"));
        menuMap.put("Entrees", List.of("Steak", "Grilled Chicken", "Catfish", "Fried Chicken"));
        menuMap.put("Desserts", List.of("Cheesecake", "Chocolate Cake", "Ice Cream", "Apple Pie"));
        menuMap.put("Beverages", List.of("Coffee", "Sweet Tea", "Coke", "Orange Juice"));
    }

    /**
     * Handles adding the selected item from the menu to the current table's order.
     * Displays confirmation or warning alerts depending on selection.
     */
    @FXML
    private void handleAddItem() {
        String selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TableDetailsController.addOrderItem(selectedItem);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item Added");
            alert.setHeaderText(null);
            alert.setContentText(selectedItem + " added to order.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to add.");
            alert.showAndWait();
        }
    }

    /**
     * Handles navigating back to the Table Details screen.
     */
    @FXML
    private void handleBackToTable() {
        try {
            MainApp.changeScene("TableDetails.fxml");
        } catch (Exception e) {
            System.out.println("Error returning to Table Details: " + e.getMessage());
        }
    }
}
