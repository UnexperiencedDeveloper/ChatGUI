package com.timprogrammiert.chat.Server;

import com.timprogrammiert.chat.Message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * created by tmatz on 03.10.2023
 */
public class Server extends Thread{
    public final static int SERVER_PORT = 7777;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread server;

    private Boolean isServerRunning = false;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private List<ClientThread> clientThreadList;
    private ObjectInputStream  objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Server() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            clientThreadList = new ArrayList<ClientThread>();
            //clientSocket = serverSocket.accept();

            /*InputStream inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);

            OutputStream outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void StartServer(){
        server = new Thread(this);
        server.start();
    }

    public void StopServer(){
        running.set(false);
    }



    @Override
    public void run() {
        running.set(true);

        while (running.get()){
            try {
                clientSocket = serverSocket.accept();
                ClientThread connectedClientThread = new ClientThread(clientSocket, this);
                connectedClientThread.StartClientThread();

                clientThreadList.add(connectedClientThread);

                System.out.println();
            }catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }

        }

    }

    public void BroadcastMessage(Message message){
        for (ClientThread cThread: clientThreadList) {
            cThread.SendMessage(message);
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.StartServer();
    }


}
