package com.example.android.webtechlegisstart;

import java.util.Comparator;

/**
 * Created by Jim Harris on 28-11-2016.
 */

public class StateSorter implements Comparator<LegisRowObject> {

    @Override public int compare(LegisRowObject e1, LegisRowObject e2) {
        if(e1.getState().equals(e2.getState())){
            return e1.getLast_name().compareTo(e2.getLast_name());
        }
        return e1.getState().compareTo(e2.getState());
    }

}
