package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ServerListener implements Runnable {

    private final Socket socket;

    public ServerListener(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }
            System.out.println("Server connection closed.");
        } catch (IOException e) {
            if (!socket.isClosed()) {
                System.out.println("The connection with the server is closed: " + e.getMessage());
            }
        }
    }
}
