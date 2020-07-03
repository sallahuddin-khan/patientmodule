package com.example.automateddiagnosticsystem;

public class userModel {
    String name;
    String email;
    String hospital;

    public userModel(){}

    public userModel(String doctor, String email, String hospital) {
        this.name = doctor;
        this.email = email;
        this.hospital = hospital;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHospital() {
        return hospital;
    }

    public void setName(String doctor) {
        this.name = doctor;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
