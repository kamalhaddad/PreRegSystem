package Core;

import com.messenger.MessageWrapper;
import com.messenger.Messenger;
import com.prereg.base.PreRegMessageFactory;
import com.prereg.base.data.PreRegProto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PreRegSession extends Thread {

    private Socket socket;
    private Messenger messenger;
    private PreRegDatabase database;
    private PreRegMessageFactory messageFactory;

    private boolean loggedIn;
    private PreRegProto.UserData user;

    public PreRegSession(Socket socket) throws IOException {
        this.socket = socket;
        this.messenger = new Messenger(new PreRegMessageFactory(),
                new BufferedInputStream(socket.getInputStream()),
                new BufferedOutputStream(socket.getOutputStream()));
        this.database = PreRegServer.getInstance().getDatabase();
        this.messageFactory = new PreRegMessageFactory();
        this.loggedIn = false;
    }

    public void run() {
        try {
            while (true) {
                MessageWrapper message = messenger.receiveMessage();
                processMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: needs refactoring. possibly defined by a new class
    private void processMessage(MessageWrapper messageWrapper) throws IOException {
        if (messageWrapper.getMessageType().equals(PreRegMessageFactory.LOGIN_REQUEST)) {
            //TODO: Allow different methods of authentication: google, facebook..etc
            PreRegProto.LoginRequestData loginRequestData = (PreRegProto.LoginRequestData) messageWrapper.getMessage();
            PreRegProto.LoginResponseData loginResponseData;
            int messageCode;
            if(DummyAuthenticator.getInstance().authenticate((PreRegProto.LoginRequestData)
                    messageWrapper.getMessage())) {
                messageCode = PreRegMessageFactory.LOGIN_SUCCESS;
                PreRegProto.UserData userData = PreRegProto.UserData.newBuilder().
                        setUsername(loginRequestData.getUsername()).build();
                PreRegProto.UserList userList = null;
                try {
                    userList = database.queryUsers(userData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loginResponseData = PreRegProto.LoginResponseData.newBuilder().setUserData(userList.getUser(0)).build();
                loggedIn = true;
                this.user = userList.getUser(0);
            } else {
                messageCode = PreRegMessageFactory.LOGIN_FAILURE;
                loginResponseData = PreRegProto.LoginResponseData.getDefaultInstance();
            }
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.LOGIN_RESPONSE);
            responseMessage.setMessageCode(messageCode);
            responseMessage.setMessage(loginResponseData);
            messenger.sendMessage(responseMessage);
        }
        else if (!loggedIn) {
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.REPLY_MESSAGE);
            PreRegProto.ReplyMessage replyMessage = PreRegProto.ReplyMessage.newBuilder().
                    setReplyMessage("Not Authenticated").build();
            responseMessage.setMessageCode(PreRegMessageFactory.FAILURE);
            responseMessage.setMessage(replyMessage);
            messenger.sendMessage(responseMessage);
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.COURSES_SEARCH)) {
            PreRegProto.CourseList courseList = null;
            try {
                 courseList = database.queryCourses((PreRegProto.CourseData) messageWrapper.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.COURSES_SEARCH_REPLY);
            responseMessage.setMessageCode(PreRegMessageFactory.SUCCESS);
            responseMessage.setMessage(courseList);
            messenger.sendMessage(responseMessage);
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.CHANGE_TIME_REQUEST)
                || messageWrapper.getMessageType().equals(PreRegMessageFactory.CAPACITY_REQUEST)
                || messageWrapper.getMessageType().equals(PreRegMessageFactory.OPEN_COURSE_REQUEST)){
            try {
                database.addCourseRequest((PreRegProto.CourseRequest) messageWrapper.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.REPLY_MESSAGE);
            PreRegProto.ReplyMessage replyMessage = PreRegProto.ReplyMessage.newBuilder().
                    setReplyMessage("The request was sent successfully!").build();
            responseMessage.setMessageCode(PreRegMessageFactory.SUCCESS);
            responseMessage.setMessage(replyMessage);
            messenger.sendMessage(responseMessage);
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.GET_SCHEDULE_REQUEST)) {
            PreRegProto.CourseList schedule = null;
            try {
                schedule = database.querySchedule(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.GET_SCHEDULE_RESPONSE);
            responseMessage.setMessageCode(PreRegMessageFactory.SUCCESS);
            responseMessage.setMessage(schedule);
            messenger.sendMessage(responseMessage);
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.ADD_COURSE)) {
            PreRegProto.CourseData courseData = (PreRegProto.CourseData) messageWrapper.getMessage();
            try {
                database.addCourse(courseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.REPLY_MESSAGE);
            PreRegProto.ReplyMessage replyMessage = PreRegProto.ReplyMessage.newBuilder().
                    setReplyMessage("The course was added successfully!").build();
            responseMessage.setMessageCode(PreRegMessageFactory.SUCCESS);
            responseMessage.setMessage(replyMessage);
            messenger.sendMessage(responseMessage);
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.UPDATE_COURSE)) {
            PreRegProto.CourseData courseData = (PreRegProto.CourseData) messageWrapper.getMessage();
            try {
                database.updateCourse(courseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.REPLY_MESSAGE);
            PreRegProto.ReplyMessage replyMessage = PreRegProto.ReplyMessage.newBuilder().
                    setReplyMessage("The course was updated successfully!").build();
            responseMessage.setMessageCode(PreRegMessageFactory.SUCCESS);
            responseMessage.setMessage(replyMessage);
            messenger.sendMessage(responseMessage);
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.GET_REQUESTS)) {
            PreRegProto.CourseRequestList requests = null;
            try {
                requests = database.queryCourseRequests(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.GET_REQUESTS_RESPONSE);
            responseMessage.setMessageCode(PreRegMessageFactory.SUCCESS);
            responseMessage.setMessage(requests);
            messenger.sendMessage(responseMessage);
        }

    }
}
