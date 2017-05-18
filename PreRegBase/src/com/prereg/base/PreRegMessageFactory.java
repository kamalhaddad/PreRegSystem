package com.prereg.base;

import com.google.protobuf.Message;
import com.messenger.MessageFactory;
import com.messenger.MessageWrapper;
import com.messenger.protobuf.MessengerProto;
import com.prereg.base.data.PreRegProto;

public class PreRegMessageFactory extends MessageFactory {

    public static final int UNKNOWN = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;
    public static final int SUCCESS = 3;
    public static final int FAILURE = 4;

    public static final MessengerProto.MessageType EMPTY_MESSAGE =
            MessengerProto.MessageType.newBuilder().setType("EMPTY_MESSAGE").build();

    public static final MessengerProto.MessageType LOGIN_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("LOGIN_REQUEST").build();

    public static final MessengerProto.MessageType LOGIN_RESPONSE =
            MessengerProto.MessageType.newBuilder().setType("LOGIN_RESPONSE").build();

    public static final MessengerProto.MessageType COURSES_SEARCH =
            MessengerProto.MessageType.newBuilder().setType("COURSES_SEARCH").build();

    public static final MessengerProto.MessageType COURSES_SEARCH_REPLY =
            MessengerProto.MessageType.newBuilder().setType("COURSES_SEARCH_REPLY").build();

    public static final MessengerProto.MessageType CHANGE_TIME_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("CHANGE_TIME_REQUEST").build();

    public static final MessengerProto.MessageType OPEN_COURSE_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("OPEN_COURSE_REQUEST").build();

    public static final MessengerProto.MessageType GET_SCHEDULE_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("GET_SCHEDULE_REQUEST").build();

    public static final MessengerProto.MessageType GET_SCHEDULE_RESPONSE =
            MessengerProto.MessageType.newBuilder().setType("GET_SCHEDULE_RESPONSE").build();

    public static final MessengerProto.MessageType CAPACITY_REQUEST =
            MessengerProto.MessageType.newBuilder().setType("CAPACITY_REQUEST").build();

    public static final MessengerProto.MessageType REPLY_MESSAGE =
            MessengerProto.MessageType.newBuilder().setType("REPLY_MESSAGE").build();

    public static final MessengerProto.MessageType ADD_COURSE =
            MessengerProto.MessageType.newBuilder().setType("ADD_COURSE").build();

    public static final MessengerProto.MessageType UPDATE_COURSE =
            MessengerProto.MessageType.newBuilder().setType("UPDATE_COURSE").build();

    public static final MessengerProto.MessageType GET_REQUESTS =
            MessengerProto.MessageType.newBuilder().setType("GET_REQUESTS").build();

    public static final MessengerProto.MessageType GET_REQUESTS_RESPONSE =
            MessengerProto.MessageType.newBuilder().setType("GET_REQUESTS_RESPONSE").build();

    public static final MessengerProto.MessageType GET_AVAILABLE_CLASS_ROOMS =
            MessengerProto.MessageType.newBuilder().setType("GET_AVAILABLE_CLASS_ROOMS").build();

    public static final MessengerProto.MessageType GET_AVAILABLE_CLASS_ROOMS_RESPONSE =
            MessengerProto.MessageType.newBuilder().setType("GET_AVAILABLE_CLASS_ROOMS_RESPONSE").build();

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
        else if (messageType.equals(COURSES_SEARCH_REPLY)) {
            message = PreRegProto.CourseList.getDefaultInstance();
        }
        else if(messageType.equals(OPEN_COURSE_REQUEST)) {
            message = PreRegProto.CourseRequest.getDefaultInstance();
        }
        else if(messageType.equals(CHANGE_TIME_REQUEST)) {
            message = PreRegProto.CourseRequest.getDefaultInstance();
        }
        else if(messageType.equals(GET_SCHEDULE_REQUEST)) {
            message = PreRegProto.EmptyMessage.getDefaultInstance();
        }
        else if(messageType.equals(GET_SCHEDULE_RESPONSE)) {
            message = PreRegProto.CourseList.getDefaultInstance();
        }
        else if(messageType.equals(CAPACITY_REQUEST)) {
            message = PreRegProto.CourseRequest.getDefaultInstance();
        }
        else if(messageType.equals(REPLY_MESSAGE)) {
            message = PreRegProto.ReplyMessage.getDefaultInstance();
        }
        else if(messageType.equals(ADD_COURSE)) {
            message = PreRegProto.CourseData.getDefaultInstance();
        }
        else if(messageType.equals(UPDATE_COURSE)) {
            message = PreRegProto.CourseData.getDefaultInstance();
        }
        else if(messageType.equals(GET_REQUESTS)) {
            message = PreRegProto.CourseRequest.getDefaultInstance();
        }
        else if(messageType.equals(GET_REQUESTS_RESPONSE)) {
            message = PreRegProto.CourseRequestList.getDefaultInstance();
        }
        else if(messageType.equals(GET_AVAILABLE_CLASS_ROOMS)) {
            message = PreRegProto.CourseData.getDefaultInstance();
        }
        else if(messageType.equals(GET_AVAILABLE_CLASS_ROOMS_RESPONSE)) {
            message = PreRegProto.ClassRoomList.getDefaultInstance();
        }
        else {
            return null;
        }
        return this.createMessage(messageType, message);
    }
}
