package Core;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.ResultSet;


public class Session implements Runnable, MSG
{
    private Client c;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private volatile Thread session;
    

    public Session(Client c, ObjectInputStream in, ObjectOutputStream out)
    {
        this.c = c;
        this.in = in;
        this.out = out;
    }
    
    public void stop()
    {
        session = null;
    }
    
    public Packet ReceivePacket() throws Exception
    {
        return (Packet)in.readObject();
    }
    
    public void SendPacket(Packet p) throws Exception
    {
        out.writeObject(p);
        out.flush();
    }
    
    public void run()
    {
        this.session = Thread.currentThread();
        
        while(session == Thread.currentThread())
        {
            try
            {
                Packet p = ReceivePacket();
                
                if (p.getMessage() < 0x00 || p.getMessage() >= messagetable.length)
                {
                    System.out.printf("\nUnknown Message Receive: 0x%02X\n", p.getMessage());
                    continue;
                }
                
                Message message = messagetable[p.getMessage()];
                
                System.out.printf("\nMessage: %s\n", message.name);
                
                if (message.handler != null)
                {
                    Class[] types = new Class[] { Packet.class };
                    Object[] args = new Object[] { p };
                
                    this.getClass().getDeclaredMethod(message.handler, types).invoke(this, args);
                }
                else
                {
                    System.out.printf("Processing is not require for this packet.\n");
                    continue;
                }
            }
            catch (InvocationTargetException ite)
            {
                // Throw when an exception occur while processing packet.
                Throwable t = ite.getCause();
                
                if (t instanceof ClassCastException)
                    System.out.printf("Client %s (Id: %d) send a packet with wrong structure. (Attemp to crash server?)", c.getUsername(), c.getId());
                else
                    System.out.printf("Unhandler exception occur while processing packet data.\nException message: %s\n", ite.getCause());
            }
            
            catch (EOFException eof)
            {
                System.out.printf("\nClient %s (Id: %d) unexpected EOF while waiting for packet. (possible disconnected?)\n", c.getUsername(), c.getId());
                
                Logout();
            }
            catch (SocketException se)
            {
                System.out.printf("\nClient %s (Id: %d) connection was closed unexpectedly. (possible disconnected?)\n", c.getUsername(), c.getId());
                
                Logout();
            }
            catch (SocketTimeoutException ste)
            {
                // Client will send a Time Sync every 10 sec.
                // Every 30 sec, the server will request the client to send a ping acknowledgement.
                // If a client does not send any packet for 60 seconds, we consider that it is disconnected.
                System.out.printf("\nClient %s (Id: %d) is not respond for 60 seconds. (possible disconnected?)\n", c.getUsername(), c.getId());
                
                Logout();
            }
            catch (Exception e){e.printStackTrace();}
        }
        
        System.out.printf("Session thread of %s (Id: %d) stopped successfully.\n", c.getUsername(), c.getId());
    }

    void HandleGetCourseListMessage(Packet packet) throws Exception
    {


        ResultSet rs = Main.db.query("SELECT * FROM `Courses` t1 JOIN Classrooms t2 ON t2.Room = t1.ClassRoom");

        Packet p;

        while(rs.next())
        {
            int Id = rs.getInt(3);
            String username = rs.getString(4);
            String CourseName = rs.getString(5);
            int CRN = rs.getInt(1);
            int SectionNumber = rs.getInt(2);
            String Time = rs.getString(6);
            String ClassRoom = rs.getString(7);
            int capacity = rs.getInt(8);
            int maxCap = rs.getInt(12);

            Client target = Main.clientList.findClient(rs.getInt(3));


            p = new Packet(SMSG_COURSE_DETAIL);
            p.put(Id);
            p.put(username);
            p.put(CourseName);
            p.put(CRN);
            p.put(SectionNumber);
            p.put(Time);
            p.put(ClassRoom);
            p.put(capacity);
            p.put(maxCap);

            SendPacket(p);

            System.out.printf("Send Contact: %s to client %d\n", rs.getString(4), c.getId());

            Thread.sleep(10);
        }

        rs.close();

    }

    
    void HandleLogoutMessage(Packet packet) throws Exception
    {
        SendPacket(new Packet(SMSG_LOGOUT_COMPLETE));
        
        Logout();
    }


