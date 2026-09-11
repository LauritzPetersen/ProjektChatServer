package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5005;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(HOST, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner consoleInput = new Scanner(System.in)
        ) {
            Thread serverListenerThread = new Thread(new ServerListener(socket), "server-listener");
            serverListenerThread.start();

            System.out.println("Forbundet til serveren. Skriv en besked eller QUIT for at afslutte.");

            while (consoleInput.hasNextLine()) {
                String message = consoleInput.nextLine();
                out.println(message);

                if ("QUIT".equalsIgnoreCase(message)) {
                    break;
                }
            }
            /**
             * Wait for the server listener thread to finish before exiting the program.
             * This ensures that all messages from the server are printed before the client exits.
             */
            serverListenerThread.join(1000);
        } catch (IOException e) {
            System.out.println("client fejl: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("client blev afbrudt");
        }
    }
}
