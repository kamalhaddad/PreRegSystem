package Core;

import com.prereg.base.data.PreRegProto;

import java.util.List;

public interface PreRegDatabase {

    void init();

    PreRegProto.UserList queryUser(PreRegProto.UserData userQuery);

    PreRegProto.CourseList queryCourse(PreRegProto.CourseData courseQuery);
}
