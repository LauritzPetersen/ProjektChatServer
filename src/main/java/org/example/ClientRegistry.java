package org.example;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientRegistry {

    private static final ConcurrentMap<String, ClientHandler> ACTIVE_USERS = new ConcurrentHashMap<>();

    public static boolean isUsernameTaken(String username) {
        for (String activeUsername : ACTIVE_USERS.keySet()) {
            if (activeUsername.equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean register(String username, ClientHandler clientHandler) {
        return ACTIVE_USERS.putIfAbsent(username, clientHandler) == null;
    }

    public static ClientHandler getClientHandler(String username) {
        return ACTIVE_USERS.get(username);
    }

    public static void unregister(String username) {
        ACTIVE_USERS.remove(username);
    }

}
