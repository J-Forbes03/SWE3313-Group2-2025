package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.MainApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MenuSelectionController {

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ListView<String> itemListView;

    // Category -> List of food items
    private final Map<String, List<String>> menuMap = new HashMap<>();

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

    private void setupMenu() {
        menuMap.put("Soups/Salads", List.of("Caesar Salad", "Garden Salad", "Tomato Soup", "Chicken Noodle Soup"));
        menuMap.put("Appetizers", List.of("Mozzarella Sticks", "Wings", "Queso Dip", "Spinach Dip"));
        menuMap.put("Entrees", List.of("Steak", "Grilled Chicken", "Catfish", "Fried Chicken"));
        menuMap.put("Desserts", List.of("Cheesecake", "Chocolate Cake", "Ice Cream", "Apple Pie"));
        menuMap.put("Beverages", List.of("Coffee", "Sweet Tea", "Coke", "Orange Juice"));
    }

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

    @FXML
    private void handleBackToTable() {
        try {
            MainApp.changeScene("TableDetails.fxml");
        } catch (Exception e) {
            System.out.println("Error returning to Table Details: " + e.getMessage());
        }
    }
}
