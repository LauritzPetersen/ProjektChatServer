package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static final String LOGIN_PREFIX = "LOGIN||";

    private final Socket socket;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Welcome");
            out.println("Please login with LOGIN||<username>");

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                if (clientMessage.equalsIgnoreCase("QUIT")) {
                    System.out.println("Client disconnected");
                    break;
                }

                if (username == null) {
                    handleLogin(clientMessage, out);
                }else {
                    System.out.println("Client " + username + ": " + clientMessage);
                    out.println("Echo: " + clientMessage);
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        } finally {
            if (username != null) {
                ClientRegistry.unregister(username);
            }
            ChatServer.activeConnections.decrementAndGet();
        }
    }

    private void handleLogin(String clientMessage, PrintWriter out) {
        if (!clientMessage.startsWith(LOGIN_PREFIX)) {
            out.println("ERROR|Please log in with LOGIN||<username>");
            return;
        }

        String requestedUsername = clientMessage.substring(LOGIN_PREFIX.length()).trim();
        if (requestedUsername.isEmpty()) {
            out.println("ERROR|Username cannot be empty. Please choose a new name:");
            return;
        }

        if (ClientRegistry.isUsernameTaken(requestedUsername)) {
            out.println("ERROR|Username already taken. Please choose a new name:");
            return;
        }

        if (!ClientRegistry.register(requestedUsername, this)) {
            out.println("ERROR|Username already taken. Please choose a new name:");
            return;
        }

        this.username = requestedUsername;
        out.println("LOGIN_OK|" + requestedUsername);
        System.out.println("User logged in: " + requestedUsername);
    }
}
