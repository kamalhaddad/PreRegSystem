package Core;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreRegServer extends Thread {
    private ServerSocket serverSocket;

    private PreRegMysqlDatabase database;

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
        Logger.getGlobal().log(Level.INFO,"Starting server on port "+port);
        serverSocket = new ServerSocket(port);
        try {
            database.init("localhost",3306,"PreReg","root","abdallah");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
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
