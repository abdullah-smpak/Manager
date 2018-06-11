package com.hellodoc.abdullah.hellodocserver.Model;

public class Appointment {

        private String Patient_Name;
        private String Patient_Age;
        private String Patient_Gender;
        private String Appointment_Date_;
        private String Appointment_Time;
        private String Patient_Phone;
        private  String Status;
        private  String DrName;

        public Appointment() {
        }

        public Appointment(String patient_Name, String patient_Age, String patient_Gender, String appointment_Date_, String appointment_Time, String patient_Phone,String drName) {
            Patient_Name = patient_Name;
            Patient_Age = patient_Age;
            Patient_Gender = patient_Gender;
            Appointment_Date_ = appointment_Date_;
            Appointment_Time = appointment_Time;
            Patient_Phone = patient_Phone;
            Status = "0";
            DrName =  drName;
        }

        public String getDrName() {
            return DrName;
        }

        public void setDrName(String drName) {
            DrName = drName;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getPatient_Name() {
            return Patient_Name;
        }

        public void setPatient_Name(String patient_Name) {
            Patient_Name = patient_Name;
        }

        public String getPatient_Age() {
            return Patient_Age;
        }

        public void setPatient_Age(String patient_Age) {
            Patient_Age = patient_Age;
        }

        public String getPatient_Gender() {
            return Patient_Gender;
        }

        public void setPatient_Gender(String patient_Gender) {
            Patient_Gender = patient_Gender;
        }

        public String getAppointment_Date_() {
            return Appointment_Date_;
        }

        public void setAppointment_Date_(String appointment_Date_) {
            Appointment_Date_ = appointment_Date_;
        }

        public String getAppointment_Time() {
            return Appointment_Time;
        }

        public void setAppointment_Time(String appointment_Time) {
            Appointment_Time = appointment_Time;
        }

        public String getPatient_Phone() {
            return Patient_Phone;
        }

        public void setPatient_Phone(String patient_Phone) {
            Patient_Phone = patient_Phone;
        }
}
