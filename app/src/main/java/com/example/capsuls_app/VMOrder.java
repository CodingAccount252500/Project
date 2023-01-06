package com.example.capsuls_app;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;


@IgnoreExtraProperties
public class VMOrder {

    public double Latitude;
    public double Longitude;
    public String PrescriptionDrugFile;
    public boolean IsCompleted;
    public String Notes;
    public String DriverId;
    public String Status;
    public String Date;
    public static HashMap<String,Integer> CartOfItems;
    public String UserId;
    public String Address;


    public VMOrder(double latitude, double longitude, String prescriptionDrugFile, boolean isCompleted, String notes, String driverId, String status, String date, HashMap<String,Integer> cartOfItems,String userId,String address) {
        Latitude = latitude;
        Longitude = longitude;
        PrescriptionDrugFile = prescriptionDrugFile;
        IsCompleted = isCompleted;
        Notes = notes;
        DriverId = driverId;
        Status = status;
        Date = date;
        CartOfItems = cartOfItems;
        UserId=userId;
        Address=address;
    }

    public VMOrder() {
    }
}
