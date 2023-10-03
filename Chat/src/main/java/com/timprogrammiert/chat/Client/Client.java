package com.timprogrammiert.chat.Client;

import com.timprogrammiert.chat.HelloController;
import com.timprogrammiert.chat.Message.Message;
import com.timprogrammiert.chat.Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private ExecutorService executorService;

    public Client() throws RuntimeException{
        try {
            socket = new Socket("localhost", Server.SERVER_PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            threadRunning.set(true);
            //executorService = Executors.newFixedThreadPool(2);
            startRecieverThread();
            startSendingThread();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void SetController(HelloController controller){
        helloController = controller;
    }

    public void StopClient(){
        try {
            sendMessage(false); // Test for Server
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        threadRunning.set(false);
        senderThread.interrupt();
        try {
            socket.close();
            inputStream.close();
            //objectInputStream.close();
            outputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        System.out.println(senderThread.getState());
    }

    //TODO NEED 2 THREADS - 1 FOR RECIEVING - 1 FOR SENDING

    private void startRecieverThread(){

    }

    private void startSendingThread(){
        senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Staretring Thread");
                try {
                    objectOutputStream = new ObjectOutputStream(outputStream);
                    //Scanner inputScanner = new Scanner(System.in);
                    while(threadRunning.get()){
                       senderThread.sleep(300);
                       sendMessage(true);
                    }


                } catch (IOException |InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        });
        senderThread.start();
    }

    private void sendMessage(Boolean connectionActive) throws IOException{
        System.out.println("Test1");
        Message m = new Message();
        m.setConnectionStillActive(connectionActive);
        m.SetMessage("Test");
        objectOutputStream.writeObject(m);
    }

    private void recieveMessage(){

       //objectInputStream = new ObjectInputStream(inputStream);
    }


    public static void main(String[] args) {
        Client client = new Client();

    }
}
