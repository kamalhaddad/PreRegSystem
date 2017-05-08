package Core;


import com.messenger.MessageObserver;
import com.messenger.MessageSubject;
import com.messenger.MessageWrapper;
import com.messenger.Messenger;
import com.messenger.protobuf.MessengerProto;
import com.prereg.base.PreRegMessageFactory;
import com.prereg.base.User;
import com.prereg.base.data.PreRegProto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


//TODO: Refactor this class to make it abstract and work for any application
public class PreRegClientSession extends Thread implements MessageSubject {

    private Socket socket;

    private Messenger messenger;
    private User user;

    private static PreRegClientSession session = null;

    private ReentrantLock observersLock;

    private Map<MessengerProto.MessageType, Vector<MessageObserver>> messageObservers;

    private Queue<MessageObserver> messageObserverQueue;

    private PreRegMessageFactory preRegMessageFactory;

    private PreRegClientSession() {
        observersLock = new ReentrantLock();
        messageObservers = new HashMap<>();
        messageObserverQueue = new LinkedList<>();
        preRegMessageFactory = new PreRegMessageFactory();
    }

    public static PreRegClientSession getSession() {
        if (session == null) {
            session = new PreRegClientSession();
        }
        return session;
    }

    public void init(String username, String password, MessageObserver loginObserver)
            throws IOException, IllegalStateException {
        socket = new Socket("127.0.0.1", 5050);
        messenger = new Messenger(new PreRegMessageFactory(),
                new BufferedInputStream(socket.getInputStream()),
                new BufferedOutputStream(socket.getOutputStream()));

        PreRegProto.LoginRequestData loginData = PreRegProto.LoginRequestData.newBuilder().setUsername(username)
                .setPassword(password).build();

        MessageWrapper loginMessage = preRegMessageFactory.createMessage(PreRegMessageFactory.LOGIN_REQUEST);

        loginMessage.setMessage(loginData);

        messenger.sendMessage(loginMessage);

        MessageWrapper loginResponse = messenger.receiveMessage();
        PreRegProto.UserData userData = ((PreRegProto.LoginResponseData) loginResponse.getMessage()).getUserData();
        user = new User(userData);

        loginObserver.notify(loginResponse);
    }

    public void run() {
        try {
            while (true) {
                MessageWrapper message = messenger.receiveMessage();
                //processMessage(message);
                //TODO: add support for notification messages
                observersLock.lock();
                MessageObserver messageObserver = messageObserverQueue.poll();
                observersLock.unlock();
                messageObserver.notify(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(MessageObserver messageObserver, MessengerProto.MessageType messageType) {
        observersLock.lock();
        if(messageObservers.containsKey(messageType)) {
            messageObservers.put(messageType, new Vector<>());
        }
        messageObservers.get(messageType).add(messageObserver);
        observersLock.unlock();
    }

    @Override
    public void unsubscribe(MessageObserver messageObserver, MessengerProto.MessageType messageType) {
        observersLock.lock();
        if(messageObservers.containsKey(messageType)) {
            messageObservers.put(messageType, new Vector<>());
        }
        messageObservers.get(messageType).remove(messageObserver);
        observersLock.unlock();
    }

    public void queueMessage(MessageWrapper messageWrapper, MessageObserver messageObserver)
            throws IOException {
        observersLock.lock();
        messageObserverQueue.offer(messageObserver);
        messenger.sendMessage(messageWrapper);
        observersLock.unlock();
    }

    private void processMessage(MessageWrapper messageWrapper) {
        observersLock.lock();
        //...
        observersLock.unlock();
    }

    public User getUser() {
        return user;
    }
}
