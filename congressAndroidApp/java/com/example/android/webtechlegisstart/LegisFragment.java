package com.example.android.webtechlegisstart;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import android.widget.Toolbar;

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
 * Created by Jim Harris on 28-11-2016.
 */

public class LegisFragment extends Fragment {

    private Activity mainActivity;
    private ArrayList<LegisRowObject> legislators = new ArrayList<LegisRowObject>();
    private ArrayList<LegisRowObject> legislators_house = new ArrayList<LegisRowObject>();
    private ArrayList<LegisRowObject> legislators_senate = new ArrayList<LegisRowObject>();

    private ListAdapter customListAdapter;
    private ListAdapter customListAdapter_house;
    private ListAdapter customListAdapter_senate;
    String json_url = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=1&subchoice=1";
    RequestQueue queue;;

    private LegisRowObject legisSelected;

    private static String TAG = "WebTag";

    ArrayList<String> legis_index = new ArrayList<String>();
    ArrayList<String> legis_index_house = new ArrayList<String>();
    ArrayList<String> legis_index_senate = new ArrayList<String>();





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = getActivity();
        Log.i(TAG, "onCreate: ");

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.legis_fragment, container, false);
        Log.i(TAG, "onCreateView: ");

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("Legislators");

        TabHost tabHost = (TabHost)view.findViewById(R.id.legis_tab);
        tabHost.setup();


        TabHost.TabSpec tab1 = tabHost.newTabSpec("By State");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("House");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Senate");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("By State");
        tab1.setContent(R.id.tab1);

        tab2.setIndicator("House");
        tab2.setContent(R.id.tab2);

        tab3.setIndicator("Senate");
        tab3.setContent(R.id.tab3);

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");


        customListAdapter = new LegisAdapter(getActivity(), legislators);// Pass the food arrary to the constructor.
        final ListView customListView = (ListView) getActivity().findViewById(R.id.legis_list);
        customListView.setAdapter(customListAdapter);

        customListAdapter_house = new LegisAdapter(getActivity(), legislators_house);// Pass the food arrary to the constructor.
        final ListView customListView_house = (ListView) getActivity().findViewById(R.id.legis_list_house);
        customListView_house.setAdapter(customListAdapter_house);

        customListAdapter_senate = new LegisAdapter(getActivity(), legislators_senate);// Pass the food arrary to the constructor.
        final ListView customListView_senate = (ListView) getActivity().findViewById(R.id.legis_list_senate);
        customListView_senate.setAdapter(customListAdapter_senate);

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                legisSelected = (LegisRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), LegisDetails.class);
                intent.putExtra("bioguide_id", legisSelected.getBioguide_id());
                startActivity(intent);

            }
        });

        customListView_house.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                legisSelected = (LegisRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), LegisDetails.class);
                intent.putExtra("bioguide_id", legisSelected.getBioguide_id());
                startActivity(intent);

            }
        });

        customListView_senate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                legisSelected = (LegisRowObject) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), LegisDetails.class);
                intent.putExtra("bioguide_id", legisSelected.getBioguide_id());
                startActivity(intent);

            }
        });





        final View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                String firstLetter = (String) v.getTag();
                int index = 0;
                if (legislators != null) {
                    for (LegisRowObject legisObj : legislators) {
//                        Log.i(TAG, "Legis " + firstLetter);
                        if ((legisObj.getState()).startsWith(firstLetter)) {
                            index = legislators.indexOf(legisObj);
                            break;
                        }
                    }
                }
                customListView.setSelectionFromTop(index, 0);
            }
        };

        final View.OnClickListener listener_house = new View.OnClickListener() {
            public void onClick(View v) {
                String firstLetter = (String) v.getTag();
                int index = 0;
                if (legislators != null) {
                    for (LegisRowObject legisObj : legislators_house) {
//                        Log.i(TAG, "Legis " + firstLetter);
                        if ((legisObj.getLast_name()).startsWith(firstLetter)) {
                            index = legislators_house.indexOf(legisObj);
                            break;
                        }
                    }
                }
                customListView_house.setSelectionFromTop(index, 0);
            }
        };

        final View.OnClickListener listener_senate = new View.OnClickListener() {
            public void onClick(View v) {
                String firstLetter = (String) v.getTag();
                int index = 0;
                if (legislators != null) {
                    for (LegisRowObject legisObj : legislators_senate) {
//                        Log.i(TAG, "Legis " + firstLetter);
                        if ((legisObj.getLast_name()).startsWith(firstLetter)) {
                            index = legislators_senate.indexOf(legisObj);
                            break;
                        }
                    }
                }
                customListView_senate.setSelectionFromTop(index, 0);
            }
        };


