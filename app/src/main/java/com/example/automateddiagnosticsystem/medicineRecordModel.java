package com.example.automateddiagnosticsystem;

public class medicineRecordModel {
    String dateTaken;
    String timeTaken;
    String prescriptionid;
    String medicineName;
    String patientContact;

    public medicineRecordModel(){

    }

    public medicineRecordModel(String date, String time, String medicineid, String medicineName, String patientContact) {
        this.dateTaken = date;
        this.timeTaken = time;
        this.prescriptionid = medicineid;
        this.medicineName = medicineName;
        this.patientContact = patientContact;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setPrescriptionid(String prescriptionid) {
        this.prescriptionid = prescriptionid;
    }

    public String getPrescriptionid() {
        return prescriptionid;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getPatientContact() {
        return patientContact;
    }

    public void setDateTaken(String date) {
        this.dateTaken= date;
    }

    public void setTimeTaken(String time) {
        this.timeTaken = time;
    }



    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setPatientContact(String patientContact) {
        this.patientContact = patientContact;
    }
}
