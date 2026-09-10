package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){

        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
            out.println("Welcome");
            out.println("Type 'QUIT' to exit");

            String clientMessage;
            while((clientMessage = in.readLine()) != null){
                if(clientMessage.equalsIgnoreCase("QUIT")){
                    System.out.println("Client disconnected");
                    break;
                }
                System.out.println("Client: " + clientMessage);
                out.println("Echo: " + clientMessage);
            }
        } catch (IOException ioe){
            System.out.println("Error: " + ioe.getMessage());
        } finally {
            ChatServer.activeConnections.decrementAndGet();
        }
    }
}
