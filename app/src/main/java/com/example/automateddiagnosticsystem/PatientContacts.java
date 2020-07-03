package com.example.automateddiagnosticsystem;

public class PatientContacts {
   // String age;
   //String username;
    //String hospital;
    String gender;
    String contact;
   // String doctor;
    String name;

    PatientContacts(){

    }

   /* public PatientContacts(String age, String username, String hospital, String gender, String contact, String doctor) {
        this.age = age;
        this.username = username;
        this.hospital = hospital;
        this.gender = gender;
        this.contact = contact;
        this.doctor = doctor;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getHospital() {
        return hospital;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    public String getDoctor() {
        return doctor;
    }*/

    public PatientContacts(String gender, String contact, String name) {
        this.gender = gender;
        this.contact = contact;
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setName(String name) {
        this.name = name;
    }
}