//        TextView A = (TextView) getActivity().findViewById(R.id.A);
//        A.setOnClickListener(listener);
//        TextView B = (TextView) getActivity().findViewById(R.id.B);
//        B.setOnClickListener(listener);
//        TextView C = (TextView) getActivity().findViewById(R.id.C);
//        C.setOnClickListener(listener);
//        TextView D = (TextView) getActivity().findViewById(R.id.D);
//        D.setOnClickListener(listener);
//        TextView E = (TextView) getActivity().findViewById(R.id.E);
//        E.setOnClickListener(listener);
//        TextView F = (TextView) getActivity().findViewById(R.id.F);
//        F.setOnClickListener(listener);
//        TextView G = (TextView) getActivity().findViewById(R.id.G);
//        G.setOnClickListener(listener);
//        TextView H = (TextView) getActivity().findViewById(R.id.H);
//        H.setOnClickListener(listener);
//        TextView I = (TextView) getActivity().findViewById(R.id.I);
//        I.setOnClickListener(listener);
//        TextView J = (TextView) getActivity().findViewById(R.id.J);
//        J.setOnClickListener(listener);
//        TextView K = (TextView) getActivity().findViewById(R.id.K);
//        K.setOnClickListener(listener);
//        TextView L = (TextView) getActivity().findViewById(R.id.L);
//        L.setOnClickListener(listener);
//        TextView M = (TextView) getActivity().findViewById(R.id.M);
//        M.setOnClickListener(listener);
//        TextView N = (TextView) getActivity().findViewById(R.id.N);
//        N.setOnClickListener(listener);
//        TextView O = (TextView) getActivity().findViewById(R.id.O);
//        O.setOnClickListener(listener);
//        TextView P = (TextView) getActivity().findViewById(R.id.P);
//        P.setOnClickListener(listener);
//        TextView Q = (TextView) getActivity().findViewById(R.id.Q);
//        Q.setOnClickListener(listener);
//        TextView RLetter = (TextView) getActivity().findViewById(R.id.R);
//        RLetter.setOnClickListener(listener);
//        TextView S = (TextView) getActivity().findViewById(R.id.S);
//        S.setOnClickListener(listener);
//        TextView T = (TextView) getActivity().findViewById(R.id.T);
//        T.setOnClickListener(listener);
//        TextView U = (TextView) getActivity().findViewById(R.id.U);
//        U.setOnClickListener(listener);
//        TextView V = (TextView) getActivity().findViewById(R.id.V);
//        V.setOnClickListener(listener);
//        TextView W = (TextView) getActivity().findViewById(R.id.W);
//        W.setOnClickListener(listener);
//        TextView X = (TextView) getActivity().findViewById(R.id.X);
//        X.setOnClickListener(listener);
//        TextView Y = (TextView) getActivity().findViewById(R.id.Y);
//        Y.setOnClickListener(listener);
//        TextView Z = (TextView) getActivity().findViewById(R.id.Z);
//        Z.setOnClickListener(listener);
//
//        TextView A_house = (TextView) getActivity().findViewById(R.id.A_house);
//        A_house.setOnClickListener(listener_house);
//        TextView B_house = (TextView) getActivity().findViewById(R.id.B_house);
//        B_house.setOnClickListener(listener_house);
//        TextView C_house = (TextView) getActivity().findViewById(R.id.C_house);
//        C_house.setOnClickListener(listener_house);
//        TextView D_house = (TextView) getActivity().findViewById(R.id.D_house);
//        D_house.setOnClickListener(listener_house);
//        TextView E_house = (TextView) getActivity().findViewById(R.id.E_house);
//        E_house.setOnClickListener(listener_house);
//        TextView F_house = (TextView) getActivity().findViewById(R.id.F_house);
//        F_house.setOnClickListener(listener_house);
//        TextView G_house = (TextView) getActivity().findViewById(R.id.G_house);
//        G_house.setOnClickListener(listener_house);
//        TextView H_house = (TextView) getActivity().findViewById(R.id.H_house);
//        H_house.setOnClickListener(listener_house);
//        TextView I_house = (TextView) getActivity().findViewById(R.id.I_house);
//        I_house.setOnClickListener(listener_house);
//        TextView J_house = (TextView) getActivity().findViewById(R.id.J_house);
//        J_house.setOnClickListener(listener_house);
//        TextView K_house = (TextView) getActivity().findViewById(R.id.K_house);
//        K_house.setOnClickListener(listener_house);
//        TextView L_house = (TextView) getActivity().findViewById(R.id.L_house);
//        L_house.setOnClickListener(listener_house);
//        TextView M_house = (TextView) getActivity().findViewById(R.id.M_house);
//        M_house.setOnClickListener(listener_house);
//        TextView N_house = (TextView) getActivity().findViewById(R.id.N_house);
//        N_house.setOnClickListener(listener_house);
//        TextView O_house = (TextView) getActivity().findViewById(R.id.O_house);
//        O_house.setOnClickListener(listener_house);
//        TextView P_house = (TextView) getActivity().findViewById(R.id.P_house);
//        P_house.setOnClickListener(listener_house);
//        TextView Q_house = (TextView) getActivity().findViewById(R.id.Q_house);
//        Q_house.setOnClickListener(listener_house);
//        TextView RLetter_house = (TextView) getActivity().findViewById(R.id.R_house);
//        RLetter_house.setOnClickListener(listener_house);
//        TextView S_house = (TextView) getActivity().findViewById(R.id.S_house);
//        S_house.setOnClickListener(listener_house);
//        TextView T_house = (TextView) getActivity().findViewById(R.id.T_house);
//        T_house.setOnClickListener(listener_house);
//        TextView U_house = (TextView) getActivity().findViewById(R.id.U_house);
//        U_house.setOnClickListener(listener_house);
//        TextView V_house = (TextView) getActivity().findViewById(R.id.V_house);
//        V_house.setOnClickListener(listener_house);
//        TextView W_house = (TextView) getActivity().findViewById(R.id.W_house);
//        W_house.setOnClickListener(listener_house);
//        TextView X_house = (TextView) getActivity().findViewById(R.id.X_house);
//        X_house.setOnClickListener(listener_house);
//        TextView Y_house = (TextView) getActivity().findViewById(R.id.Y_house);
//        Y_house.setOnClickListener(listener_house);
//        TextView Z_house = (TextView) getActivity().findViewById(R.id.Z_house);
//        Z_house.setOnClickListener(listener_house);
//
//        TextView A_senate = (TextView) getActivity().findViewById(R.id.A_senate);
//        A_senate.setOnClickListener(listener_senate);
//        TextView B_senate = (TextView) getActivity().findViewById(R.id.B_senate);
//        B_senate.setOnClickListener(listener_senate);
//        TextView C_senate = (TextView) getActivity().findViewById(R.id.C_senate);
//        C_senate.setOnClickListener(listener_senate);
//        TextView D_senate = (TextView) getActivity().findViewById(R.id.D_senate);
//        D_senate.setOnClickListener(listener_senate);
//        TextView E_senate = (TextView) getActivity().findViewById(R.id.E_senate);
//        E_senate.setOnClickListener(listener_senate);
//        TextView F_senate = (TextView) getActivity().findViewById(R.id.F_senate);
//        F_senate.setOnClickListener(listener_senate);
//        TextView G_senate = (TextView) getActivity().findViewById(R.id.G_senate);
//        G_senate.setOnClickListener(listener_senate);
//        TextView H_senate = (TextView) getActivity().findViewById(R.id.H_senate);
//        H_senate.setOnClickListener(listener_senate);
//        TextView I_senate = (TextView) getActivity().findViewById(R.id.I_senate);
//        I_senate.setOnClickListener(listener_senate);
//        TextView J_senate = (TextView) getActivity().findViewById(R.id.J_senate);
//        J_senate.setOnClickListener(listener_senate);
//        TextView K_senate = (TextView) getActivity().findViewById(R.id.K_senate);
//        K_senate.setOnClickListener(listener_senate);
//        TextView L_senate = (TextView) getActivity().findViewById(R.id.L_senate);
//        L_senate.setOnClickListener(listener_senate);
//        TextView M_senate = (TextView) getActivity().findViewById(R.id.M_senate);
//        M_senate.setOnClickListener(listener_senate);
//        TextView N_senate = (TextView) getActivity().findViewById(R.id.N_senate);
//        N_senate.setOnClickListener(listener_senate);
//        TextView O_senate = (TextView) getActivity().findViewById(R.id.O_senate);
//        O_senate.setOnClickListener(listener_senate);
//        TextView P_senate = (TextView) getActivity().findViewById(R.id.P_senate);
//        P_senate.setOnClickListener(listener_senate);
//        TextView Q_senate = (TextView) getActivity().findViewById(R.id.Q_senate);
//        Q_senate.setOnClickListener(listener_senate);
//        TextView RLetter_senate = (TextView) getActivity().findViewById(R.id.R_senate);
//        RLetter_senate.setOnClickListener(listener_senate);
//        TextView S_senate = (TextView) getActivity().findViewById(R.id.S_senate);
//        S_senate.setOnClickListener(listener_senate);
//        TextView T_senate = (TextView) getActivity().findViewById(R.id.T_senate);
//        T_senate.setOnClickListener(listener_senate);
//        TextView U_senate = (TextView) getActivity().findViewById(R.id.U_senate);
//        U_senate.setOnClickListener(listener_senate);
//        TextView V_senate = (TextView) getActivity().findViewById(R.id.V_senate);
//        V_senate.setOnClickListener(listener_senate);
//        TextView W_senate = (TextView) getActivity().findViewById(R.id.W_senate);
//        W_senate.setOnClickListener(listener_senate);
//        TextView X_senate = (TextView) getActivity().findViewById(R.id.X_senate);
//        X_senate.setOnClickListener(listener_senate);
//        TextView Y_senate = (TextView) getActivity().findViewById(R.id.Y_senate);
//        Y_senate.setOnClickListener(listener_senate);
//        TextView Z_senate = (TextView) getActivity().findViewById(R.id.Z_senate);
//        Z_senate.setOnClickListener(listener_senate);


        if(legislators.size() == 0){
            queue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url, (JSONArray) null,
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

                                    legislators.add(legisRowObject);
                                    if(!legis_index.contains(jsonObject.getString("state").substring(0, 1))){
                                        legis_index.add(jsonObject.getString("state").substring(0, 1));
                                    }

                                    if(jsonObject.getString("chamber").equals("house")) {
                                        if(!legis_index_house.contains(jsonObject.getString("last_name").substring(0, 1))){
                                            legis_index_house.add(jsonObject.getString("last_name").substring(0, 1));
                                        }
                                        legislators_house.add(legisRowObject);
                                    }

                                    if(jsonObject.getString("chamber").equals("senate")) {
                                        if(!legis_index_senate.contains(jsonObject.getString("last_name").substring(0, 1))) {
                                            legis_index_senate.add(jsonObject.getString("last_name").substring(0, 1));
                                        }
                                        legislators_senate.add(legisRowObject);
                                    }

                                    count++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(legislators, new StateSorter());
                            Collections.sort(legislators_house, new LastNameSorter());
                            Collections.sort(legislators_senate, new LastNameSorter());
                            ((BaseAdapter) customListAdapter).notifyDataSetChanged();
                            ((BaseAdapter) customListAdapter_house).notifyDataSetChanged();
                            ((BaseAdapter) customListAdapter_senate).notifyDataSetChanged();

                            Collections.sort(legis_index);
                            Collections.sort(legis_index_house);
                            Collections.sort(legis_index_senate);
                            String index;
                            for(int i = 0; i < legis_index.size(); i ++){
                                TextView index_text = new TextView(getActivity());
                                index_text.setText(legis_index.get(i));
                                index_text.setTag(legis_index.get(i));
                                index_text.setOnClickListener(listener);
                                LinearLayout line = (LinearLayout) getActivity().findViewById(R.id.indexer_legis);
                                line.addView(index_text);
                            }

                            for(int i = 0; i < legis_index_house.size(); i ++){
                                TextView index_text = new TextView(getActivity());
                                index_text.setText(legis_index_house.get(i));
                                index_text.setTag(legis_index_house.get(i));
                                index_text.setOnClickListener(listener_house);
                                LinearLayout line = (LinearLayout) getActivity().findViewById(R.id.indexer_legis_house);
                                line.addView(index_text);
                            }

                            for(int i = 0; i < legis_index_senate.size(); i ++){
                                TextView index_text = new TextView(getActivity());
                                index_text.setText(legis_index_senate.get(i));
                                index_text.setTag(legis_index_senate.get(i));
                                index_text.setOnClickListener(listener_senate);
                                LinearLayout line = (LinearLayout) getActivity().findViewById(R.id.indexer_legis_senate);
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
        else{
            String index;
            for(int i = 0; i < legis_index.size(); i ++){
                TextView index_text = new TextView(getActivity());
                index_text.setText(legis_index.get(i));
                index_text.setTag(legis_index.get(i));
                index_text.setOnClickListener(listener);
                LinearLayout line = (LinearLayout) getActivity().findViewById(R.id.indexer_legis);
                line.addView(index_text);
            }

            for(int i = 0; i < legis_index_house.size(); i ++){
                TextView index_text = new TextView(getActivity());
                index_text.setText(legis_index_house.get(i));
                index_text.setTag(legis_index_house.get(i));
                index_text.setOnClickListener(listener_house);
                LinearLayout line = (LinearLayout) getActivity().findViewById(R.id.indexer_legis_house);
                line.addView(index_text);
            }

            for(int i = 0; i < legis_index_senate.size(); i ++){
                TextView index_text = new TextView(getActivity());
                index_text.setText(legis_index_senate.get(i));
                index_text.setTag(legis_index_senate.get(i));
                index_text.setOnClickListener(listener_senate);
                LinearLayout line = (LinearLayout) getActivity().findViewById(R.id.indexer_legis_senate);
                line.addView(index_text);
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
}
