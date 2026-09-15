package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private static final String LOGIN_PREFIX = "LOGIN||";
    private static final MessageParser MESSAGE_PARSER = new MessageParser();

    private final Socket socket;
    private String username;
    private PrintWriter out;
    private final Set<String> joinedRooms = ConcurrentHashMap.newKeySet();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            this.out = new PrintWriter(socket.getOutputStream(), true);

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
                } else {
                    handleMessage(clientMessage, out);
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        } finally {
            if (username != null) {
                ClientRegistry.unregister(username);
                for (String roomName : joinedRooms) {
                    ChatRoomManager.removeUserFromRoom(roomName, username);
                }
            }
            ChatServer.activeConnections.decrementAndGet();
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Fejl ved lukning af socket: " + e.getMessage());
            }
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

    private void handleMessage(String clientMessage, PrintWriter out) {
        try {
            Message message = MESSAGE_PARSER.parseIncoming(clientMessage, username);
            switch (message.getMessageType()) {
                case PUBLIC:
                    handleRoomMessage(message, out);
                    break;
                case PRIVATE:
                    handlePrivateMessage(message, out);
                    break;
                case JOIN_ROOM:
                    handleJoinRoom(message, out);
                    break;
                case LEAVE_ROOM:
                    handleLeaveRoom(message, out);
                    break;
                case LIST_ROOMS:
                    handleListRooms(out);
                    break;
                default:
                    out.println("ERROR|Ugyldig beskedstype: " + message.getMessageType());
                    break;
            }
        } catch (IllegalArgumentException e) {
            out.println("ERROR|" + e.getMessage());
        }
    }

    private void handleJoinRoom(Message message, PrintWriter out) {
        String roomName = resolveRoomName(message);
        if (!ChatRoomManager.isValidRoom(roomName)) {
            out.println("ERROR|Ugyldigt rum. Tilgængelige rum: public 1, public 2, public 3");
            return;
        }

        ChatRoomManager.ensureDefaultRoomExists(roomName);
        ChatRoomManager.addUserToRoom(roomName, username);
        joinedRooms.add(roomName);

        List<String> history = ChatRoomManager.getRoomHistory(roomName);
        if (!history.isEmpty()) {
            out.println("ROOM_HISTORY|" + roomName + "|" + String.join("||", history));
        }
        out.println("ROOM_JOINED|" + roomName);
    }

    private void handleLeaveRoom(Message message, PrintWriter out) {
        String roomName = resolveRoomName(message);
        if (!ChatRoomManager.isValidRoom(roomName)) {
            out.println("ERROR|Ugyldigt rum. Tilgængelige rum: public 1, public 2, public 3");
            return;
        }

        ChatRoomManager.removeUserFromRoom(roomName, username);
        joinedRooms.remove(roomName);
        out.println("ROOM_LEFT|" + roomName);
    }

    private void handleListRooms(PrintWriter out) {
        List<String> rooms = ChatRoomManager.getRooms();
        out.println("ROOMS|" + String.join(",", rooms));
    }

    private void handleRoomMessage(Message message, PrintWriter out) {
        String roomName = resolveRoomName(message);
        if (!ChatRoomManager.isValidRoom(roomName)) {
            out.println("ERROR|Ugyldigt rum. Tilgængelige rum: public 1, public 2, public 3");
            return;
        }

        if (!joinedRooms.contains(roomName)) {
            out.println("ERROR|Du er ikke medlem af rummet " + roomName + ".");
            return;
        }

        String formattedMessage = MESSAGE_PARSER.formatMessage(message);
        ChatRoomManager.addMessageToHistory(roomName, formattedMessage);

        for (String user : ChatRoomManager.getMembers(roomName)) {
            ClientHandler client = ClientRegistry.get(user);
            if (client != null) {
                client.sendMessage(formattedMessage);
            }
        }
    }

    private void handlePrivateMessage(Message message, PrintWriter out) {
        String targetUser = message.getTarget();
        if (targetUser == null || targetUser.isBlank()) {
            out.println("ERROR|Private target mangler.");
            return;
        }

        ClientHandler targetClient = ClientRegistry.get(targetUser);
        if (targetClient == null) {
            out.println("ERROR|Brugeren " + targetUser + " findes ikke.");
            return;
        }

        String formattedMessage = MESSAGE_PARSER.formatMessage(message);
        targetClient.sendMessage(formattedMessage);
        sendMessage(formattedMessage);
    }

    private String resolveRoomName(Message message) {
        String roomName = message.getTarget();
        if (roomName == null || roomName.isBlank()) {
            roomName = message.getPayload();
        }
        return roomName == null ? "" : roomName.trim().toLowerCase(Locale.ROOT);
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
}
