package com.example.android.webtechlegisstart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jim Harris on 30-11-2016.
 */

public class CommAdapter extends ArrayAdapter<CommRowObject> {

    public CommAdapter(Context context, ArrayList<CommRowObject> list) {
        super(context, R.layout.bills_row, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater commInflater = LayoutInflater.from(getContext());
        View customView = commInflater.inflate(R.layout.bills_row, parent, false);

        CommRowObject item = (CommRowObject) getItem(position);

        TextView commId = (TextView) customView.findViewById(R.id.bill_id);
        TextView commName = (TextView) customView.findViewById(R.id.bill_content);
        TextView commChamber = (TextView) customView.findViewById(R.id.bill_date);

        commId.setText(item.getComm_id().toUpperCase());
        commName.setText(item.getName());
        commChamber.setText(item.getChamber().substring(0, 1).toUpperCase() + item.getChamber().substring(1));

        return customView;
    }
}
