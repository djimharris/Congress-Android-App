package com.example.android.webtechlegisstart;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Jim Harris on 01-12-2016.
 */

public class FavTracker extends Application {
    ArrayList<String> bio_guide = new ArrayList<String>();
    ArrayList<String> bill_id = new ArrayList<String>();
    ArrayList<String> comm_id = new ArrayList<String>();
    int tab = 0;

    public boolean containsBioguide(String id){
        return bio_guide.contains(id);
    }

    public boolean containsBill(String id){
        return bill_id.contains(id);
    }

    public boolean containsComm(String id){
        return comm_id.contains(id);
    }

    public void insertBioguide(String id){
        bio_guide.add(id);
    }

    public void insertBill(String id){
        bill_id.add(id);
    }

    public void insertComm(String id){
        comm_id.add(id);
    }

    public void removeBioguide(String id){
        bio_guide.remove(id);
    }

    public void removeBill(String id){
        bill_id.remove(id);
    }

    public void removeComm(String id){
        comm_id.remove(id);
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }
}
