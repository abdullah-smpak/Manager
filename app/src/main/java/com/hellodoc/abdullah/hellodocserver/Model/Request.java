package com.hellodoc.abdullah.hellodocserver.Model;

public class Request {
    private String pat_name;
    private String pat_phone;
    private String status;
    private String ap_id;


    public Request() {
    }

    public Request(String pat_name, String pat_phone, String status, String ap_id) {
        this.pat_name = pat_name;
        this.pat_phone = pat_phone;
        this.status = status;
        this.ap_id = ap_id;
    }

    public String getPat_name() {
        return pat_name;
    }

    public void setPat_name(String pat_name) {
        this.pat_name = pat_name;
    }

    public String getPat_phone() {
        return pat_phone;
    }

    public void setPat_phone(String pat_phone) {
        this.pat_phone = pat_phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAp_id() {
        return ap_id;
    }

    public void setAp_id(String ap_id) {
        this.ap_id = ap_id;
    }
}
