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

    public PreRegSession(Socket socket) throws IOException {
        this.socket = socket;
        this.messenger = new Messenger(new PreRegMessageFactory(),
                new BufferedInputStream(socket.getInputStream()),
                new BufferedOutputStream(socket.getOutputStream()));
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

    private void processMessage(MessageWrapper messageWrapper) {
        if (messageWrapper.getMessageType().equals(PreRegMessageFactory.LOGIN_REQUEST)) {
            //TODO: Allow different methods of authentication
            DummyAuthenticator.getInstance().authenticate((PreRegProto.LoginRequestData) messageWrapper.getMessage());
        }
        else if (messageWrapper.getMessageType().equals(PreRegMessageFactory.GET_COURSES_REQUEST)) {

        }
    }
}
