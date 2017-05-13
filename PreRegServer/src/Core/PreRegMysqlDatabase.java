package Core;

import com.prereg.base.data.PreRegProto;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    public PreRegProto.UserList queryUser(PreRegProto.UserData userQuery) throws SQLException {
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
            int id = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String email = resultSet.getString(3);
            String title = resultSet.getString(4);
            String name = resultSet.getString(5);
            int creditsUsed = resultSet.getInt(6);
            int maxCredits = resultSet.getInt(7);

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
    public PreRegProto.CourseList queryCourse(PreRegProto.CourseData courseQuery) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        String query = "SELECT * FROM COURSES t1 JOIN Classrooms t2 ON t2.Room = t1.ClassRoom";
        String queryConditions = "WHERE";
        if (courseQuery.hasCRN()) {
            queryConditions += " CRN=" + courseQuery.getCRN() + " ";
        }
        if (courseQuery.hasCourseName()) {
            queryConditions += " COURSENAME=\"" + courseQuery.getCourseName() + "\" ";
        }
        if (courseQuery.hasInstructor()) {
            queryConditions += " INSTRUCTORUSERNAME=\"" + courseQuery.getInstructor().getUsername() + "\" ";
        }
        if (!queryConditions.equals("WHERE")) {
            query += " " + queryConditions;
        }
        ResultSet result = statement.executeQuery(query);
        PreRegProto.CourseList.Builder courseListBuilder = PreRegProto.CourseList.newBuilder();
        while (result.next()) {
            int instructorId = result.getInt(3);
            String instructorUsername = result.getString(4);
            String courseName = result.getString(5);
            int CRN = result.getInt(1);
            int sectionNumber = result.getInt(2);
            String time = result.getString(6);
            String classRoom = result.getString(7);
            int capacity = result.getInt(8);
            int maxCap = result.getInt(14);
            PreRegProto.CourseData courseData = PreRegProto.CourseData.newBuilder()
                    .setCRN(CRN)
                    .setCourseName(courseName)
                    .setTime(time)
                    .setClassRoom(classRoom)
                    .setCapacity(capacity)
                    .setMaxCapacity(maxCap)
                    .setSectionNumber(sectionNumber)
                    .setInstructor(PreRegProto.UserData.newBuilder()
                            .setId(instructorId)
                            .setUsername(instructorUsername)
                            .setAccess(PreRegProto.UserData.Access.PROFESSOR).build())
                    .build();
            courseListBuilder.addCourse(courseData);
        }
        return courseListBuilder.build();
    }

    @Override
    public void addCourseRequest(int fromId, int type, PreRegProto.CourseData courseData) {

    }

    @Override
    public PreRegProto.CourseList querySchedule(int studentId) {
        return null;
    }
}
