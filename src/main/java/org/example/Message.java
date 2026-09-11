package org.example;

public class Message{

    private String target;
    private String payload;
    private MessageType type;
    private String sender;


    public Message(MessageType type, String target, String payload, String sender) {
        this.target = target;
        this.payload = payload;
        this.type = type;
        this.sender = sender;
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

    public String getSender() {
        return sender;
    }


}
