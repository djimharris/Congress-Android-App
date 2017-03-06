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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

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
 * Created by Jim Harris on 01-12-2016.
 */

public class FavFragment extends Fragment {

    private Activity mainActivity;
    private ArrayList<LegisRowObject> fav_legis = new ArrayList<LegisRowObject>();
    private ArrayList<BillRowObject> fav_bill = new ArrayList<BillRowObject>();
    private ArrayList<CommRowObject> fav_comm = new ArrayList<CommRowObject>();

    private String json_url_legis = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=1&subchoice=1";
    private String json_url_bill_active = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=2&subchoice=1";
    private String json_url_bill_new = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=2&subchoice=2";
    private String json_url_comm = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=3";

    private ListAdapter customListAdapter_legis;
    private ListAdapter customListAdapter_bill;
    private ListAdapter customListAdapter_comm;

    private LegisRowObject legisSelected;
    private BillRowObject billSelected;
    private CommRowObject commSelected;

    ArrayList<String> legis_index = new ArrayList<String>();

    RequestQueue queue;
    private static String TAG = "WebTag";

    FavTracker tracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fav_fragment, container, false);

        TabHost tabHost = (TabHost)view.findViewById(R.id.fav_tab);
        tabHost.setup();


        TabHost.TabSpec tab1 = tabHost.newTabSpec("Legislators");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Bills");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Committees");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Legislators");
        tab1.setContent(R.id.fav_legis);

        tab2.setIndicator("Bills");
        tab2.setContent(R.id.fav_bill);

        tab3.setIndicator("Committees");
        tab3.setContent(R.id.fav_comm);


        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        tracker = (FavTracker) getActivity().getApplicationContext();

        TabHost tabHost = (TabHost) getActivity().findViewById(R.id.fav_tab);
        tabHost.setCurrentTab(tracker.getTab());
        Log.i(TAG, "onActivityCreated: Tab " + tracker.getTab());

        customListAdapter_legis = new LegisAdapter(getActivity(), fav_legis);// Pass the food arrary to the constructor.
        final ListView customListView_legis = (ListView) getActivity().findViewById(R.id.fav_legis);
        customListView_legis.setAdapter(customListAdapter_legis);

        customListAdapter_bill = new BillAdapter(getActivity(), fav_bill);// Pass the food arrary to the constructor.
        final ListView customListView_bill = (ListView) getActivity().findViewById(R.id.fav_bill);
        customListView_bill.setAdapter(customListAdapter_bill);

        customListAdapter_comm = new CommAdapter(getActivity(), fav_comm);// Pass the food arrary to the constructor.
        final ListView customListView_comm = (ListView) getActivity().findViewById(R.id.fav_comm);
        customListView_comm.setAdapter(customListAdapter_comm);

        customListView_legis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                legisSelected = (LegisRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), LegisDetails.class);
                intent.putExtra("bioguide_id", legisSelected.getBioguide_id());
                tracker.setTab(0);
                startActivity(intent);

            }
        });

        customListView_bill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                billSelected = (BillRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), BillDetails.class);
                intent.putExtra("bill_id", billSelected.getBill_id());
                tracker.setTab(1);
                startActivity(intent);

            }
        });

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                commSelected = (CommRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), CommsDetails.class);
                intent.putExtra("committee_id", commSelected.getComm_id());
                tracker.setTab(2);
                startActivity(intent);

            }
        };

        final View.OnClickListener listener_index = new View.OnClickListener() {
            public void onClick(View v) {
                String firstLetter = (String) v.getTag();
                int index = 0;
                if (fav_legis != null) {
                    for (LegisRowObject legisObj : fav_legis) {
//                        Log.i(TAG, "Legis " + firstLetter);
                        if ((legisObj.getState()).startsWith(firstLetter)) {
                            index = fav_legis.indexOf(legisObj);
                            break;
                        }
                    }
                }
                customListView_legis.setSelectionFromTop(index, 0);
            }
        };

        customListView_comm.setOnItemClickListener(listener);

        if(fav_legis.size() == 0){
            queue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url_legis, (JSONArray) null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int count = 0;
                            while (count < response.length()) {
                                try {
                                    String district;

                                    JSONObject jsonObject = response.getJSONObject(count);
                                    if (jsonObject.getString("district").equals("null")) {
                                        district = "0";
                                    } else {
                                        district = jsonObject.getString("district");
                                    }
                                    LegisRowObject legisRowObject = new LegisRowObject(jsonObject.getString("bioguide_id"),
                                            jsonObject.getString("first_name"),
                                            jsonObject.getString("last_name"),
                                            jsonObject.getString("party"),
                                            jsonObject.getString("state_name"),
                                            district);

                                    if(tracker.containsBioguide(jsonObject.getString("bioguide_id"))) {
                                        fav_legis.add(legisRowObject);
                                        if(!legis_index.contains(jsonObject.getString("last_name").substring(0, 1))){
                                            legis_index.add(jsonObject.getString("last_name").substring(0, 1));
                                        }
                                    }



                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(fav_legis, new LastNameSorter());
                            ((BaseAdapter) customListAdapter_legis).notifyDataSetChanged();

                            Collections.sort(legis_index);

                            String index;
                            for(int i = 0; i < legis_index.size(); i ++){
                                TextView index_text = new TextView(getActivity());
                                index_text.setText(legis_index.get(i));
                                index_text.setTag(legis_index.get(i));
                                index_text.setOnClickListener(listener_index);
                                LinearLayout line = (LinearLayout) getActivity().findViewById(R.id.indexer_legis);
                                line.addView(index_text);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonArrayRequest.setTag(TAG);
            queue.add(jsonArrayRequest);
        }

        if(fav_bill.size() == 0){
            queue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url_bill_active, (JSONArray) null,
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

                                    if(tracker.containsBill(jsonObject.getString("bill_id"))) {
                                        fav_bill.add(billRowObject);
                                    }



                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(fav_bill, new BillComparator());
                            ((BaseAdapter) customListAdapter_bill).notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonArrayRequest.setTag(TAG);
            queue.add(jsonArrayRequest);

            JsonArrayRequest jsonArrayRequest_new = new JsonArrayRequest(Request.Method.GET, json_url_bill_new, (JSONArray) null,
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
                                    if(tracker.containsBill(jsonObject.getString("bill_id")) && !jsonObject.getJSONObject("history").getBoolean("active") ) {
                                        fav_bill.add(billRowObject);

                                    }

                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(fav_bill, new BillComparator());
                            ((BaseAdapter) customListAdapter_bill).notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonArrayRequest_new.setTag(TAG);
            queue.add(jsonArrayRequest_new);
        }

        if(fav_comm.size() == 0){
            queue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url_comm, (JSONArray) null,
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
                                    if(tracker.containsComm(jsonObject.getString("committee_id"))) {
                                        fav_comm.add(commRowObject);
                                    }

                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(fav_comm, new CommSorter());
                            ((BaseAdapter) customListAdapter_comm).notifyDataSetChanged();

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

    @Override
    public void onResume() {
        super.onResume();
        if(fav_legis.size() != 0 || fav_bill.size() != 0 || fav_comm.size() != 0) {
            getActivity().getFragmentManager().beginTransaction()
                    .replace(R.id.placeholder, new FavFragment())
                    .commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
