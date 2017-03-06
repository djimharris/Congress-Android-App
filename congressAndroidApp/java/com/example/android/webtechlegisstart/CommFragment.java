package com.example.android.webtechlegisstart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jim Harris on 30-11-2016.
 */

public class CommFragment extends Fragment {

    private Activity mainActivity;
    private ArrayList<CommRowObject> comms_house = new ArrayList<CommRowObject>();
    private ArrayList<CommRowObject> comms_senate = new ArrayList<CommRowObject>();
    private ArrayList<CommRowObject> comms_joint = new ArrayList<CommRowObject>();

    private ListAdapter customListAdapter_house;
    private ListAdapter customListAdapter_senate;
    private ListAdapter customListAdapter_joint;
    String json_url = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=3";
    RequestQueue queue;

    private CommRowObject commSelected;
    private static String TAG = "WebTag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comm_fragment, container, false);

        TabHost tabHost = (TabHost)view.findViewById(R.id.comm_tab);
        tabHost.setup();


        TabHost.TabSpec tab1 = tabHost.newTabSpec("House");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Senate");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Joint");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("House");
        tab1.setContent(R.id.comm_house);

        tab2.setIndicator("Senate");
        tab2.setContent(R.id.comm_senate);

        tab3.setIndicator("Joint");
        tab3.setContent(R.id.comm_joint);


        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customListAdapter_house = new CommAdapter(getActivity(), comms_house);// Pass the food arrary to the constructor.
        final ListView customListView_house = (ListView) getActivity().findViewById(R.id.comm_house);
        customListView_house.setAdapter(customListAdapter_house);

        customListAdapter_senate = new CommAdapter(getActivity(), comms_senate);// Pass the food arrary to the constructor.
        final ListView customListView_senate = (ListView) getActivity().findViewById(R.id.comm_senate);
        customListView_senate.setAdapter(customListAdapter_senate);

        customListAdapter_joint = new CommAdapter(getActivity(), comms_joint);// Pass the food arrary to the constructor.
        final ListView customListView_joint = (ListView) getActivity().findViewById(R.id.comm_joint);
        customListView_joint.setAdapter(customListAdapter_joint);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                commSelected = (CommRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), CommsDetails.class);
                intent.putExtra("committee_id", commSelected.getComm_id());
                startActivity(intent);

            }
        };

        customListView_house.setOnItemClickListener(listener);
        customListView_senate.setOnItemClickListener(listener);
        customListView_joint.setOnItemClickListener(listener);

        if(comms_house.size() == 0){
            queue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url, (JSONArray) null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int count = 0;
                            while (count < response.length()) {
                                try {

                                    JSONObject jsonObject = response.getJSONObject(count);

                                    CommRowObject commRowObject = new CommRowObject(jsonObject.getString("committee_id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("chamber"));
                                    if(jsonObject.getString("chamber").equals("house")) {
                                        comms_house.add(commRowObject);
                                    }
                                    if(jsonObject.getString("chamber").equals("senate")) {
                                        comms_senate.add(commRowObject);
                                    }
                                    if(jsonObject.getString("chamber").equals("joint")) {
                                        comms_joint.add(commRowObject);
                                    }
                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(comms_house, new CommSorter());
                            Collections.sort(comms_senate, new CommSorter());
                            Collections.sort(comms_joint, new CommSorter());
                            ((BaseAdapter) customListAdapter_house).notifyDataSetChanged();
                            ((BaseAdapter) customListAdapter_senate).notifyDataSetChanged();
                            ((BaseAdapter) customListAdapter_joint).notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonArrayRequest.setTag(TAG);
            queue.add(jsonArrayRequest);
        }
    }
}
