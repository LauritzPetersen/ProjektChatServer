package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {

    private static final int PORT = 5005;
    private static final int MAX_CLIENTS = 3;

    public static AtomicInteger activeConnections = new AtomicInteger(0);

    public static void main(String[] args){
        System.out.println("ChatServer is running on port " + PORT + " with a maximum of " + MAX_CLIENTS + " clients.");
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
                Socket socket = serverSocket.accept();
                if(activeConnections.get() >= MAX_CLIENTS){
                    System.out.println("afviser ny klient: maks antal klienter nået");
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Serveren er fuld. Prøv igen senere.");
                    socket.close();
                    continue;
                }
            activeConnections.incrementAndGet();
            System.out.println("Accepterer ny klient");
            pool.submit(new ClientHandler(socket));
            }
        } catch (IOException e){
            System.out.println("server fejl: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}



