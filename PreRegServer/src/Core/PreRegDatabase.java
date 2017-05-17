package Core;

import com.prereg.base.data.PreRegProto;

import java.util.List;

public interface PreRegDatabase {

    PreRegProto.UserList queryUsers(PreRegProto.UserData userQuery) throws Exception;

    PreRegProto.CourseList queryCourses(PreRegProto.CourseData courseQuery) throws Exception;

    void addCourse(PreRegProto.CourseData course) throws Exception;
    void updateCourse(PreRegProto.CourseData course) throws Exception;

    //TODO: needs refactoring (better design of requests)
    void addCourseRequest(PreRegProto.CourseRequest courseRequest) throws Exception;
    PreRegProto.CourseRequestList queryCourseRequests(int instructorId) throws Exception;

    PreRegProto.CourseList querySchedule(int studentId) throws Exception;

    PreRegProto.ClassRoomList queryAvailableClassRooms(PreRegProto.CourseData course) throws Exception;

    class PreRegDatabaseException extends Exception {

    }
}
