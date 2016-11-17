package resources;

import java.lang.annotation.Retention;

import javax.annotation.Resource;

import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

import org.json.JSONObject;

@Stateful(name = "StatefulUserBean")
public class User implements UserRemote
{
    @Resource
    SessionContext     sessionContext;
    
    private UserObject u = new UserObject("", "", "", "", "", "", "", "", "", "", false);

    @Override
    public UserObject getUser()
    {
        return this.u;
    }

    public void setLoginState(boolean isLoggedIn)
    {
        this.u.setLoginState(isLoggedIn);
    }

    public boolean getLoginState()
    {
        return this.u.getLoginState();
    }
    
    @Override
    public String showUserInformation()
    {
        String userData = "UserName: " + this.u.getUsername() + 
                          ", Email: " + this.u.getEmail() + 
                          ", Street Address: " + this.u.getAddress() + 
                          ", City: " + this.u.getCity() + 
                          ", State: " + this.u.getState() +
                          ", Zip Code: " + this.u.getZipCode() + 
                          ", Phone Number: " + this.u.getPhoneNumber();
        return userData;
    }

    @Override
    public void registerUser(String username, String firstName, String lastName, String email, String password, String address, String city, String state, String zipCode, String phone)
    {
        u.setUsername(username);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPassword(password);
        u.setAddress(address);
        u.setCity(city);
        u.setState(state);
        u.setZipCode(zipCode);
        u.setPhoneNumber(phone);
    }
}
