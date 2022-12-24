package com.example.capsuls_app;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class VMDrug {
    public String Name;
    public String Description;
    public double Price;
    public String ImageLink;
    public int    AvailableQtn;

    public VMDrug(String name, String description, double price, String imageLink, int availableQtn) {
        Name = name;
        Description = description;
        Price = price;
        ImageLink = imageLink;
        AvailableQtn = availableQtn;
    }

    public VMDrug() {
    }
}
