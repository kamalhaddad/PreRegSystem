package com.messenger;


import com.messenger.protobuf.MessengerProto;

public interface MessageSubject {
    void subscribe(MessageObserver messageObserver, MessengerProto.MessageType messageType);
    void unsubscribe(MessageObserver messageObserver, MessengerProto.MessageType messageType);
}
