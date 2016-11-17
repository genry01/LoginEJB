package resources;

import javax.ejb.Remote;

import javax.enterprise.context.SessionScoped;

import org.json.JSONObject;

@Remote
@SessionScoped
public interface UserRemote
{
    UserObject getUser();
    
    String showUserInformation();
    
    void registerUser(String username, String firstName, String lastName, String email, String password, String address, String city, String state, String zipCode, String phone);
    
    public void setLoginState(boolean isLoggedIn);
    
    public boolean getLoginState();
}