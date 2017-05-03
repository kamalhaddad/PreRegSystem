package Core;

import com.prereg.base.data.PreRegProto;

public interface PreRegDatabase {

    void init();

    PreRegProto.UserData queryUser(PreRegProto.UserData userQuery);

    PreRegProto.CourseData queryCourse(PreRegProto.CourseData courseQuery);
}
