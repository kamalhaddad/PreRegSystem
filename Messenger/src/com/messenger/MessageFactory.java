package com.messenger;

import com.messenger.protobuf.MessengerProto;

public interface MessageFactory {
    MessageWrapper createMessage(MessengerProto.MessageType messageType);
}
