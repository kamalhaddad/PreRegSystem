package com.messenger;

import com.google.protobuf.Message;
import com.messenger.protobuf.MessengerProto;

public abstract class MessageFactory {
    public abstract MessageWrapper createMessage(MessengerProto.MessageType messageType);

    protected final MessageWrapper createMessage(MessengerProto.MessageType messageType, Message message) {
        return new MessageWrapper(messageType, message);
    }
}
