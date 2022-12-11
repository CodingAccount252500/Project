package com.example.capsuls_app;
/*import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties*/
public class VMDoctor {
    public String Name;
    public String Phone;
    public String Specialization;
    public String HospitalId;

    public VMDoctor(String name, String phone, String specialization, String hospitalId) {
        Name = name;
        Phone = phone;
        Specialization = specialization;
        HospitalId = hospitalId;
    }
}
