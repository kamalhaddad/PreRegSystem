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
    final byte CMSG_TIME_REQUEST            = 0x0A;
    final byte CMSG_COURSE_REQUEST          = 0x0B;
    final byte CMSG_COURSE_SEARCH           = 0x0C;
    final byte CMSG_GET_SCHED_LIST          = 0x0D;
    final byte SMSG_SCHED_DETAIL            = 0x0E;
    final byte SMSG_ERROR_MESSAGE           = 0x0F;

    final Message[] messagetable =
    {
        new Message /* 0x00 */ ("UNKNOWN", null                              ),
        new Message /* 0x01 */ ("CMSG_LOGIN", null                              ),
        new Message /* 0x02 */ ("CMSG_LOGOUT", null                              ),
        new Message /* 0x03 */ ("SMSG_LOGIN_SUCCESS", null                              ),
        new Message /* 0x04 */ ("SMSG_LOGIN_FAILED", null                              ),
        new Message /* 0x05 */ ("SMSG_LOGOUT_COMPLETE", "HandleLogoutCompleteMessage"      ),
        new Message /* 0x06 */ ("CMSG_GET_COURSE_LIST", null),
        new Message /* 0x07 */ ("SMSG_COURSE_DETAIL", "HandleCourseDetailMessage"       ),
        new Message /* 0x08 */ ("CMSG_CAPACITY_REQUEST", null       ),
        new Message /* 0x09 */ ("SMSG_REFRESH_COURSE_LIST", "HandleRefreshCourseListMessage" ),
        new Message /* 0x0A */ ("CMSG_TIME_REQUEST", null       ),
        new Message /* 0x0B */ ("CMSG_COURSE_REQUEST", null       ),
        new Message /* 0x0C */ ("CMSG_COURSE_SEARCH", null       ),
        new Message /* 0x0D */ ("CMSG_GET_SCHED_LIST",null),
        new Message /* 0x0E */ ("SMSG_SCHED_DETAIL", "HandleSchedDetailMessage"),
        new Message /* 0x0F */ ("SMSG_ERROR_MESSAGE", "HandleErrorMessage")


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