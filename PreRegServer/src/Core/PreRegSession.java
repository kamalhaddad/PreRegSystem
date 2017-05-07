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

    public PreRegSession(Socket socket) throws IOException {
        this.socket = socket;
        this.messenger = new Messenger(new PreRegMessageFactory(),
                new BufferedInputStream(socket.getInputStream()),
                new BufferedOutputStream(socket.getOutputStream()));
        this.database = PreRegServer.getInstance().getDatabase();
        this.messageFactory = new PreRegMessageFactory();
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
                PreRegProto.UserList userList = database.queryUser(userData);
                loginResponseData = PreRegProto.LoginResponseData.newBuilder().setUserData(userList.getUser(0)).build();
            } else {
                messageCode = PreRegMessageFactory.LOGIN_FAILURE;
                loginResponseData = PreRegProto.LoginResponseData.getDefaultInstance();
            }
            MessageWrapper responseMessage = new MessageWrapper(messageCode,PreRegMessageFactory.LOGIN_RESPONSE,loginResponseData);
            messenger.sendMessage(responseMessage);
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.COURSES_SEARCH)) {
            PreRegProto.CourseList courseList =
                    database.queryCourse((PreRegProto.CourseData) messageWrapper.getMessage());
            MessageWrapper responseMessage = messageFactory.createMessage(PreRegMessageFactory.OPEN_COURSE_REQUEST);
            responseMessage.setMessageCode(PreRegMessageFactory.SUCCESS);
            responseMessage.setMessage(courseList);
            messenger.sendMessage(responseMessage);
        }
    }
}
