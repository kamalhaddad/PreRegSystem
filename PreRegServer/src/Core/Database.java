package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database
{
    public Connection conn;
    
    public Database(String connString) throws Exception
    {
        System.out.printf("\nInitializing database connection.\n");
        System.out.printf("Connection String: %s\n", connString);
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(connString);
        
        System.out.printf("Database Connection Successful\n");
    }
    
    public int execute(String sql, Object... args)
    {
        try
        {
            System.out.printf("SQL: " + sql + "\n", args);
            Statement s = conn.createStatement();
            return s.executeUpdate(String.format(sql, args));
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    
    public ResultSet query(String sql, Object... args)
    {
        try
        {
            System.out.printf("SQL: " + sql + "\n", args);
            Statement s = conn.createStatement();
            return s.executeQuery(String.format(sql, args));
        }
        catch(Exception e)
        {
            return null;
        }
    }
}