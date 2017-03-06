package com.example.android.webtechlegisstart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillDetails extends AppCompatActivity {
    private static String TAG = "WebTag";
    private RequestQueue queue;
    private String json_url;
    private String bill_title;
    private String bill_type;
    private String bill_sponsor;
    private String bill_chamber;
    private String bill_status;
    private String bill_intro;
    private String bill_url;

    FavTracker tracker;
    ImageButton favIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bill Info");
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b3b3b")));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Intent intent = getIntent();
        final String bill_id = intent.getStringExtra("bill_id");

        tracker = (FavTracker) getApplicationContext();



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();
            }
        });

        queue = Volley.newRequestQueue(this);
        json_url = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=2&subchoice=3&id=" + bill_id;
        Log.i(TAG, "onCreate: " + json_url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url, (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while (count < response.length()) {
                            try {
                                String district;

                                JSONObject jsonObject = response.getJSONObject(count);
                                if (jsonObject.getString("short_title").equals("null")) {
                                    bill_title = jsonObject.getString("official_title");
                                } else {
                                    bill_title = jsonObject.getString("short_title");
                                }
                                bill_type = jsonObject.getString("bill_type");
                                bill_chamber = jsonObject.getString("chamber");
                                bill_intro = jsonObject.getString("introduced_on");
                                bill_sponsor = jsonObject.getJSONObject("sponsor").getString("title") + " " + jsonObject.getJSONObject("sponsor").getString("last_name") + ", " + jsonObject.getJSONObject("sponsor").getString("first_name");
//                                bill_status = jsonObject.getString("");
//                                bill_url = jsonObject.getString("");
                                if(jsonObject.getJSONObject("history").getBoolean("active")){
                                    bill_status = "Active";
                                }
                                else{
                                    bill_status = "New";
                                }
                                if (jsonObject.getJSONObject("urls").has("congress")){
                                    bill_url = jsonObject.getJSONObject("urls").getString("congress");
                                }
                                else{
                                    bill_url = "NA";
                                }

                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            TableLayout bill_table = (TableLayout) findViewById(R.id.bill_table);

                            TextView row_head = new TextView(getApplicationContext());
                            TextView row_content = new TextView(getApplicationContext());
                            TableRow tableRow = new TableRow(getApplicationContext());
                            bill_table.addView(tableRow);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Bill ID:");
                            row_head.setText(bill_id.toUpperCase());
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
                            bill_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Title:");
                            row_head.setText(bill_title);
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
                            bill_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Bill Type:");
                            row_head.setText(bill_type.toUpperCase());
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
                            bill_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Sponsor:");
                            row_head.setText(bill_sponsor);
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
                            bill_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Chamber:");
                            row_head.setText(bill_chamber.substring(0, 1).toUpperCase() + bill_chamber.substring(1));
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
                            bill_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Status:");
                            row_head.setText(bill_status);
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);

                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));

                            //----------------------------

                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                            Date dateIntro = null;
                            try {
                                dateIntro = dt.parse(bill_intro);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd, yyyy");

                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            bill_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Introduced On:");
                            row_head.setText(dt1.format(dateIntro));
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
                            bill_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Congress URL:");
                            row_head.setText(bill_url);
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);

                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));





















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
        if(tracker.containsBill(bill_id)){
            favIcon.setImageResource(R.drawable.favoritesicon2);
        }
        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tracker.containsBill(bill_id)){
                    tracker.removeBill(bill_id);
                    favIcon.setImageResource(R.drawable.favoritesicon);

                }
                else {
                    tracker.insertBill(bill_id);
                    favIcon.setImageResource(R.drawable.favoritesicon2);
                }
            }
        });



    }

}
