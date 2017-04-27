package Core;

public interface MSG
{
    final byte CMSG_LOGIN                   = 0x01;
    final byte CMSG_LOGOUT                  = 0x02;
    final byte SMSG_LOGIN_SUCCESS           = 0x03;
    final byte SMSG_LOGIN_FAILED            = 0x04;
    final byte SMSG_LOGOUT_COMPLETE         = 0x05;
    final byte CMSG_GET_COURSE_LIST         = 0x06;
    final byte SMSG_COURSE_DETAIL           = 0x07;
    final byte CMSG_CAPACITY_REQUEST        = 0x08;
    final byte SMSG_REFRESH_COURSE_LIST     = 0x09;
    final byte CMSG_TIME_REQUEST            = 0x10;
    final byte CMSG_COURSE_REQUEST          = 0x11;
    
    final Message[] messagetable =
    {
        new Message /* 0x00 */ ("UNKNOWN", null                              ),
        new Message /* 0x01 */ ("CMSG_LOGIN", null                              ),
        new Message /* 0x02 */ ("CMSG_LOGOUT", null                              ),
        new Message /* 0x03 */ ("SMSG_LOGIN_SUCCESS", null                              ),
        new Message /* 0x04 */ ("SMSG_LOGIN_FAILED", null                              ),
        new Message /* 0x18 */ ("SMSG_LOGOUT_COMPLETE", "HandleLogoutCompleteMessage"      ),
        new Message /* 0x2D */ ("CMSG_GET_COURSE_LIST", null),
        new Message /* 0x2E */ ("SMSG_COURSE_DETAIL", "HandleCourseDetailMessage"       ),
        new Message /* 0x31 */ ("CMSG_CAPACITY_REQUEST", null       ),
        new Message /* 0x32 */ ("SMSG_REFRESH_COURSE_LIST", "HandleRefreshCourseListMessage" ),
        new Message /* 0x33 */ ("CMSG_TIME_REQUEST", null       ),
        new Message /* 0x34 */ ("CMSG_COURSE_REQUEST", null       )

    };
    
    final class Message
    {
        final String name;
        //final SessionStatus sessionStatus;
        //final int length;
        final String handler;
        
        public Message(String name, String Handler)
        {
            this.name = name;
//            this.sessionStatus = status;
//            this.length = length;
            this.handler = Handler;
        }
    }
}