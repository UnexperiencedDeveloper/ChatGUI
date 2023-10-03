package com.timprogrammiert.chat;

import com.timprogrammiert.chat.Client.Client;
import com.timprogrammiert.chat.Message.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatController {

    @FXML
    private TextArea txtAreaGroupChat;
    @FXML
    private TextArea txtAreaMessage;
    @FXML
    private ListView<String> connectedUsersListView;
    public Client client;

    public void initialize(){
        ObservableList<String> userTest = FXCollections.observableArrayList();
        txtAreaMessage.setWrapText(true);
        txtAreaGroupChat.setWrapText(true);
        connectedUsersListView.setItems(userTest);

        for (int i = 0; i < 10; i++) {
            userTest.add("TestUser" + i);
        }

    }

    @FXML
    void sendMessage(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)) {
            // Create Message Object, Send Message
            Message message = new Message();
            message.setConnectionStillActive(true);
            message.setText(txtAreaMessage.getText());
            client.sendMessage(message);

            txtAreaMessage.setText("");
        }
    }

    public void RecieveMessage(Message messageObject){
        txtAreaGroupChat.appendText("[" + messageObject.usernameSentFrom + "]: " + messageObject.getMessage() + "");
    }
}