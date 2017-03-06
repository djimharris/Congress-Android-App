package com.example.android.webtechlegisstart;

import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 * Created by Jim Harris on 30-11-2016.
 */

public class BillComparator implements Comparator<BillRowObject>{

    @Override public int compare(BillRowObject e1, BillRowObject e2) {

        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = dt.parse(e1.getBill_date());
            Date date2 = dt.parse(e2.getBill_date());
            if(date1.before(date2)) {
                return 1;
            }
            if(date2.before(date1)){
                return -1;
            }

        }
        catch (ParseException e){

        }
        return 0;
    }


}
