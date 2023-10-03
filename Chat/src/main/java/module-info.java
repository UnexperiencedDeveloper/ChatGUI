module com.timprogrammiert.chat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;


    exports com.timprogrammiert.chat;
    opens com.timprogrammiert.chat to javafx.fxml;
}