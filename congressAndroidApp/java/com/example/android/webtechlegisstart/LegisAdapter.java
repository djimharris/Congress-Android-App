package com.example.android.webtechlegisstart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jim Harris on 28-11-2016.
 */

public class LegisAdapter extends ArrayAdapter<LegisRowObject> {

    public LegisAdapter(Context context, ArrayList<LegisRowObject> list) {
        super(context, R.layout.legis_row, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater legisInflater = LayoutInflater.from(getContext());
        View customView = legisInflater.inflate(R.layout.legis_row, parent, false);

        LegisRowObject item = (LegisRowObject) getItem(position);

        TextView legisName = (TextView) customView.findViewById(R.id.legis_name);
        TextView legisInfo = (TextView) customView.findViewById(R.id.legis_info);

        ImageView legisImage = (ImageView) customView.findViewById(R.id.legis_image);
        Picasso.with(getContext()).load("https://theunitedstates.io/images/congress/original/" + item.getBioguide_id() + ".jpg").resize(200, 200)
                .centerCrop().into(legisImage);

        String name = item.getLast_name() + ", " + item.getFirst_name();
        String info = "(" + item.getParty() + ") " + item.getState() + " District - " + item.getDistrict();

        legisName.setText(name);
        legisInfo.setText(info);

        return customView;
    }
}
