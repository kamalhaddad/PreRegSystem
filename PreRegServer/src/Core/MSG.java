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
        new Message /* 0x00 */ ("UNKNOWN", null                             ),
        new Message /* 0x01 */ ("CMSG_LOGIN", null                       ),
        new Message /* 0x02 */ ("CMSG_LOGOUT", "HandleLogoutMessage"             ),
        new Message /* 0x03 */ ("SMSG_LOGIN_SUCCESS", null                             ),
        new Message /* 0x04 */ ("SMSG_LOGIN_FAILED", null                             ),
        new Message /* 0x05 */ ("SMSG_LOGOUT_COMPLETE", null                             ),
        new Message /* 0x06 */ ("CMSG_GET_COURSE_LIST", "HandleGetCourseListMessage"     ),
        new Message /* 0x07 */ ("SMSG_COURSE_DETAIL", null                             ),
        new Message /* 0x08 */ ("CMSG_CAPACITY_REQUEST", "HandleCapacityRequestMessage"       ),
        new Message /* 0x09 */ ("SMSG_REFRESH_COURSE_LIST", null ),
        new Message /* 0x10 */ ("CMSG_TIME_REQUEST", "HandleTimeRequestMessage"       ),
        new Message /* 0x11 */ ("CMSG_COURSE_REQUEST", "HandleCourseRequestMessage"       )
    };
    
    final class Message
    {
        final String name;
        final String handler;
        
        public Message(String name, String Handler)
        {
            this.name = name;
            this.handler = Handler;
        }
    }
}