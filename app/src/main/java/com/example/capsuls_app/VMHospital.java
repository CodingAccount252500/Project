package com.example.capsuls_app;
/*import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties*/
public class VMHospital {

    public  String Name;
    public  String Phone;
    public  String Image;
    public  String Pio;
    public  String Latitude;
    public  String Longitude;

    public VMHospital(String name, String phone, String image, String pio, String latitude, String longitude) {
        Name = name;
        Phone = phone;
        Image = image;
        Pio = pio;
        Latitude = latitude;
        Longitude = longitude;
    }
}
