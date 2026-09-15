package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MessageParser {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SEPARATOR = "|";

    public Message parseIncoming(String rawMessage, String sender) {
        if (rawMessage == null || rawMessage.isBlank()) {
            throw new IllegalArgumentException("Ugyldigt format. Brug TYPE|TARGET|PAYLOAD.");
        }

        String[] parts = rawMessage.split("\\|", 3);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Ugyldigt format. Brug TYPE|TARGET|PAYLOAD.");
        }

        String typeText = parts[0].trim();
        String target = parts[1].trim();
        String payload = parts[2].trim();

        if (typeText.isEmpty() || target.isEmpty() || payload.isEmpty()) {
            throw new IllegalArgumentException("Ugyldigt format. Brug TYPE|TARGET|PAYLOAD.");
        }

        MessageType type = resolveType(typeText);
        return new Message(type, target, payload, sender);
    }

    public String formatMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message kan ikke være null.");
        }

        return LocalDateTime.now().format(DTF)
                + SEPARATOR + message.getMessageType()
                + SEPARATOR + message.getSender()
                + SEPARATOR + message.getTarget()
                + SEPARATOR + message.getPayload();
    }

    public String parseMessage(Message message, String sender) {
        return formatMessage(new Message(message.getMessageType(), message.getTarget(), message.getPayload(), sender));
    }

    private MessageType resolveType(String typeText) {
        String normalized = typeText.trim().toUpperCase(Locale.ROOT);
        if ("ROOM".equals(normalized)) {
            return MessageType.PUBLIC;
        }
        try {
            return MessageType.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldigt format. Brug TYPE|TARGET|PAYLOAD.", e);
        }
    }
}
