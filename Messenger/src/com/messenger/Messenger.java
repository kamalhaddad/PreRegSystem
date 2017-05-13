package com.messenger;


import com.messenger.protobuf.MessengerProto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        messageWrapper.getHeader().writeDelimitedTo(sendStream);
        messageWrapper.getMessage().writeDelimitedTo(sendStream);
        sendStream.flush();
    }

    public MessageWrapper receiveMessage()
            throws IOException, IllegalStateException {
        MessengerProto.MessageHeader header = MessengerProto.MessageHeader.parseDelimitedFrom(receiveStream);
        MessengerProto.MessageType messageType = header.getMessageType();
        MessageWrapper message = messageFactory.createMessage(messageType);
        if (message == null) {
            throw new IllegalStateException("unknown message");
        }
        message.setMessageCode(header.getCode());
        message.setMessage(message.getMessage().getParserForType().parseDelimitedFrom(receiveStream));
        return message;
    }

}
