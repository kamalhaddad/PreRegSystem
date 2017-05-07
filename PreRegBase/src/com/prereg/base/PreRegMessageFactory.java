package com.prereg.base;

import com.google.protobuf.Message;
import com.messenger.MessageFactory;
import com.messenger.MessageWrapper;
import com.messenger.protobuf.MessengerProto;
import com.prereg.base.data.PreRegProto;

public class PreRegMessageFactory implements MessageFactory {

    public static final int UNKNOWN = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;
    public static final int SUCCESS = 3;

    public static final MessengerProto.MessageType EMPTY_MESSAGE =
            MessengerProto.MessageType.newBuilder().setType("EMPTY_MESSAGE").build();

    public static final MessengerProto.MessageType LOGIN_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("LOGIN_REQUEST").build();

    public static final MessengerProto.MessageType LOGIN_RESPONSE =
            MessengerProto.MessageType.newBuilder().setType("LOGIN_RESPONSE").build();

    public static final MessengerProto.MessageType COURSES_SEARCH =
            MessengerProto.MessageType.newBuilder().setType("COURSES_SEARCH").build();

    public static final MessengerProto.MessageType COURSES_SEARCH_REPLY =
            MessengerProto.MessageType.newBuilder().setType("OPEN_COURSE_REQUEST").build();

    public static final MessengerProto.MessageType CHANGE_TIME_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("OPEN_COURSE_REQUEST").build();

    public static final MessengerProto.MessageType OPEN_COURSE_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("OPEN_COURSE_REQUEST").build();

    public static final MessengerProto.MessageType GET_SCHEDULE_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("OPEN_COURSE_REQUEST").build();

    public static final MessengerProto.MessageType GET_SCHEDULE_RESPONSE =
            MessengerProto.MessageType.newBuilder().setType("OPEN_COURSE_REQUEST").build();

    public static final MessengerProto.MessageType ERROR_MESSAGE =
            MessengerProto.MessageType.newBuilder().setType("OPEN_COURSE_REQUEST").build();

    @Override
    public MessageWrapper createMessage(MessengerProto.MessageType messageType) {
        Message message;
        if (messageType.equals(EMPTY_MESSAGE)) {
            message = PreRegProto.EmptyMessage.getDefaultInstance();
        }
        else if (messageType.equals(LOGIN_REQUEST)) {
            message = PreRegProto.LoginRequestData.getDefaultInstance();
        }
        else if (messageType.equals(LOGIN_RESPONSE)) {
            message = PreRegProto.LoginResponseData.getDefaultInstance();
        }
        else if (messageType.equals(COURSES_SEARCH)) {
            message = PreRegProto.CourseData.getDefaultInstance();
        }
        else if(messageType.equals(OPEN_COURSE_REQUEST)) {
            message = PreRegProto.CourseList.getDefaultInstance();
        }
        else {
            return null;
        }
        return new MessageWrapper(messageType, message);
    }
}
