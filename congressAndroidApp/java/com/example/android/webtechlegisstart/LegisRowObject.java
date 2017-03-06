package com.example.android.webtechlegisstart;

/**
 * Created by Jim Harris on 28-11-2016.
 */

public class LegisRowObject {
    private String bioguide_id;
    private String first_name;
    private String last_name;
    private String party;
    private String state;
    private String district;

    public LegisRowObject(String bioguide_id, String first_name, String last_name, String party, String state, String district) {
        this.bioguide_id = bioguide_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.party = party;
        this.state = state;
        this.district = district;
    }

    public String getBioguide_id() {
        return bioguide_id;
    }

    public void setBioguide_id(String bioguide_id) {
        this.bioguide_id = bioguide_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }



    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
