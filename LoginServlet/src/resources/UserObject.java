package resources;

import java.io.Serializable;

@SuppressWarnings("oracle.jdeveloper.java.serialversionuid-field-missing")
public class UserObject implements Serializable
{
    private String username = "";
    private String password = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String zipCode = "";
    private String phoneNumber = "";
    private boolean loggedIn = false;
    
    public UserObject(String username, String password, String firstName, String lastName, String email, String address, 
                      String city, String state, String zipCode, String phoneNumber, boolean loggedIn)
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.loggedIn = loggedIn;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setLoginState(boolean isLoggedIn)
    {
        this.loggedIn = isLoggedIn;
    }

    public boolean getLoginState()
    {
        return this.loggedIn;
    }

    public String getUsername()
    {
        return username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getPassword()
    {
        return password;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}