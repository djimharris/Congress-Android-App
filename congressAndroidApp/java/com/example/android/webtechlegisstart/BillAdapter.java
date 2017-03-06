package com.example.android.webtechlegisstart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 * Created by Jim Harris on 30-11-2016.
 */

public class BillAdapter extends ArrayAdapter<BillRowObject> {

    public BillAdapter(Context context, ArrayList<BillRowObject> list) {
        super(context, R.layout.bills_row, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater billInflater = LayoutInflater.from(getContext());
        View customView = billInflater.inflate(R.layout.bills_row, parent, false);

        BillRowObject item = (BillRowObject) getItem(position);

        TextView billId = (TextView) customView.findViewById(R.id.bill_id);
        TextView billTitle = (TextView) customView.findViewById(R.id.bill_content);
        TextView billDate = (TextView) customView.findViewById(R.id.bill_date);

        billId.setText(item.getBill_id().toUpperCase());
        billTitle.setText(item.getBill_title());

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dt.parse(item.getBill_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd, yyyy");
        String dateString = dt1.format(date);

        billDate.setText(dateString);

        return customView;


    }
}
