package Core;

import com.prereg.base.data.PreRegProto;

import java.util.List;

public interface PreRegDatabase {

    int OPEN_COURSE = 0;
    int CAPACITY = 0;
    int CHANGE_TIME = 0;

    PreRegProto.UserList queryUser(PreRegProto.UserData userQuery) throws Exception;

    PreRegProto.CourseList queryCourse(PreRegProto.CourseData courseQuery) throws Exception;

    //TODO: needs refactoring (better design of requests)
    void addCourseRequest(int fromId, int type, PreRegProto.CourseData courseData) throws Exception;

    PreRegProto.CourseList querySchedule(int studentId) throws Exception;
}
