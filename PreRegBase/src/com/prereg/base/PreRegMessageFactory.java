package com.prereg.base;

import com.google.protobuf.Message;
import com.messenger.MessageFactory;
import com.messenger.MessageWrapper;
import com.messenger.protobuf.MessengerProto;
import com.prereg.base.data.PreRegProto;

import java.util.Objects;

public class PreRegMessageFactory implements MessageFactory {

    public static final int UNKNOWN = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 1;

    public static final MessengerProto.MessageType LOGIN_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("LOGIN_REQUEST").build();

    public static final MessengerProto.MessageType LOGIN_RESPONSE =
            MessengerProto.MessageType.newBuilder().setType("LOGIN_RESPONSE").build();

    public static final MessengerProto.MessageType GET_COURSES_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("GET_COURSES_REQUEST").build();

    @Override
    public MessageWrapper createMessage(MessengerProto.MessageType messageType) {
        Message message;
        if (messageType.equals(LOGIN_REQUEST)) {
            message = PreRegProto.LoginRequestData.getDefaultInstance();
        }
        else if (messageType.equals(LOGIN_RESPONSE)) {
            message = PreRegProto.LoginResponseData.getDefaultInstance();
        }
        else if (messageType.equals(GET_COURSES_REQUEST)) {
            message = PreRegProto.GetCoursesRequestData.getDefaultInstance();
        }
        else {
            return null;
        }

        return new MessageWrapper(messageType, message);
    }
}
