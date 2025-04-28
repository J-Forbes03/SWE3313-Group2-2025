module com.example.prototype2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.fxml;
}