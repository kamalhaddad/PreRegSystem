package Core;

import UI.MainUI;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkManager implements MSG
{
    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public NetworkManager()
    {
    }
    
    public static void destroy()
    {
        try { socket.close(); }
        catch (Exception e) {}
        
        socket = null;
        in = null;
        out = null;
    }

    public static void login(String username, String password)
    {
        try
        {
            // Connect to the server
            socket = new Socket("127.0.0.1", 6769);
            out = new ObjectOutputStream(socket.getOutputStream());
            
            // 5 mins timeout
            socket.setSoTimeout(5 * 60 * 1000);
            
            // If connection is create successfully, send the login detail to the server.
            Packet loginPacket = new Packet(CMSG_LOGIN);
            loginPacket.put(username);
            loginPacket.put(password);

            SendPacket(loginPacket);
            
            // Create input stream.
            in = new ObjectInputStream(socket.getInputStream());
            
            Packet p = (Packet)in.readObject();
            
            switch(p.getMessage())
            {
                case SMSG_LOGIN_SUCCESS: /* Login is success */
                    int accountGuid = (Integer)p.get();
                    System.out.println(accountGuid);
                    String accountUsername = (String)p.get();
                    System.out.println(accountUsername);
                    String accountTitle = (String)p.get();
                    System.out.println(accountTitle);

                    if(accountTitle.equals("Student"))
                        MainUI.isProfessor = false;

                    else if (accountTitle.equals("Professor"))
                        MainUI.isProfessor = true;

                    new Thread(new NetworkThread()).start();
                    UICore.getMainUI().setUserInfo(accountGuid, accountUsername, accountTitle);
                    UICore.switchUI();
                    break;
                case SMSG_LOGIN_FAILED: /* Login failed */
                    NetworkManager.destroy();
                    
                    UICore.showMessageDialog("The information you entered is not valid.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    UICore.getMainUI().enableLoginInput(true);
                    
                    break;

                default: /* Server problem? */
                    UICore.showMessageDialog("Unknown error occur, please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                    UICore.getMainUI().enableLoginInput(true);
                    break;
            }
        }
        catch (IOException ioe)
        {
            UICore.showMessageDialog("Unable to connect to server. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            UICore.getMainUI().enableLoginInput(true);
        }
        catch (Exception e)
        {
            UICore.showMessageDialog("Unknown error occur, please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            UICore.getMainUI().enableLoginInput(true);
        }
    }
    
    public static void logout()
    {
        NetworkThread.stop();
        destroy();
        
        UICore.switchUI();

        UICore.getMainUI().setTitle("Login");
        UICore.getMainUI().enableLoginInput(true);
        User.clear();
    }


      public static void getCourseList()
      {
          SendPacket(new Packet(CMSG_GET_COURSE_LIST));
      }
    
    public static void SendPacket(Packet p)
    {
        try
        {
            out.writeObject(p);
            out.flush();
        }
        catch (Exception e){}
    }
    
    public static Packet ReceivePacket() throws Exception
    {
        return (Packet)in.readObject();
    }
}