    void HandleTimeRequestMessage(Packet packet) throws Exception{

        int CRN = (Integer)packet.get();
        String times = (String)packet.get();
        int InstructorID;
        String Type = "\"Change Time\"";
        String TypeNoQuotes = "Change Time";
        String info ="\""+times+"\"";
        String id;

        ResultSet rs = Main.db.query("SELECT Capacity, InstructorID FROM Courses WHERE CRN = %d", CRN);

        if(rs.first()){
            InstructorID = rs.getInt(2);
            id = "\""+CRN  + TypeNoQuotes+ c.getId()+"\"";

            ResultSet rs2 = Main.db.query("SELECT * FROM Requests WHERE id = %s", id);
            if(!rs2.first()) {
                Main.db.execute("INSERT into Requests VALUES(%s, %d, %d,%s,%s) ", id, InstructorID, c.getId(), Type, info);

                //Main.db.execute("UPDATE Courses SET Capacity = %d WHERE CRN=%d", cap, CRN);
                //Packet p = new Packet(SMSG_REFRESH_COURSE_LIST);

                //SendPacket(p);
            }
            else{
                //TODO:...
            }

        }

    }

    void HandleCourseRequestMessage(Packet packet) throws Exception{

        String course= (String)packet.get();
        String instructorUserName = (String)packet.get();
        int InstructorID;
        String Type = "\"Course Req.\"";
        String TypeNoQuotes = "Course Req.";
        String info ="\""+course+"\"";
        String id;

        //ResultSet rs = Main.db.query("SELECT Capacity, InstructorID FROM Courses WHERE CRN = %d", CRN);

        //if(rs.first()){
            //int cap = rs.getInt(1);
            //InstructorID = rs.getInt(2);
            //cap++;
            id = "\""+course  + instructorUserName+ TypeNoQuotes+ c.getId()+"\"";

            ResultSet rs2 = Main.db.query("SELECT * FROM Requests WHERE id = %s", id);
            if(!rs2.first()) {
                //Main.db.execute("INSERT into Requests VALUES(%s, %d, %d,%s,%s) ", id, InstructorID, c.getId(), Type, info);

                //Main.db.execute("UPDATE Courses SET Capacity = %d WHERE CRN=%d", cap, CRN);
                //Packet p = new Packet(SMSG_REFRESH_COURSE_LIST);

                //SendPacket(p);
            }
            else{
                //TODO:...
            }

        //}

    }

        void HandleCapacityRequestMessage(Packet packet) throws Exception
        {
            int CRN = (Integer)packet.get();
            int InstructorID;
            String Type = "\"Capacity\"";
            String TypeNoQuotes = "Capacity";
            String info ="\"No info to be shown.\"";
            String id;

            ResultSet rs = Main.db.query("SELECT Capacity, InstructorID FROM Courses WHERE CRN = %d", CRN);

            if(rs.first()){
                int cap = rs.getInt(1);
                InstructorID = rs.getInt(2);
                cap++;
                id = "\""+CRN  + TypeNoQuotes+ c.getId()+"\"";

                ResultSet rs2 = Main.db.query("SELECT * FROM Requests WHERE id = %s", id);
                if(!rs2.first()) {
                    Main.db.execute("INSERT into Requests VALUES(%s, %d, %d,%s,%s) ", id, InstructorID, c.getId(), Type, info);

                    Main.db.execute("UPDATE Courses SET Capacity = %d WHERE CRN=%d", cap, CRN);
                    Packet p = new Packet(SMSG_REFRESH_COURSE_LIST);

                    SendPacket(p);
                }
                else{
                    //TODO:...
                }

            }

        }
    
    void Logout()
    {
        try
        {
            Main.clientList.remove(c);
            
            System.out.printf("Closing client socket %d.\n", c.getId());
            c.getSocket().close();
            
            Main.db.execute("UPDATE account SET online = 0 WHERE Id = %d", c.getId());
            System.out.printf("Stopping session thread of %s (Id: %d).\n", c.getUsername(), c.getId());
            
            stop();
        }
        catch (Exception e){}
    }

}
