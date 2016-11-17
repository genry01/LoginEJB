package resources;

public class SessionObject
{
    private UserRemote user;
    private String sessID;

    public String getSessID()
    {
        return sessID;
    }

    public void setSessID(String newSessID)
    {
        this.sessID = newSessID;
    }

    public SessionObject(String sessID, UserRemote user)
    {
        this.sessID = sessID;
        this.user = user;
    }

    public UserRemote getUserRemote()
    {
        return this.user;
    }
    
    public void updateUser(UserRemote newUser)
    {
        this.user = newUser;
    }
}