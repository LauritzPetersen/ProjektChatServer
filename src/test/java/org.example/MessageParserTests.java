package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageParserTests {
    private MessageParser messageParser;

    @BeforeEach
    void setUp() {
        messageParser = new MessageParser();
    }

    @Test
    void checkParseIncoming_success(){
        String successfulMessage = "public|bob|Hello";
        Message message = messageParser.parseIncoming(successfulMessage, "bobbi");
        assertEquals(MessageType.PUBLIC, message.getMessageType());
        assertEquals("bob", message.getTarget());
        assertEquals("Hello", message.getPayload());
        assertEquals("bobbi", message.getSender());
    }

    @Test
    void checkParseIncoming_shouldThrowWhenNullOrBlank(){
        String nullMessage = null;
        String blankMessage = "   ";
        assertThrows(IllegalArgumentException.class, () -> messageParser.parseIncoming(nullMessage, "bobbi"));
        assertThrows(IllegalArgumentException.class, () -> messageParser.parseIncoming(blankMessage, "bobbi"));
    }

    @Test
    void checkParseIncoming_shouldThrowWhenInvalidFormat(){
        String noVerticalBarMessage = "publicbobHello";
        String onlyOneVerticalBar = "invalid|format";
        String tooManyVerticalBars = "invalid|format|with|too";
        assertThrows(IllegalArgumentException.class, () -> messageParser.parseIncoming(noVerticalBarMessage, "bobbi"));
        assertThrows(IllegalArgumentException.class, () -> messageParser.parseIncoming(onlyOneVerticalBar, "bobbi"));
        assertThrows(IllegalArgumentException.class, () -> messageParser.parseIncoming(tooManyVerticalBars, "bobbi"));
    }


    @Test
    void checkParseIncoming_shouldThrowWhenInvalidMessageType(){
        String invalidMessageType = "invalid|bob|Hello";
        //Exception is implicitly thrown when the message type can't be converted to an enum value, so we don't need to necessarily check for that explicitly.
        assertThrows(IllegalArgumentException.class, () -> messageParser.parseIncoming(invalidMessageType, "bobbi"));
    }



}
