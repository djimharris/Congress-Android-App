package com.example.android.webtechlegisstart;

/**
 * Created by Jim Harris on 30-11-2016.
 */

public class BillRowObject {
    String bill_id;
    String bill_title;
    String bill_date;

    public BillRowObject(String bill_id, String bill_title, String bill_date) {
        this.bill_id = bill_id;
        this.bill_title = bill_title;
        this.bill_date = bill_date;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_title() {
        return bill_title;
    }

    public void setBill_title(String bill_title) {
        this.bill_title = bill_title;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }
}
