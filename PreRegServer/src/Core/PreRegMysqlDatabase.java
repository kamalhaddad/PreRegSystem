package Core;

import com.prereg.base.data.PreRegProto;


public class PreRegMysqlDatabase implements PreRegDatabase {

    @Override
    public void init() {

    }

    @Override
    public PreRegProto.UserData queryUser(PreRegProto.UserData userQuery) {
        return null;
    }

    @Override
    public PreRegProto.CourseData queryCourse(PreRegProto.CourseData courseQuery) {
        return null;
    }
}
