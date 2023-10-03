package com.timprogrammiert.chat.Server;

import com.timprogrammiert.chat.Message.Message;

import java.io.*;
import java.net.Socket;

/**
 * created by tmatz on 03.10.2023
 */
public class ClientThread{

    private Socket clientSocket;
    private Server server;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private OutputStream outputStream;

    private Thread recieverThread;

    private Message message;

    public ClientThread(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public void StartClientThread(){

        recieverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                recieverLoop();
            }
        });
        recieverThread.start();
        //System.out.println("ClientThread started!");
    }

    public void SendMessage(Message message){
        try{
            outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(message);
        }catch (IOException e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    private void cancelClientConnection(){
        server.RemoveCanceledConnection(this);
    }


    private void recieverLoop(){
        try {
            inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            Message message;
            while((message = (Message) objectInputStream.readObject()) != null){
                if(!message.getConnectionStillActive()) {
                    cancelClientConnection();
                    recieverThread.interrupt();
                    return;
                }


                System.out.println("Message recieved! " + message.GetMessage());
                server.BroadcastMessage(message);
            }
        }catch (IOException | ClassNotFoundException e){
            throw  new RuntimeException(e.getMessage());
        }
    }


}
