package com.timprogrammiert.chat.Client;

import com.timprogrammiert.chat.Message.Message;
import com.timprogrammiert.chat.Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * created by tmatz on 03.10.2023
 */
public class Client{
    private String username;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;

    private Thread senderThread;
    private Thread recieverThread;

    public Client() {
        try {
            socket = new Socket("localhost", Server.SERVER_PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            startRecieverThread();
            startSendingThread();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO NEED 2 THREADS - 1 FOR RECIEVING - 1 FOR SENDING

    private void startRecieverThread(){

    }

    private void startSendingThread(){
        senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    objectOutputStream = new ObjectOutputStream(outputStream);
                    Scanner inputScanner = new Scanner(System.in);
                    while(true){
                        Message m = new Message();
                        m.SetMessage(inputScanner.nextLine());
                        objectOutputStream.writeObject(m);
                    }


                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        });
        senderThread.start();
    }

    private void sendMessage(){
        try{

            System.out.println("Test1");


            System.out.println("Test2");



            objectOutputStream = new ObjectOutputStream(outputStream);

            Message m = new Message();
            m.SetMessage("Test");
            objectOutputStream.writeObject(m);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void recieveMessage(){

       //objectInputStream = new ObjectInputStream(inputStream);
    }

    public static void main(String[] args) {
        Client client = new Client();

    }
}
