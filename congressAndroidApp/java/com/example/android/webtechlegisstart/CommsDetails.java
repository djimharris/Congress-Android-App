package com.example.android.webtechlegisstart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommsDetails extends AppCompatActivity {
    private static String TAG = "WebTag";
    private RequestQueue queue;
    private String json_url;

    private String comm_name;
    private String comm_chamber;
    private String comm_parent;
    private String comm_contact;
    private String comm_office;

    FavTracker tracker;
    ImageButton favIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comms_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Committee Info");
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b3b3b")));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Intent intent = getIntent();
        final String committee_id = intent.getStringExtra("committee_id");

        tracker = (FavTracker) getApplicationContext();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();
            }
        });

        queue = Volley.newRequestQueue(this);
        json_url = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=4&id=" + committee_id;
        Log.i(TAG, "onCreate: " + json_url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url, (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while (count < response.length()) {
                            try {

                                JSONObject jsonObject = response.getJSONObject(count);

                                comm_name = jsonObject.getString("name");
                                comm_chamber = jsonObject.getString("chamber");
//                                comm_office = jsonObject.getString("office");
                                if(jsonObject.has("parent_committee_id")) {
                                    comm_parent = jsonObject.getString("parent_committee_id");
                                }
                                else{
                                    comm_parent = "NA";
                                }
                                if(jsonObject.has("contact")){
                                    comm_contact = jsonObject.getString("");
                                }
                                else{
                                    comm_contact = "NA";
                                }
                                if(jsonObject.has("office")){
                                    comm_office = jsonObject.getString("office");
                                }
                                else{
                                    comm_office = "NA";
                                }


                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            TableLayout comm_table = (TableLayout) findViewById(R.id.comm_table);

                            TextView row_head = new TextView(getApplicationContext());
                            TextView row_content = new TextView(getApplicationContext());
                            TableRow tableRow = new TableRow(getApplicationContext());
                            comm_table.addView(tableRow);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Committee ID:");
                            row_head.setText(committee_id);
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));



                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            comm_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Name:");
                            row_head.setText(comm_name);
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);

                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));


                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            comm_table.addView(tableRow);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Chamber:");
                            row_head.setText(comm_chamber.substring(0, 1).toUpperCase() + comm_chamber.substring(1));
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                            ImageView party = new ImageView(getApplicationContext());
//                            party.setId(10);
                            if(comm_chamber.equals("house")) {
                                Picasso.with(getApplicationContext()).load("http://cs-server.usc.edu:45678/hw/hw8/images/h.png").resize(150, 150)
                                        .centerCrop().into(party);
                            }
                            else{
                                Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.senate);
                                Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 150, true);
                                party.setImageBitmap(bMapScaled);
                            }
                            LinearLayout line = new LinearLayout(getApplicationContext());
                            line.setGravity(Gravity.RIGHT);
                            line.addView(party);
                            line.addView(row_head);
                            tableRow.addView(row_content);
                            tableRow.addView(line);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);

                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            comm_table.addView(tableRow);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Parent Committee:");
                            row_head.setText(comm_parent);
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));


                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            comm_table.addView(tableRow);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Office:");
                            row_head.setText(comm_office);
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);

                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));


                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            comm_table.addView(tableRow);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Contact:");
                            row_head.setText(comm_contact);
                            tableRow.setPadding(0, 20, 0, 20);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));


                            //----------------------------










                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonArrayRequest.setTag(TAG);
        queue.add(jsonArrayRequest);

        favIcon = (ImageButton) findViewById(R.id.fav_button);
        if(tracker.containsComm(committee_id)){
            favIcon.setImageResource(R.drawable.favoritesicon2);
        }
        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tracker.containsComm(committee_id)){
                    tracker.removeComm(committee_id);
                    favIcon.setImageResource(R.drawable.favoritesicon);

                }
                else {
                    tracker.insertComm(committee_id);
                    favIcon.setImageResource(R.drawable.favoritesicon2);
                }
            }
        });


    }

}
