
module com.example.back {

    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens com.example.back to javafx.fxml;

    exports com.example.back;
}
