package Core;


import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;

public class Main implements MSG
{
    public static ClientList clientList;
    public static ServerSocket serverSocket;
    public static Socket connectionSocket;
    public static Database db;

    public static void main(String[] args)
    {
        try
        {
            clientList = new ClientList();

            System.out.printf("Pre-Registration Server\n\n");
           
            
            String dbname = "PreRegSys";
            String dbuser = "root";
            String dbpass ="password";
           
            
            // Database Connection
              db = new Database(String.format("jdbc:mysql://localhost/%s?user=%s%s", dbname, dbuser, !dbpass.equals("") ? "&password=" + dbpass : ""));
            
            // Open socket
            int socketPort = 6769;
            
            System.out.printf("\nServer Port: %d\n" , socketPort);
            
            serverSocket = new ServerSocket(socketPort);
            
            System.out.printf("Socket connection start.\n");

        }

        catch (MySQLSyntaxErrorException mysqle)
        {
            System.out.printf("Database Connection Fail. Please check for your database connection setting.\n");
            System.exit(0);
        }
        catch (BindException be)
        {
            System.out.printf("Fail to open acceptor, please check for the port is free.\n");
            System.exit(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.printf("Unknown error while starting the server.\n");
            System.out.printf("Message: %s\n", e.getMessage());
            System.exit(0);
        }
        
        while(true)
        {
            try
            {

                connectionSocket = serverSocket.accept();
                
                ObjectInputStream in = new ObjectInputStream(connectionSocket.getInputStream());
                
                Packet packet = (Packet)in.readObject();

                if (packet.getMessage() == CMSG_LOGIN)
                {
                    System.out.printf("\nMessage: CMSG_LOGIN\n");
                    
                    String username = (String)packet.get();
                    String password = (String)packet.get();

                    ResultSet rs = db.query("Select id, username, email, password, title from Users where username='%s' and password='%s'", username, password);

                    if (rs.first())
                    {

                            Client c = new Client(Integer.parseInt(rs.getString(1)), rs.getString(2));
                            
                            c.setTitle(rs.getString(5));
                            c.createSession(connectionSocket, in, new ObjectOutputStream(connectionSocket.getOutputStream()));
                            
                            System.out.printf("%s (Id: %d) logged in.\n", c.getUsername(), c.getId());

                            System.out.printf("Send Message: SMSG_LOGIN_SUCCESS\n");
                            
                            Packet p = new Packet(SMSG_LOGIN_SUCCESS);
                            p.put(c.getId());
                            p.put(c.getUsername());
                            p.put(c.getTitle());

                            c.getSession().SendPacket(p);

                            clientList.add(c);

                    }
                    else
                    {
                       System.out.printf("Send Message: SMSG_LOGIN_FAILED\n");
                       ObjectOutputStream out = new ObjectOutputStream(connectionSocket.getOutputStream());
                       out.writeObject(new Packet(SMSG_LOGIN_FAILED));
                       out.close();
                    }
                    
                    rs = null;
                }

            }
            catch (Exception e)
            {	e.printStackTrace();
                
            }
        }
    }
}