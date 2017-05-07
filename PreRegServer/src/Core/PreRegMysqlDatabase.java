package Core;

import com.prereg.base.data.PreRegProto;



public class PreRegMysqlDatabase implements PreRegDatabase {

    @Override
    public void init() {

    }

    @Override
    public PreRegProto.UserList queryUser(PreRegProto.UserData userQuery) {
        return PreRegProto.UserList.newBuilder().addUser(PreRegProto.UserData.newBuilder().setName("test").build()).build();
    }

    @Override
    public PreRegProto.CourseList queryCourse(PreRegProto.CourseData courseQuery) {
        return PreRegProto.CourseList.newBuilder().addCourse(PreRegProto.CourseData.newBuilder().setCRN(123).build()).build();
    }
}
