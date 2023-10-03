package com.timprogrammiert.chat.Server;

import com.timprogrammiert.chat.Message.Message;

import java.io.*;
import java.net.Socket;

/**
 * created by tmatz on 03.10.2023
 */

/**
 * ClientThread runs ServerSide! For each new Client connecting to Server one ClientThread will be created
 * ClientThread's communicate with Server and Client, there's no coummunication between Client and Server (Class) directly
 */
public class ClientThread{

    private final Socket clientSocket;
    private final Server server;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ObjectInputStream objectInputStream;

    private Thread recieverThread;

    private Message message;

    public ClientThread(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public void StartClientThread(){
        createStreams();
        recieverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                recieverLoop();
            }
        });
        recieverThread.start();
    }

    /**
     * Send Message vai Socket with
     * @param message Message Object to be sent
     */

    public void SendMessage(Message message){
        try{
                objectOutputStream.writeObject(message);
                objectOutputStream.reset();
        }catch (IOException e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    /**
     * Calls the Server Method to remove this from its list
     */
    private void cancelClientConnection(){
        server.RemoveCanceledConnection(this);
    }


    /**
     * Core Mechanimism to recieve Messages
     */
    private void recieverLoop(){
        try {
            Message message;
            while((message = (Message) objectInputStream.readObject()) != null){
                if(!message.getConnectionStillActive()) {
                    cancelClientConnection();
                    recieverThread.interrupt();
                    return;
                }


                System.out.println("Message recieved! " + message.getMessage());
                server.BroadcastMessage(message);
            }
        }catch (IOException | ClassNotFoundException e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    private void createStreams(){
        try {
            outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

            inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }


}
