package com.messenger;


import com.google.protobuf.Message;
import com.messenger.protobuf.MessengerProto;

public class MessageWrapper {

    private MessengerProto.MessageHeader header;
    private Message message;

    public MessageWrapper(MessengerProto.MessageType messageType, Message message) {
        this.header = MessengerProto.MessageHeader.newBuilder().setMessageType(messageType).build();
        this.message = message;
    }

    public MessageWrapper(int messageCode,
                          MessengerProto.MessageType messageType,
                          Message message) {
        this.header = MessengerProto.MessageHeader.newBuilder()
                .setMessageType(messageType).setCode(messageCode).build();
        this.message = message;
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

    public MessengerProto.MessageHeader getHeader() {
        return header;
    }

    protected void setMessage(Message message) {
        this.message = message;
    }

}
