package com.timprogrammiert.chat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private TextArea txtAreaGroupChat;
    @FXML
    private TextArea txtAreaMessage;
    @FXML
    private ListView<String> connectedUsersListView;

    public void initialize(){
        ObservableList<String> userTest = FXCollections.observableArrayList();
        txtAreaMessage.setWrapText(true);
        connectedUsersListView.setItems(userTest);

        for (int i = 0; i < 10; i++) {
            userTest.add("TestUser" + i);
        }

    }
}