package resources;

import java.io.IOException;

import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;

import javax.naming.InitialContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Path;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SessionScoped
@SuppressWarnings({ "oracle.jdeveloper.java.field-not-serializable", "oracle.jdeveloper.java.serialversionuid-field-missing", "org.adfemg.audits.java.system-out-usage" })
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class servlet extends HttpServlet
{
    static ArrayList<SessionObject> sessionList = new ArrayList<SessionObject>();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (request.getParameter("register") != null)
        {
            System.out.println("<------------------------------------------REGISTERING USER------------------------------------------>");
            register(request, response);
            System.out.println("<----------------------------------------END REGISTERING USER---------------------------------------->");
        }
        if (request.getParameter("login") != null)
        {
            System.out.println("<------------------------------------------LOGGING IN USER------------------------------------------->");
            doLogin(request, response);
            System.out.println("<----------------------------------------END LOGGING IN USER----------------------------------------->");
        }
    }
    
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Session List - " + sessionList.toString());
        int    index = -1;
        String incomingSessionID = request.getSession().getId();
        for (int i = 0; i < sessionList.size(); i++)
        {
            //check to see if session already exists
            String sessionIDinList = sessionList.get(i).getSessID();
            if (sessionIDinList.equalsIgnoreCase(incomingSessionID))
            {
                index = i;
                break;
            }
        }
        SessionObject s = null;
        if (index != -1) //sessionObject exists - same client as before
        {
            s = sessionList.get(index);
            System.out.println("FOUND SESSION OBJECT IN LIST");
            //System.out.println(sessionList.toString());
        }
        else // sessionObject does not exist - need to create new sessionObject with new sessID
        {
            try
            {
                InitialContext     jndiContext = new InitialContext();
                UserRemote user = (UserRemote) jndiContext.lookup("java:global/LoginServlet/StatefulUserBean!resources.UserRemote");
                s = new SessionObject(incomingSessionID, user);
                System.out.println(user.toString());
                sessionList.add(s);
                System.out.println("CREATED NEW SESSION OBJECT - ADDED TO LIST");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }
        System.out.println("Current Session ID = " + s.getSessID());

        if (s.getUserRemote() != null)
        {
            // Check to see if username matches the information stored in list for this session
            //if (s.getUser().equals(request.getParameter("userName")) && s.getSessID().equals(incomingSessionID))
            {
                //  if match then user is connecting with same session as before

            }
            //else if(true)
            //  - if no match then different user is connecting with same session id
            //      - remove session id from previous user, register new user with this id
            s.getUserRemote().registerUser(request.getParameter("userName"), request.getParameter("firstname"), request.getParameter("lastname"), 
                                     request.getParameter("email"), request.getParameter("pwd"), request.getParameter("street"), 
                                     request.getParameter("city"), request.getParameter("test"), request.getParameter("zip"), 
                                     request.getParameter("phone"));
        }
        else
        {
            System.out.println("User is null!");
        }
        System.out.println("Session List - " + sessionList.toString());
        response.sendRedirect("index.html");
    }

    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        for (int i = 0; i < sessionList.size(); i++)
        {
            String listedUser = sessionList.get(i).getUserRemote().getUser().getUsername();
            String listedPwd = sessionList.get(i).getUserRemote().getUser().getPassword();
            if (listedUser.equals(request.getParameter("userName")) && listedPwd.equals(request.getParameter("pwd")))
            {
                System.out.println("User '" + sessionList.get(i).getUserRemote().getUser().getUsername() + "' Login State: " 
                                   + sessionList.get(i).getUserRemote().getLoginState());
                //update login state for this user
                sessionList.get(i).getUserRemote().setLoginState(true);
                
                System.out.println("User '" + sessionList.get(i).getUserRemote().getUser().getUsername() + "' Login State AFTER UPDATE: " 
                                   + sessionList.get(i).getUserRemote().getLoginState());
                System.out.println("User '" + listedUser + "' successfully logged in!");
                System.out.println(sessionList.get(i).getUserRemote().showUserInformation());
                
                String path = "/LoginServlet/profile/" + listedUser;
                response.sendRedirect(path);
                return;
            }
        }
        //TODO: Produce error for invalid credentials instead of redirect
        System.out.println("Error: Invalid Credentials");
        response.sendRedirect("Login.html");
    }
}