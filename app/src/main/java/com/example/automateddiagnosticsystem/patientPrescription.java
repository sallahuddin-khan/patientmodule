package com.example.automateddiagnosticsystem;

public class patientPrescription {
    String contact;
    String medicine;
    String doctor;
    String dosage;
    String instructions;
    boolean status;
    boolean morning;
    boolean evening;
    boolean afternoon;
    boolean notify;

    patientPrescription(){

    }



    public patientPrescription(String contact, String medicinee, String doctor, boolean status, boolean morning, boolean evening, boolean afternoon, boolean notify,String dosage, String instructions) {
        this.contact = contact;
        this.medicine = medicine;
        this.doctor=doctor;
        this.status = status;
        this.morning = morning;
        this.evening = evening;
        this.afternoon = afternoon;
        this.notify = notify;
        this.dosage=dosage;
        this.instructions = instructions;
    }

    public String getContact() {
        return contact;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getDoctor() {
        return doctor;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isMorning() {
        return morning;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public String getDosage() {
        return dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
