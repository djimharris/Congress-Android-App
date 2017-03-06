package com.example.android.webtechlegisstart;

import java.util.Comparator;

/**
 * Created by Jim Harris on 28-11-2016.
 */

public class LastNameSorter implements Comparator<LegisRowObject> {

    @Override public int compare(LegisRowObject e1, LegisRowObject e2) {
        return e1.getLast_name().compareTo(e2.getLast_name());

    }
}
