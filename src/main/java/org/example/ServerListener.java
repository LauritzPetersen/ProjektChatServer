package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class ServerListener implements Runnable {

    private final Socket socket;
    private final BlockingQueue<String> controlMessages = new LinkedBlockingQueue<>();

    public ServerListener(Socket socket) {
        this.socket = socket;
    }

    public String waitForMessageMatching(Predicate<String> predicate) throws InterruptedException {
        while (true) {
            String message = controlMessages.take();
            if (predicate.test(message)) {
                return message;
            }
        }
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
                if (isControlMessage(serverMessage)) {
                    controlMessages.offer(serverMessage);
                }
            }
        } catch (IOException e) {
            if (!socket.isClosed()) {
                System.out.println("forbindelse til server blev afbrudt: " + e.getMessage());
            }
        }
    }

    private boolean isControlMessage(String serverMessage) {
        return serverMessage.startsWith("Please login")
                || serverMessage.startsWith("LOGIN_OK|")
                || serverMessage.startsWith("ERROR|");
    }
}
