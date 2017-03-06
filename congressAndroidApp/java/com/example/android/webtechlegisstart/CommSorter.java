package com.example.android.webtechlegisstart;

import java.util.Comparator;

/**
 * Created by Jim Harris on 30-11-2016.
 */

public class CommSorter implements Comparator<CommRowObject> {

    @Override public int compare(CommRowObject e1, CommRowObject e2) {
        return e1.getName().compareTo(e2.getName());
    }
}
