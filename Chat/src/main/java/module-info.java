module com.timprogrammiert.chat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.timprogrammiert.chat to javafx.fxml;
    exports com.timprogrammiert.chat;
}