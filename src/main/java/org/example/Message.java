package org.example;

public class Message{

    private String target;
    private String payload;
    private MessageType type;


    public Message(String target, String payload, MessageType type) {
        this.target = target;
        this.payload = payload;
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public String getPayload() {
        return payload;
    }

    public MessageType getMessageType(){
        return type;
    }


}
