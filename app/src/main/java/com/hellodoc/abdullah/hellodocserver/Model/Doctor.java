package com.hellodoc.abdullah.hellodocserver.Model;

public class Doctor {

    private  String Name,Image,Qualification,Address,Timings,Days,Doctor_Id;

    public Doctor() {
    }

    public Doctor(String name, String image, String qualification, String address, String timings, String days, String doctor_Id) {
        Name = name;
        Image = image;
        Qualification = qualification;
        Address = address;
        Timings = timings;
        Days = days;
        Doctor_Id = doctor_Id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTimings() {
        return Timings;
    }

    public void setTimings(String timings) {
        Timings = timings;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getDoctor_Id() {
        return Doctor_Id;
    }

    public void setDoctor_Id(String doctor_Id) {
        Doctor_Id = doctor_Id;
    }
}
