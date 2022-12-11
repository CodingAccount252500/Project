package com.example.capsuls_app;
/*import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties*/
public class VMPrescriptionDrug {
    public String DrugId;
    public String Prescription;
    public String Note;
    public int    Quantity;

    public VMPrescriptionDrug(String drugId, String prescription, String note, int quantity) {
        DrugId = drugId;
        Prescription = prescription;
        Note = note;
        Quantity = quantity;
    }
}
