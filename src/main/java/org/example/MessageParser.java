package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MessageParser {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String SEPARATOR = "|";

    public String parseMessage(Message message, String sender){

        return LocalDateTime.now().format(dtf) + SEPARATOR + message.getMessageType()
        + SEPARATOR + sender + SEPARATOR + message.getTarget() + SEPARATOR + message.getPayload();
    }
}
