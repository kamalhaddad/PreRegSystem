package Core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PreRegServer extends Thread {
    private ServerSocket serverSocket;
    private PreRegDatabase database;

    private static PreRegServer preRegServer;

    private PreRegServer() {
        database = new PreRegMysqlDatabase();
    }

    public static PreRegServer getInstance() {
        if (preRegServer == null) {
            preRegServer = new PreRegServer();
        }
        return preRegServer;
    }

    public void init(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        database.init();
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PreRegSession clientSession = new PreRegSession(clientSocket);
                clientSession.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PreRegDatabase getDatabase() {
        return database;
    }
}
