package com.example.automateddiagnosticsystem;

import java.sql.Time;
import java.util.Date;

public class PatientsFollowOn  {
    String followondate;
    String dateadded;
    String doctorname;
    boolean reminder;
    String contact;
    String description;
    String hospital;
    String time;
    String email;
    boolean status = false;
    boolean requested;
    public PatientsFollowOn(){

    }



    public PatientsFollowOn(String followOnDate, String dateAdded, String doctorName, boolean reminder, String contact, String description, String hospital, String time, String email,
                            boolean requested) {
        this.followondate = followOnDate;
        this.dateadded = dateAdded;
        this.doctorname = doctorName;
        this.reminder = reminder;
        this.contact = contact;
        this.description = description;
        this.hospital = hospital;
        this.time=time;
        this.email=email;
        this.requested = requested;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public String getEmail() {
        return email;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setFollowondate(String followondate) {
        this.followondate = followondate;
    }

    public void setDateadded(String dateadded) {
        this.dateadded = dateadded;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getFollowondate() {
        return followondate;
    }

    public String getDateadded() {
        return dateadded;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public boolean isReminder() {
        return reminder;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public String getHospital() {
        return hospital;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}
