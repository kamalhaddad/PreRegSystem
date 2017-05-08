package com.messenger;


import com.google.protobuf.Message;
import com.messenger.protobuf.MessengerProto;

public class MessageWrapper {

    private MessengerProto.MessageHeader header;
    private Message message;

    private MessageWrapper(MessengerProto.MessageHeader header, Message message) {
        this.header = header;
        this.message = message;
    }

    protected MessageWrapper(MessengerProto.MessageType messageType, Message message) {
        this(MessengerProto.MessageHeader.newBuilder().setMessageType(messageType).build(), message);
    }

    protected MessageWrapper(int messageCode,
                          MessengerProto.MessageType messageType,
                          Message message) {
        this(MessengerProto.MessageHeader.newBuilder()
                .setMessageType(messageType).setCode(messageCode).build(), message);
    }

    public int getMessageCode() {
        return header.getCode();
    }

    public MessengerProto.MessageType getMessageType() {
        return header.getMessageType();
    }

    public Message getMessage() {
        return message;
    }

    protected MessengerProto.MessageHeader getHeader() {
        return header;
    }

    public void setMessage(Message message)
            throws IllegalArgumentException {
        if (!message.getDefaultInstanceForType().equals(this.message.getDefaultInstanceForType())) {
            throw new IllegalArgumentException("Message Argument doesn't cope with the required message content type.");
        }
        this.message = message;
    }

    public void setMessageCode(int code) {
        this.header = MessengerProto.MessageHeader.newBuilder(header).setCode(code).build();
    }

    public void setMessageType(MessengerProto.MessageType messageType) {
        this.header = MessengerProto.MessageHeader.newBuilder(header).setMessageType(messageType).build();
    }

}
