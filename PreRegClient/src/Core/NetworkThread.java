package Core;

import javax.swing.*;
import java.io.EOFException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import static Core.NetworkManager.SendPacket;

public class NetworkThread implements Runnable, MSG
{
    private static ArrayList<Packet> PacketStorage;
    private static volatile Thread thread;
    
    public static void stop()
    {
        thread = null;
    }
    
    public void run()
    {
        thread = Thread.currentThread();
        
        //sessionStatus = SessionStatus.LOGGEDIN;
        
        PacketStorage = new ArrayList<Packet>();
        
        // The server will first send SMSG_COURSE_DETAIL signal to inform client that this is a client detail data.
       NetworkManager.getCourseList();

        Packet p;
        
        while(thread == Thread.currentThread())
        {
            try
            {
                p = NetworkManager.ReceivePacket();
                
                if (p.getMessage() < 0x00 || p.getMessage() >= messagetable.length)
                    continue;
                
                Message message = messagetable[p.getMessage()];
                
                ProcessPacket(p);
            }
            catch (EOFException eof)
            {
                NetworkManager.logout();

                UICore.showMessageDialog("You have been disconnected from the server.", "Disconnected", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SocketException se)
            {
                NetworkManager.logout();

                UICore.showMessageDialog("You have been disconnected from the server.", "Disconnected", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SocketTimeoutException ste)
            {
                NetworkManager.logout();
                UICore.showMessageDialog("You have been disconnected from the server.", "Disconnected", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e) {}
        }
    }

    void ProcessQueuePacket() throws Exception
    {
        for (ListIterator<Packet> packet = PacketStorage.listIterator(); packet.hasNext(); )
        {
            Packet p = packet.next();
            
            Message message = messagetable[p.getMessage()];
            
            //if (!IsMessageCanProcessNow(message))
               // continue;
            
            ProcessPacket(p);
            packet.remove();
        }
    }
    
    void ProcessPacket(Packet p) throws Exception
    {
        Message message = messagetable[p.getMessage()];
        
        if (message.handler != null)
        {
            Class[] types = new Class[] { Packet.class };
            Object[] args = new Object[] { p };
        
            this.getClass().getDeclaredMethod(message.handler, types).invoke(this, args);
        }
    }

    void HandleCourseDetailMessage(Packet packet)
    {
        int guid = (Integer)packet.get();
        String c_username = (String)packet.get();
        String c_coursename = (String)packet.get();
        int c_crn = (Integer)packet.get();
        int c_secionnumber = (Integer)packet.get();
        String c_time = (String) packet.get();
        String c_classroom = (String) packet.get();
        int c_capacity = (Integer) packet.get();
        int maxCap = (Integer) packet.get();


        Course c = new Course(c_crn, c_secionnumber, c_username, c_coursename, c_time, c_classroom, c_capacity, maxCap);

        UICore.getMainUI().addCourse(c);
    }

    void HandleRefreshCourseListMessage(Packet packet){
        UICore.getMainUI().model.clear();

        Packet p = new Packet(CMSG_GET_COURSE_LIST);
        NetworkManager.SendPacket(p);
    }


    void HandleLogoutCompleteMessage(Packet packet)
    {
        NetworkManager.logout();
    }

}
