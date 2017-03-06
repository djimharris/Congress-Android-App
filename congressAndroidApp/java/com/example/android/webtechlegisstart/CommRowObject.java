package com.example.android.webtechlegisstart;

/**
 * Created by Jim Harris on 30-11-2016.
 */

public class CommRowObject {
    String comm_id;
    String name;
    String chamber;

    public CommRowObject(String comm_id, String name, String chamber) {
        this.comm_id = comm_id;
        this.name = name;
        this.chamber = chamber;
    }

    public String getComm_id() {
        return comm_id;
    }

    public void setComm_id(String comm_id) {
        this.comm_id = comm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }
}
