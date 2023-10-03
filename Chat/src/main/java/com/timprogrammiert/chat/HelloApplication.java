package com.timprogrammiert.chat;

import com.timprogrammiert.chat.Client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Client client;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setHeight(440);
        stage.setResizable(false);
        stage.show();

        try {
            client = new Client();
            client.SetController(fxmlLoader.getController());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            Platform.exit();
        }

    }

    @Override
    public void stop() throws Exception {
        System.out.println("Closing");
        if(client != null) client.StopClient();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}