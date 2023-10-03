package com.timprogrammiert.chat.Client;

import com.timprogrammiert.chat.HelloController;
import com.timprogrammiert.chat.Message.Message;
import com.timprogrammiert.chat.Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * created by tmatz on 03.10.2023
 */
public class Client{

    public AtomicBoolean threadRunning = new AtomicBoolean(false);

    private String username;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    private InputStream inputStream;
    private OutputStream outputStream;

    private Thread senderThread;
    private Thread recieverThread;

    private HelloController helloController;

    public Client() throws IOException{
        socket = new Socket("localhost", Server.SERVER_PORT);
        createStreams();

        threadRunning.set(true);
        startRecieverThread();
        startSendingThread();
    }

    public void SetController(HelloController controller){
        helloController = controller;
    }

    public void StopClient(){
        try {
            Message stopConnection = new Message();
            stopConnection.setConnectionStillActive(false);
            sendMessage(stopConnection);
            socket.close();
            inputStream.close();
            outputStream.close();
            objectOutputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        threadRunning.set(false);
        //senderThread.interrupt();
        recieverThread.interrupt();
    }

    private void createStreams(){
        try {
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);

            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startRecieverThread(){
        recieverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message;
                    while(threadRunning.get()){
                        message = (Message) objectInputStream.readObject();
                        System.out.println(message.getMessage()); // -> DEBUG
                        helloController.AddNewMessage(message);

                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        recieverThread.start();
    }

    private void startSendingThread(){
        /*senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner inputScanner = new Scanner(System.in);
                while(threadRunning.get()){
                    // DEBUGGING - One Client as GUI, one Client as Console
                    Message m = new Message();

                    // May not close Properly because nextLine is still reading input
                    m.setText(inputScanner.nextLine());
                    m.setConnectionStillActive(true);
                    sendMessage(m);
                }
            }
        });
        senderThread.start();*/
    }

    public void sendMessage(Message message){
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        try {
            Client client = new Client();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }
}
