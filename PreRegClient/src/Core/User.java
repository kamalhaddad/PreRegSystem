package Core;

public final class User
{
    private static int id;
    private static String username;
    private static String title;
    
    public static void init(int cid, String cUsername, String cTitle)
    {
        id = cid;
        username = cUsername;
        title = cTitle;

    }
    
    public static void clear()
    {
        id = 0;
        username = null;
        title = null;

    }
    
    public static int getId()
    {
        return id;
    }
    
    public static String getUsername()
    {
        return username;
    }
    
    public static String getTitle()
    {
        return title;
    }
    
    public static void setTitle(String newTitle)
    {
        title = newTitle;
    }

    public static String getDisplayTitle()
    {
        return username;
    }
    
    public static String getUITitle()
    {
        return String.format("Pre-Registration System <%s>", getUsername());
    }
}
