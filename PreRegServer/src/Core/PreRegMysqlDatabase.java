package Core;

import com.prereg.base.PreRegMessageFactory;
import com.prereg.base.data.PreRegProto;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: handling errors
public class PreRegMysqlDatabase implements PreRegDatabase {

    private Connection databaseConnection;


    public void init(String host, int port, String databaseName, String username, String password)
            throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Logger.getGlobal().log(Level.INFO,"Connecting to database...");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://" + host + ":" + port + "/"
                + databaseName + "?" + "user=" + username + "&password=" + password;
        databaseConnection = DriverManager.getConnection(url);
    }

    @Override
    public PreRegProto.UserList queryUsers(PreRegProto.UserData userQuery) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        String query = "SELECT * FROM USERS";
        String queryConditions = "WHERE";
        if (userQuery.hasId()) {
            queryConditions += " ID=" + userQuery.getId();
        }
        if (userQuery.hasName()) {
            queryConditions += " NAME=\"" + userQuery.getName() + "\"";
        }
        if (userQuery.hasUsername()) {
            queryConditions += " USERNAME=\"" + userQuery.getUsername() + "\"";
        }
        if (userQuery.hasEmail()) {
            queryConditions += " EMAIL=\"" + userQuery.getEmail() + "\"";
        }
        if (!queryConditions.equals("WHERE")) {
            query += " " +  queryConditions;
        }
        ResultSet resultSet = statement.executeQuery(query);
        PreRegProto.UserList.Builder userListBuilder = PreRegProto.UserList.newBuilder();
        while (resultSet.next()) {
            int ind = 1;
            int id = resultSet.getInt(ind++);
            String userName = resultSet.getString(ind++);
            String email = resultSet.getString(ind++);
            String title = resultSet.getString(ind++);
            String name = resultSet.getString(ind++);
            int creditsUsed = resultSet.getInt(ind++);
            int maxCredits = resultSet.getInt(ind++);

            PreRegProto.UserData userData = PreRegProto.UserData.newBuilder()
                    .setId(id)
                    .setUsername(userName)
                    .setEmail(email)
                    .setAccess(title.equals("professor") ? PreRegProto.UserData.Access.PROFESSOR: PreRegProto.UserData.Access.STUDENT)
                    .setName(name)
                    .build();

            userListBuilder.addUser(userData);
        }

        return userListBuilder.build();
    }

    @Override
    public PreRegProto.CourseList queryCourses(PreRegProto.CourseData courseQuery) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        String query = "SELECT COURSES.CRN, COURSES.SECTIONNUMBER, COURSES.COURSENAME, " +
                "COURSES.TIME, COURSES.CAPACITY, COURSES.DEPARTMENT, COURSES.CREDITS, " +
                "USERS.ID, USERS.USERNAME, USERS.EMAIL, USERS.TITLE, USERS.NAME," +
                "CLASSROOMS.ID, CLASSROOMS.BUILDING, CLASSROOMS.ROOMNUMBER, CLASSROOMS.MAXCAPACITY " +
                "FROM COURSES " +
                "LEFT JOIN CLASSROOMS ON COURSES.CLASSROOM = CLASSROOMS.ID " +
                "LEFT JOIN USERS ON USERS.ID = COURSES.INSTRUCTORID";
        String queryConditions = "WHERE";
        if (courseQuery.hasCRN()) {
            queryConditions += " COURSES.CRN=" + courseQuery.getCRN() + " ";
        }
        if (courseQuery.hasCourseName()) {
            queryConditions += " COURSES.COURSENAME=\"" + courseQuery.getCourseName() + "\" ";
        }
        if (courseQuery.hasInstructor()) {
            queryConditions += " USERS.USERNAME=\"" + courseQuery.getInstructor().getUsername() + "\" ";
        }
        if (!queryConditions.equals("WHERE")) {
            query += " " + queryConditions;
        }
        ResultSet resultSet = statement.executeQuery(query);
        PreRegProto.CourseList.Builder courseListBuilder = PreRegProto.CourseList.newBuilder();
        while (resultSet.next()) {
            int ind = 1;
            int courseCRN = resultSet.getInt(ind++);
            int sectionNumber = resultSet.getInt(ind++);
            String courseName = resultSet.getString(ind++);
            String time = resultSet.getString(ind++);
            int capacity = resultSet.getInt(ind++);
            String deparment = resultSet.getString(ind++);
            int credits = resultSet.getInt(ind++);
            int instructorId = resultSet.getInt(ind++);
            String userName = resultSet.getString(ind++);
            String email = resultSet.getString(ind++);
            String title = resultSet.getString(ind++);
            String name = resultSet.getString(ind++);
            int classRoomId = resultSet.getInt(ind++);
            String building = resultSet.getString(ind++);
            String roomNumber = resultSet.getString(ind++);
            int maxCapacity = resultSet.getInt(ind++);

            PreRegProto.CourseData.Builder courseDataBuilder = PreRegProto.CourseData.newBuilder();

            courseDataBuilder
                    .setCRN(courseCRN)
                    .setSectionNumber(sectionNumber)
                    .setCourseName(courseName)
                    .setTime(time)
                    .setCapacity(capacity)
                    .setCredits(credits)
                    .setInstructor(PreRegProto.UserData.newBuilder()
                            .setId(instructorId)
                            .setUsername(userName == null ? "TBA":userName)
                            .setEmail(email == null ? "TBA":email)
                            .setAccess(PreRegProto.UserData.Access.PROFESSOR)
                            .setName(name == null ? "TBA":name)
                            .build())
                    .setClassRoom(PreRegProto.ClassRoomData.newBuilder()
                            .setId(classRoomId)
                            .setBuilding(building == null ? "TBA":building)
                            .setRoomNumber(roomNumber == null ? "TBA":roomNumber)
                            .setMaxCapacity(maxCapacity)
                            .build());

            PreRegProto.CourseData course = courseDataBuilder.build();

            courseListBuilder.addCourse(course);
        }
        return courseListBuilder.build();
    }

    @Override
    public PreRegProto.CourseList querySchedule(int studentId) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        String query = "SELECT COURSES.CRN, COURSES.SECTIONNUMBER, COURSES.COURSENAME, " +
                "COURSES.TIME, COURSES.CAPACITY, COURSES.DEPARTMENT, COURSES.CREDITS, " +
                "USERS.ID, USERS.USERNAME, USERS.EMAIL, USERS.TITLE, USERS.NAME," +
                "CLASSROOMS.ID, CLASSROOMS.BUILDING, CLASSROOMS.ROOMNUMBER, CLASSROOMS.MAXCAPACITY " +
                "FROM REGISTERED_COURSES " +
                "INNER JOIN COURSES ON REGISTERED_COURSES.CRN=COURSES.CRN " +
                "LEFT JOIN CLASSROOMS ON COURSES.CLASSROOM=CLASSROOMS.ID " +
                "LEFT JOIN USERS ON USERS.ID=COURSES.INSTRUCTORID " +
                "WHERE REGISTERED_COURSES.STUDENTID=" + studentId;

        ResultSet resultSet = statement.executeQuery(query);

        PreRegProto.CourseList.Builder scheduleBuilder = PreRegProto.CourseList.newBuilder();

        while (resultSet.next()) {
            int ind = 1;
            int courseCRN = resultSet.getInt(ind++);
            int sectionNumber = resultSet.getInt(ind++);
            String courseName = resultSet.getString(ind++);
            String time = resultSet.getString(ind++);
            int capacity = resultSet.getInt(ind++);
            String deparment = resultSet.getString(ind++);
            int credits = resultSet.getInt(ind++);
            int instructorId = resultSet.getInt(ind++);
            String userName = resultSet.getString(ind++);
            String email = resultSet.getString(ind++);
            String title = resultSet.getString(ind++);
            String name = resultSet.getString(ind++);
            int classRoomId = resultSet.getInt(ind++);
            String building = resultSet.getString(ind++);
            String roomNumber = resultSet.getString(ind++);
            int maxCapacity = resultSet.getInt(ind++);

            PreRegProto.CourseData.Builder courseDataBuilder = PreRegProto.CourseData.newBuilder();

            courseDataBuilder
                    .setCRN(courseCRN)
                    .setSectionNumber(sectionNumber)
                    .setCourseName(courseName)
                    .setTime(time)
                    .setCapacity(capacity)
                    .setCredits(credits)
                    .setInstructor(PreRegProto.UserData.newBuilder()
                            .setId(instructorId)
                            .setUsername(userName == null ? "TBA":userName)
                            .setEmail(email == null ? "TBA":email)
                            .setAccess(PreRegProto.UserData.Access.PROFESSOR)
                            .setName(name == null ? "TBA":name)
                            .build())
                    .setClassRoom(PreRegProto.ClassRoomData.newBuilder()
                            .setId(classRoomId)
                            .setBuilding(building == null ? "TBA":building)
                            .setRoomNumber(roomNumber == null ? "TBA":roomNumber)
                            .setMaxCapacity(maxCapacity)
                            .build());

            PreRegProto.CourseData course = courseDataBuilder.build();

            scheduleBuilder.addCourse(course);
        }
        PreRegProto.CourseList schedule = scheduleBuilder.build();

        return schedule;
    }

    @Override
    public void addCourse(PreRegProto.CourseData course) throws SQLException {
        String query = "INSERT INTO COURSES " +
                "(COURSENAME, INSTRUCTORID, TIME, CREDITS, SECTIONNUMBER, CLASSROOM, MAXCAPACITY) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        int ind = 1;
        statement.setString(ind++, course.getCourseName());
        statement.setInt(ind++, course.getInstructor().getId());
        statement.setString(ind++, course.hasTime() ? "TBA":course.getTime());
        statement.setInt(ind++, course.getCredits());
        statement.setInt(ind++, course.getSectionNumber());
        statement.setInt(ind++, course.hasClassRoom() ? 0:course.getClassRoom().getId());
        statement.setInt(ind++, course.getMaxCapacity());

        statement.executeUpdate();
    }

    @Override
    public void updateCourse(PreRegProto.CourseData course) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        String query = "UPDATE COURSES SET ";
        if (course.hasTime()) {
            query += "TIME=\"" + course.getTime() + "\"";
        }
        if (course.hasClassRoom()) {
            query += "CLASSROOM=" + course.getClassRoom().getId();
        }
        if (course.hasMaxCapacity()) {
            query += "MAXCAPACITY=" + course.getMaxCapacity();
        }
        if (course.hasInstructor()) {
            query += "INSTRUCTORID=" + course.getInstructor().getId();
        }
        if (course.hasCredits()) {
            query += "CREDITS=" + course.getCredits();
        }
        query += " " + "WHERE CRN=" + course.getCRN();

        statement.executeUpdate(query);
    }


    @Override
    public void addCourseRequest(PreRegProto.CourseRequest courseRequest) throws Exception {
        String query = "INSERT INTO REQUESTS (FROMID, TOUSERNAME, TYPE, INFO) " +
                "VALUES ( ?, ?, ?, ?)";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        String type = courseRequest.getType();
        int ind = 1;
        preparedStatement.setInt(ind++, courseRequest.getFromId());
        preparedStatement.setString(ind++, courseRequest.getToUsername());
        preparedStatement.setString(ind++, courseRequest.getType());
        if (type.equals(PreRegMessageFactory.CHANGE_TIME_REQUEST.getType())) {
            preparedStatement.setString(ind++, courseRequest.getInfo());
        }
        if (type.equals(PreRegMessageFactory.OPEN_COURSE_REQUEST.getType())) {
            preparedStatement.setString(ind++, courseRequest.getInfo());
        }
        if (type.equals(PreRegMessageFactory.CAPACITY_REQUEST.getType())) {
            preparedStatement.setString(ind++, courseRequest.getInfo());
            preparedStatement.execute("INSERT INTO REGISTERED_COURSES (STUDENTID, CRN) VALUES ("+courseRequest.getFromId()+","+ courseRequest.getInfo() +")");
        }
        preparedStatement.executeUpdate();
    }

    @Override
    public PreRegProto.CourseRequestList queryCourseRequests(String instructorUsername) throws Exception {
        Statement statement = databaseConnection.createStatement();
        String query = "SELECT ID, TOUSERNAME, FROMID, TYPE, INFO FROM REQUESTS WHERE TOUSERNAME = \"" + instructorUsername + "\"";
        ResultSet resultSet = statement.executeQuery(query);
        PreRegProto.CourseRequestList.Builder requestsList = PreRegProto.CourseRequestList.newBuilder();

        while (resultSet.next()) {
            int ind = 1;
            int requestId = resultSet.getInt(ind++);
            String toUsername = resultSet.getString(ind++);
            int fromId = resultSet.getInt(ind++);
            String type = resultSet.getString(ind++);
            String info = resultSet.getString(ind++);

            PreRegProto.CourseRequest.Builder requestBuilder = PreRegProto.CourseRequest.newBuilder();

            requestBuilder
                    .setFromId(fromId)
                    .setToUsername(toUsername)
                    .setType(type)
                    .setInfo(info);

            PreRegProto.CourseRequest request = requestBuilder.build();

            requestsList.addCourseRequest(request);

        }
        return requestsList.build();
    }


    @Override
    public PreRegProto.ClassRoomList queryAvailableClassRooms(PreRegProto.CourseData course) throws Exception {
        Statement statement = databaseConnection.createStatement();
        String query = "SELECT ID, BUILDING, ROOMNUMBER, CLASSROOMS.MAXCAPACITY FROM CLASSROOMS " +
                "LEFT JOIN COURSES ON CLASSROOMS.ID = COURSES.CLASSROOM " +
                "WHERE COURSES.TIME != \"" + course.getTime() + "\"";

        ResultSet resultSet = statement.executeQuery(query);

        PreRegProto.ClassRoomList.Builder classRooms = PreRegProto.ClassRoomList.newBuilder();

        while (resultSet.next()) {
            int ind = 1;
            int id = resultSet.getInt(ind++);
            String building = resultSet.getString(ind++);
            String roomNumber = resultSet.getString(ind++);
            int maxCapacity = resultSet.getInt(ind++);

            PreRegProto.ClassRoomData.Builder classRoom = PreRegProto.ClassRoomData.newBuilder();

            classRoom.setId(id)
                    .setBuilding(building)
                    .setRoomNumber(roomNumber)
                    .setMaxCapacity(maxCapacity);

            classRooms.addClassRoom(classRoom.build());
        }

        return classRooms.build();
    }
}
