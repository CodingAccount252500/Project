package com.example.capsuls_app;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class VMPrescriptionDrug {
    public String DrugId;
    public String OrderId;
    public String Note;
    public int    Quantity;

    public VMPrescriptionDrug(String drugId, String orderId, String note, int quantity) {
        DrugId = drugId;
        OrderId = orderId;
        Note = note;
        Quantity = quantity;
    }

    public VMPrescriptionDrug() {
    }
}
