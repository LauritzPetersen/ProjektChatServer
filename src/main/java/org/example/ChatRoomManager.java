package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRoomManager {

    public static final List<String> DEFAULT_ROOMS = List.of("public 1", "public 2", "public 3");

    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<String>> ROOM_MEMBERS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<String>> ROOM_HISTORY = new ConcurrentHashMap<>();

    static {
        for (String roomName : DEFAULT_ROOMS) {
            ROOM_MEMBERS.put(roomName, new CopyOnWriteArrayList<>());
            ROOM_HISTORY.put(roomName, new CopyOnWriteArrayList<>());
        }
    }

    public static boolean isValidRoom(String roomName) {
        if (roomName == null) {
            return false;
        }
        String normalizedRoom = normalizeRoomName(roomName);
        return normalizedRoom != null && DEFAULT_ROOMS.contains(normalizedRoom);
    }

    public static void ensureDefaultRoomExists(String roomName) {
        String normalizedRoom = normalizeRoomName(roomName);
        if (!isValidRoom(normalizedRoom)) {
            throw new IllegalArgumentException("Ugyldigt rum. Tilgængelige rum: public 1, public 2, public 3");
        }

        ROOM_MEMBERS.computeIfAbsent(normalizedRoom, key -> new CopyOnWriteArrayList<>());
        ROOM_HISTORY.computeIfAbsent(normalizedRoom, key -> new CopyOnWriteArrayList<>());
    }

    public static boolean addUserToRoom(String roomName, String username) {
        String normalizedRoom = normalizeRoomName(roomName);
        ensureDefaultRoomExists(normalizedRoom);
        CopyOnWriteArrayList<String> members = ROOM_MEMBERS.get(normalizedRoom);
        return members.addIfAbsent(username);
    }

    public static boolean removeUserFromRoom(String roomName, String username) {
        if (roomName == null || username == null) {
            return false;
        }

        String normalizedRoom = normalizeRoomName(roomName);
        CopyOnWriteArrayList<String> members = ROOM_MEMBERS.get(normalizedRoom);
        if (members == null) {
            return false;
        }

        return members.remove(username);
    }

    public static List<String> getMembers(String roomName) {
        String normalizedRoom = normalizeRoomName(roomName);
        return new ArrayList<>(ROOM_MEMBERS.getOrDefault(normalizedRoom, new CopyOnWriteArrayList<>()));
    }

    public static List<String> getRoomHistory(String roomName) {
        String normalizedRoom = normalizeRoomName(roomName);
        return new ArrayList<>(ROOM_HISTORY.getOrDefault(normalizedRoom, new CopyOnWriteArrayList<>()));
    }

    public static void addMessageToHistory(String roomName, String formattedMessage) {
        String normalizedRoom = normalizeRoomName(roomName);
        ensureDefaultRoomExists(normalizedRoom);
        ROOM_HISTORY.get(normalizedRoom).add(formattedMessage);
    }

    public static List<String> getRooms() {
        return new ArrayList<>(DEFAULT_ROOMS);
    }

    private static String normalizeRoomName(String roomName) {
        if (roomName == null) {
            return null;
        }
        return roomName.trim().toLowerCase(Locale.ROOT);
    }
}
