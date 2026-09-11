package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5005;
    private static final String TEST_MESSAGE = "Hej Server";

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            waitForServerBanner(in);
            waitForServerBanner(in);

            out.println(TEST_MESSAGE);
            waitForServerBanner(in);
            out.println("QUIT");
        } catch (IOException e) {
            System.out.println("client fejl: " + e.getMessage());
        }
    }

    private static void waitForServerBanner(BufferedReader in) throws IOException {
        if (in.readLine() == null) {
            throw new IOException("serveren lukkede forbindelsen uventet");
        }
    }
}
