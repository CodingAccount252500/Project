package com.example.capsuls_app;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;


@IgnoreExtraProperties
public class VMOrder {

    public double Latitude;
    public double Longitude;
    public String PrescriptionDrugId;
    public boolean IsCompleted;
    public String Notes;
    public String DriverId;
    public String Status;
    public String Date;
    public String PrescriptionFilePath;
    public ArrayList<VMDrug> Drugs;


    public VMOrder(double latitude, double longitude, String prescriptionDrugId, boolean isCompleted, String notes, String driverId, String status,String date,String prescriptionFilePath,ArrayList<VMDrug> drugs) {
        Latitude = latitude;
        Longitude = longitude;
        PrescriptionDrugId = prescriptionDrugId;
        IsCompleted = isCompleted;
        Notes = notes;
        DriverId = driverId;
        Status = status;
        Date=date;
        PrescriptionFilePath=prescriptionFilePath;
        Drugs=drugs;
    }

}
