package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {



    public static AtomicInteger activeConnections = new AtomicInteger(0);

    public static void main(String[] args){

        final int PORT = 5005;
        final int MAX_CLIENTS = 3;

        System.out.println("ChatServer is running on port " + PORT + " with a maximum of " + MAX_CLIENTS + " clients.");
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
                Socket socket = serverSocket.accept();
                if(activeConnections.get() >= MAX_CLIENTS){
                    System.out.println("Rejects new client: Max amount of clients reached");
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("The server is full. Please try again later.");
                    socket.close();
                    continue;
                }
            activeConnections.incrementAndGet();
            System.out.println("Accepts a new client");
            pool.submit(new ClientHandler(socket));
            }
        } catch (IOException e){
            System.out.println("server error: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}



