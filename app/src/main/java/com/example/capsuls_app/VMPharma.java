package com.example.capsuls_app;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class VMPharma {
    public String  Name;
    public String  ImageLink;
    public String  Address;
    public String  Phone;
    public String  Owner;
    public double  latauide;
    public double  longitude;
    public String  responsiblePerson;


    public VMPharma(String name, String imageLink, String address, String phone, String owner, double latauide, double longitude, String responsiblePerson) {
        Name = name;
        ImageLink = imageLink;
        Address = address;
        Phone = phone;
        Owner = owner;
        this.latauide = latauide;
        this.longitude = longitude;
        this.responsiblePerson = responsiblePerson;
    }

    public VMPharma() {
    }
}
