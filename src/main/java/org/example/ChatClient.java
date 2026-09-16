package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        final String HOST = "localhost"; // Default host
        final int PORT = 5005; // Default port
        final String LOGIN_PROMPT = "Please login"; // Default login prompt

        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner consoleInput = new Scanner(System.in)) {

            ServerListener serverListener = new ServerListener(socket);
            Thread listenerThread = new Thread(serverListener, "server-listener");
            listenerThread.start();
            serverListener.waitForMessageMatching(message -> message.startsWith(LOGIN_PROMPT));

            while (true) {
                System.out.print("Choose username (or QUIT): ");
                String username = consoleInput.nextLine();
                if ("QUIT".equalsIgnoreCase(username)) {
                    out.println("QUIT");
                    break;
                }

                out.println("LOGIN||" + username);
                String response = serverListener.waitForMessageMatching(message ->
                        message.startsWith("LOGIN_OK|") || message.startsWith("ERROR|"));
                if (response.startsWith("LOGIN_OK|")) {
                    System.out.println("You are logged in as " + username);
                    break;
                }
                System.out.println("Please try a different username.");
            }

            while (consoleInput.hasNextLine()) {
                String message = consoleInput.nextLine();
                if ("QUIT".equalsIgnoreCase(message)) {
                    out.println("QUIT");
                    break;
                }
                out.println(message);
            }

            listenerThread.join(1000);
        } catch (IOException e) {
            System.out.println("client error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("The client was disconnected unexpectedly.");
        }
    }
}
