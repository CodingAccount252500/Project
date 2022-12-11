package com.example.capsuls_app;

/*import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties*/
public class VMUsers {
    public String Name;
    public String Email;
    public String PhoneNumber;
    public double Latitude;
    public double Longitude;
    public String SSN;
    public boolean IsAccountActivate;
    public String ProfileImagePath;
    public String Sex;
    public String Age;
    public String AccountTypeId;
    public String Password;
    public VMUsers(){

    }

    public VMUsers(String name, String email, String phoneNumber, double latitude, double longitude, String SSN, boolean isAccountActivate, String profileImagePath, String sex, String age,String AccountTypeId,String password) {
        Name = name;
        Email = email;
        PhoneNumber = phoneNumber;
        Latitude = latitude;
        Longitude = longitude;
        this.SSN = SSN;
        IsAccountActivate = isAccountActivate;
        ProfileImagePath = profileImagePath;
        Sex = sex;
        Age = age;
        this.AccountTypeId=AccountTypeId;
        Password=password;
    }
}

