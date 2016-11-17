package resources;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

@Path("/")
@SuppressWarnings("oracle.jdeveloper.java.serialversionuid-field-missing")
public class displayProfile extends servlet
{
    @GET
    @Path("/profile/{userName}")
    @SuppressWarnings({ "org.adfemg.audits.java.system-out-usage", "oracle.jdeveloper.java.constructor-method-name" })
    public String displayProfile(@PathParam("userName") String userName)
    {
        System.out.println("<---------------------------------Profile here--------------------------------->");
        System.out.println("Session List - " + servlet.sessionList.toString());
        JSONObject j = new JSONObject();
        int        index = -1;
        for (int i = 0; i < servlet.sessionList.size(); i++)
        {
            String currUserName = servlet.sessionList.get(i).getUserRemote().getUser().getUsername();
            System.out.println(servlet.sessionList.get(i).getUserRemote().toString());
            if (currUserName.equalsIgnoreCase(userName))
            {
                index = i;
                break;
            }
        }
        if (index != -1) //found registered user
        {
            System.out.println("User '" + servlet.sessionList.get(index).getUserRemote().getUser().getUsername() + 
                               "' Login State: " + servlet.sessionList.get(index).getUserRemote().getUser().getLoginState());
            if (servlet.sessionList.get(index).getUserRemote().getUser().getLoginState()) //user is currently logged in and can access information
                j.put("User Information", servlet.sessionList.get(index).getUserRemote().showUserInformation());
            else //user is not currently logged in
                j.put("Error", "User '" + servlet.sessionList.get(index).getUserRemote().getUser().getUsername() + "' is not logged in");
        }
        else //no registered user found
            j.put("Error", "User '" + userName + "' does not exist");
        System.out.println("Session List - " + servlet.sessionList.toString());
        System.out.println("<-------------------------------END Profile here------------------------------->");
        String returnString = j.toString() 
                              + "<!DOCTYPE html>" 
                              + "<html>"
                              +     "<head>"
                              +         "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\"/>"
                              +         "<title>Profile</title>"
                              +     "</head>"
                              +     "<body>"
                              +         "<form action=\"logout/" + servlet.sessionList.get(index).getSessID() + "\" name=\"logout\" method=\"post\">"
                              +             "<input type=\"submit\" name=\"logout\" value=\"Log Out\"><br>"
                              +         "</form>"
                              +     "</body>"
                              + "</html>";
        return returnString;
    }
    
    @POST
    @Path("/profile/logout/{sessID}")
    @SuppressWarnings({ "oracle.jdeveloper.webservice.rest.broken-resource-warning",
                        "org.adfemg.audits.java.system-out-usage" })
    public String doLogOut(@PathParam("sessID") String sessID) throws ServletException, IOException
    {
        for (int i = 0; i < servlet.sessionList.size(); i++)
        {
            String listedID = servlet.sessionList.get(i).getSessID();
            System.out.println(listedID + " --- " + sessID);
            if (listedID.equals(sessID))
            {
                servlet.sessionList.get(i).setSessID(""); //removes session but does not delete SessionObject - retains user information
                servlet.sessionList.get(i).getUserRemote().setLoginState(false);
                System.out.println("User '" + servlet.sessionList.get(i).getUserRemote().getUser().getUsername() + "' successfully logged out!");
                return "<!DOCTYPE html>\n" + 
                "<html>\n" + 
                "    <head>\n" + 
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\"/>\n" + 
                "        <title>Index</title>\n" + 
                "    </head>\n" + 
                "    <body>\n" + 
                "        <a href=\"../../Login.html\"><button type=\"button\">Login</button></a>\n" + 
                "        <a href=\"../../Register.html\"><button type=\"button\">Register New User</button></a>\n" + 
                "    </body>\n" + 
                "</html>";
            }
        }
        //Should never happen if user is logged in
        System.out.println("Error: No User to Log Out");
        return "<!DOCTYPE html>\n" + 
                "<html>\n" + 
                "    <head>\n" + 
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\"/>\n" + 
                "        <title>Index</title>\n" + 
                "    </head>\n" + 
                "    <body>\n" + 
                "        <a href=\"Login.html\"><button type=\"button\">Login</button></a>\n" + 
                "        <a href=\"Register.html\"><button type=\"button\">Register New User</button></a>\n" + 
                "    </body>\n" + 
                "</html>";
    }
}