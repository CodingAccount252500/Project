package com.example.capsuls_app;
/*import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties*/
public class VMPrescription {
    public String Date;
    public String CustomerId;
    public String DoctorId;
    public String Note;
    public boolean IsNeedApproved;
    public String FilePath;

    public VMPrescription(String date, String customerId, String doctorId, String note, boolean isNeedApproved, String filePath) {
        Date = date;
        CustomerId = customerId;
        DoctorId = doctorId;
        Note = note;
        IsNeedApproved = isNeedApproved;
        FilePath = filePath;
    }
}
