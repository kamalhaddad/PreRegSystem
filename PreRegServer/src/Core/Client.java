package Core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
    private int id;
    private String username;
    private String title;

    private Socket socket;
    private Session session;

    
    public Client(int id, String username)
    {
        this.id = id;
        this.username = username;
    }
    
    public void createSession(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException
    {
        this.session = new Session(this, in, out);
        this.socket = socket;

        new Thread(session).start();
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }

    
    public int getId()
    {
        return this.id;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public String getTitle()
    {
        return this.title;
    }

    public Socket getSocket()
    {
        return this.socket;
    }
    
    public Session getSession()
    {
        return this.session;
    }
}