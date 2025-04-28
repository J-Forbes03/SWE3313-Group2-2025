package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.MainApp;

import java.util.HashMap;
import java.util.Map;


public class FloorLayoutController {

    @FXML
    private GridPane tableGrid;

    // Table number -> Status (0 = open, 1 = occupied, 2 = dirty)
    public static final Map<Integer, Integer> tableStatus = new HashMap<>();

    @FXML
    private void initialize() {
        int tableNumber = 1;

        for (int row = 0; row < 5; row++) {        // 5 rows
            for (int col = 0; col < 6; col++) {     // 6 columns
                if (tableNumber <= 30) {
                    VBox tableBox = createTable(tableNumber);
                    tableGrid.add(tableBox, col, row);
                    tableNumber++;
                }
            }
        }
    }

    private VBox createTable(int tableNumber) {
        VBox tableBox = new VBox(15); // more vertical space between table label and seats
        tableBox.setPrefSize(140, 140); // make the whole table bigger
        tableBox.setStyle("-fx-alignment: center; -fx-background-color: lightgreen; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label tableLabel = new Label("Table " + tableNumber);
        tableLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox seatsRow1 = new HBox(15);
        seatsRow1.setStyle("-fx-alignment: center;");

        HBox seatsRow2 = new HBox(15);
        seatsRow2.setStyle("-fx-alignment: center;");

        for (int i = 0; i < 2; i++) {
            Label seat = new Label("●");
            seat.setStyle("-fx-font-size: 26px;");
            seatsRow1.getChildren().add(seat);
        }
        for (int i = 0; i < 2; i++) {
            Label seat = new Label("●");
            seat.setStyle("-fx-font-size: 26px;");
            seatsRow2.getChildren().add(seat);
        }

        tableBox.getChildren().addAll(tableLabel, seatsRow1, seatsRow2);

        tableBox.setOnMouseClicked(event -> handleTableClick(tableNumber));

        tableStatus.putIfAbsent(tableNumber, 0);

        updateTableColor(tableBox, tableStatus.get(tableNumber));

        return tableBox;
    }



    public static void updateTableColor(Pane pane, int status) {
        String color;
        switch (status) {
            case 0 -> color = "lightgreen";   // Open
            case 1 -> color = "gold";          // Occupied
            case 2 -> color = "indianred";     // Dirty
            default -> color = "lightgray";
        }
        pane.setStyle("-fx-alignment: center; -fx-background-color: " + color + "; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5;");
    }

    private void handleTableClick(int tableNumber) {
        TableDetailsController.setSelectedTable(tableNumber);
        try {
            MainApp.changeScene("TableDetails.fxml");
        } catch (Exception e) {
            System.out.println("Error opening Table Details: " + e.getMessage());
        }
    }

    public static void refreshFloor(GridPane tableGrid) {
        for (var node : tableGrid.getChildren()) {
            if (node instanceof VBox tableBox) {
                Label label = (Label) tableBox.getChildren().get(0); // First child = Label "Table X"
                String text = label.getText();
                if (text.startsWith("Table")) {
                    int tableNum = Integer.parseInt(text.split(" ")[1]);
                    updateTableColor(tableBox, tableStatus.getOrDefault(tableNum, 0));
                }
            }
        }
    }

    @FXML
    private void handleLogout() {
        try {
            MainApp.changeScene("Login.fxml");
        } catch (Exception e) {
            System.out.println("Error returning to login: " + e.getMessage());
        }
    }
}
