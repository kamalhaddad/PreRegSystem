package com.messenger;


import com.google.protobuf.Message;
import com.messenger.protobuf.MessengerProto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messenger {
    private MessageFactory messageFactory;

    private InputStream receiveStream;
    private OutputStream sendStream;

    public Messenger(MessageFactory messageFactory, InputStream receiveStream, OutputStream sendStream) {
        this.receiveStream = receiveStream;
        this.sendStream = sendStream;
        this.messageFactory = messageFactory;
    }

    public void sendMessage(MessageWrapper messageWrapper)
            throws IOException {
        messageWrapper.getHeader().writeTo(sendStream);
        messageWrapper.getMessage().writeTo(sendStream);
    }

    public MessageWrapper receiveMessage()
            throws IOException, IllegalStateException {
        MessengerProto.MessageHeader header = MessengerProto.MessageHeader.parseFrom(receiveStream);
        MessengerProto.MessageType messageType = header.getMessageType();
        MessageWrapper message = messageFactory.createMessage(messageType);
        if (message == null) {
            throw new IllegalStateException("unknown message");
        }
        message.setMessage(message.getMessage().getParserForType().parseFrom(receiveStream));
        return message;
    }

}
