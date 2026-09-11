package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientRegistry {

    private static final ConcurrentMap<String, ClientHandler> ACTIVE_USERS = new ConcurrentHashMap<>();

    public static boolean isUsernameTaken(String username) {
        return ACTIVE_USERS.containsKey(username);
    }

    public static boolean register(String username, ClientHandler clientHandler) {
        return ACTIVE_USERS.putIfAbsent(username, clientHandler) == null;
    }

    public static void unregister(String username) {
        ACTIVE_USERS.remove(username);
    }

    public static void unregister(ClientHandler clientHandler) {
        ACTIVE_USERS.entrySet().removeIf(entry -> entry.getValue() == clientHandler);
    }

    public static int size() {
        return ACTIVE_USERS.size();
    }
}
