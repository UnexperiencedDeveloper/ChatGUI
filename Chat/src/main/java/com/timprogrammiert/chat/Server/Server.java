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

    private final Boolean isServerRunning = false;
    private final ServerSocket serverSocket;
    private Socket clientSocket;
    private final List<ClientThread> clientThreadList;
    private ObjectInputStream  objectInputStream;
    private ObjectOutputStream objectOutputStream;

    // TODO -> Check if Sockets in Threads are still active
    // When Client is closed Thread for Client still remains active
    public Server() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            clientThreadList = new ArrayList<ClientThread>();
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

        // Add newly connected Thread for each Socket to List and start Thread
        while (running.get()){
            try {
                clientSocket = serverSocket.accept();
                ClientThread connectedClientThread = new ClientThread(clientSocket, this);
                connectedClientThread.StartClientThread();

                clientThreadList.add(connectedClientThread);
            }catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }

        }

    }

    /**
     * Sends the Message to every active Client Connection
     *
     */
    public void BroadcastMessage(Message message){
        for (ClientThread cThread: clientThreadList) {
            cThread.SendMessage(message);
        }
    }

    /**
     * Removes the STOPPED Thread form Thread List
     */
    public void RemoveCanceledConnection(ClientThread thread){
        clientThreadList.remove(thread);
        System.out.println(clientThreadList.toString());
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.StartServer();
    }


}
