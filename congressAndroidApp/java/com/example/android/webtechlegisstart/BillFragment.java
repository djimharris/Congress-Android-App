package com.example.android.webtechlegisstart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class BillFragment extends Fragment {
    private Activity mainActivity;
    private ArrayList<BillRowObject> bills_active = new ArrayList<BillRowObject>();
    private ArrayList<BillRowObject> bills_new = new ArrayList<BillRowObject>();

    private ListAdapter customListAdapter_active;
    private ListAdapter customListAdapter_new;
    String json_url_active = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=2&subchoice=1";
    String json_url_new = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=2&subchoice=2";
    RequestQueue queue;
    RequestQueue queue_new;

    private BillRowObject billSelected;

    private static String TAG = "WebTag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_fragment, container, false);

        TabHost tabHost = (TabHost)view.findViewById(R.id.bill_tab);
        tabHost.setup();


        TabHost.TabSpec tab1 = tabHost.newTabSpec("Active Bills");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("New Bills");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Active Bills");
        tab1.setContent(R.id.bill_active);

        tab2.setIndicator("New Bills");
        tab2.setContent(R.id.bill_new);

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customListAdapter_active = new BillAdapter(getActivity(), bills_active);// Pass the food arrary to the constructor.
        final ListView customListView_active = (ListView) getActivity().findViewById(R.id.bill_active);
        customListView_active.setAdapter(customListAdapter_active);

        customListAdapter_new = new BillAdapter(getActivity(), bills_new);// Pass the food arrary to the constructor.
        final ListView customListView_new = (ListView) getActivity().findViewById(R.id.bill_new);
        customListView_new.setAdapter(customListAdapter_new);

        customListView_active.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                billSelected = (BillRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), BillDetails.class);
                intent.putExtra("bill_id", billSelected.getBill_id());
                startActivity(intent);

            }
        });
        customListView_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                billSelected = (BillRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), BillDetails.class);
                intent.putExtra("bill_id", billSelected.getBill_id());
                startActivity(intent);

            }
        });

        if(bills_active.size() == 0){
            queue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url_active, (JSONArray) null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int count = 0;
                            while (count < response.length()) {
                                try {
                                    String title;

                                    JSONObject jsonObject = response.getJSONObject(count);
                                    if (jsonObject.getString("short_title").equals("null")) {
                                        title = jsonObject.getString("official_title");
                                    } else {
                                        title = jsonObject.getString("short_title");
                                    }
                                    BillRowObject billRowObject = new BillRowObject(jsonObject.getString("bill_id"),
                                            title,
                                            jsonObject.getString("introduced_on"));
                                    bills_active.add(billRowObject);

                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(bills_active, new BillComparator());
                            ((BaseAdapter) customListAdapter_active).notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonArrayRequest.setTag(TAG);
            queue.add(jsonArrayRequest);
        }

        if(bills_new.size() == 0){
            queue_new = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest_new = new JsonArrayRequest(Request.Method.GET, json_url_new, (JSONArray) null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int count = 0;
                            while (count < response.length()) {
                                try {
                                    String title;

                                    JSONObject jsonObject = response.getJSONObject(count);
                                    if (jsonObject.getString("short_title").equals("null")) {
                                        title = jsonObject.getString("official_title");
                                    } else {
                                        title = jsonObject.getString("short_title");
                                    }
                                    BillRowObject billRowObject = new BillRowObject(jsonObject.getString("bill_id"),
                                            title,
                                            jsonObject.getString("introduced_on"));
                                    bills_new.add(billRowObject);

                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(bills_new, new BillComparator());
                            ((BaseAdapter) customListAdapter_new).notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonArrayRequest_new.setTag(TAG);
            queue_new.add(jsonArrayRequest_new);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
        if (queue_new != null) {
            queue_new.cancelAll(TAG);
        }
    }
}
