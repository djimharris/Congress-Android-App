package com.example.android.webtechlegisstart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Application;
import android.widget.ProgressBar;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import java.util.Collections;

public class LegisDetails extends AppCompatActivity {
    private static String TAG = "WebTag";
    private String legis_name_content;
    private String legis_chamber;
    private String legis_email;
    private String legis_contact;
    private String legis_term_start = "";
    private String legis_term_end = "";
    private String legis_office;
    private String legis_state;
    private String legis_fax;
    private String legis_birthday;
    private String legis_facebook;
    private String legis_twitter;
    private String legis_website;
    private String legis_party;

    private RequestQueue queue;
    private String json_url;

    FavTracker tracker;
    ImageButton favIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legis_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Legislator Info");
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b3b3b")));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        legis_name_content = "";

//        MyApplication app = (MyApplication) getApplicationContext();
//        Log.i(TAG, "onCreate: " + app.getData());

        tracker = (FavTracker) getApplicationContext();


        final Intent intent = getIntent();
        final String bioguide_id = intent.getStringExtra("bioguide_id");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
//                Log.i(TAG, "onClick: " + intent.getStringExtra("bioguide_id"));
                finish();
            }
        });




        queue = Volley.newRequestQueue(this);
        json_url = "http://congressapi-1.dba2fmgtwc.us-west-2.elasticbeanstalk.com/congressCallArray.php?choice=1&subchoice=2&id=" + bioguide_id;
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
                                if (jsonObject.getString("district").equals("null")) {
                                    district = "0";
                                } else {
                                    district = jsonObject.getString("district");
                                }

                                legis_name_content = jsonObject.getString("title") + " " + jsonObject.getString("last_name") + ", " + jsonObject.getString("first_name");
                                legis_chamber = jsonObject.getString("chamber");
                                legis_email = jsonObject.getString("oc_email");
                                legis_contact = jsonObject.getString("phone");
                                legis_term_start = jsonObject.getString("term_start");
                                legis_term_end = jsonObject.getString("term_end");
                                legis_office = jsonObject.getString("office");
                                legis_state = jsonObject.getString("state");
                                legis_fax = jsonObject.getString("fax");
                                legis_birthday = jsonObject.getString("birthday");
                                if(jsonObject.has("facebook_id")) {
                                    legis_facebook = jsonObject.getString("facebook_id");
                                }
                                else{
                                    legis_facebook = "null";
                                }
                                if(jsonObject.has("twitter_id")) {
                                    legis_twitter = jsonObject.getString("twitter_id");
                                }
                                else{
                                    legis_twitter = "null";
                                }
                                legis_website = jsonObject.getString("website");
                                legis_party = jsonObject.getString("party");

                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            View.OnClickListener facebooktoastlistener  = new View.OnClickListener() {
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Facebook Account Not Found", Toast.LENGTH_LONG).show();
                                }
                            };

                            View.OnClickListener facebooklistener  = new View.OnClickListener() {
                                public void onClick(View v) {
                                    String url = "https://www.facebook.com/" + legis_facebook;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            };

                            ImageButton facebook = (ImageButton) findViewById(R.id.imageButton4);
                            if(legis_facebook.equals("null")) {
                                facebook.setOnClickListener(facebooktoastlistener);
                            }
                            else{
                                facebook.setOnClickListener(facebooklistener);
                            }

                            View.OnClickListener twittertoastlistener  = new View.OnClickListener() {
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Twitter Account Not Found", Toast.LENGTH_LONG).show();
                                }
                            };

                            View.OnClickListener twitterlistener  = new View.OnClickListener() {
                                public void onClick(View v) {
                                    String url = "https://twitter.com/" + legis_twitter;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            };

                            ImageButton twitter = (ImageButton) findViewById(R.id.imageButton5);
                            if(legis_twitter.equals("null")) {
                                twitter.setOnClickListener(twittertoastlistener);
                            }
                            else{
                                twitter.setOnClickListener(twitterlistener);
                            }

                            View.OnClickListener webtoastlistener  = new View.OnClickListener() {
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Website Not Found", Toast.LENGTH_LONG).show();
                                }
                            };

                            View.OnClickListener weblistener  = new View.OnClickListener() {
                                public void onClick(View v) {
                                    String url = legis_website;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            };

                            ImageButton web = (ImageButton) findViewById(R.id.imageButton6);
                            if(legis_website.equals("null")) {
                                web.setOnClickListener(webtoastlistener);
                            }
                            else{
                                web.setOnClickListener(weblistener);
                            }

                            ImageView party = (ImageView) findViewById(R.id.imageView5);
                            TextView partyText = (TextView) findViewById(R.id.textView3);
                            partyText.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Large);

                            if(legis_party.equals("R")){
                                Picasso.with(getApplicationContext()).load("http://cs-server.usc.edu:45678/hw/hw8/images/r.png").resize(150, 150)
                                        .centerCrop().into(party);
                                partyText.setText("Republican");
                            }
                            if(legis_party.equals("D")){
                                Picasso.with(getApplicationContext()).load("http://cs-server.usc.edu:45678/hw/hw8/images/d.png").resize(150, 150)
                                        .centerCrop().into(party);
                                partyText.setText("Democrat");
                            }
                            if(legis_party.equals("I")){
                                Picasso.with(getApplicationContext()).load("http://independentamericanparty.org/wp-content/themes/v/images/logo-american-heritage-academy.png").resize(150, 150)
                                        .centerCrop().into(party);
                                partyText.setText("Independent");
                            }



                            TableLayout legis_table = (TableLayout) findViewById(R.id.legis_details_table);

                            TextView row_head = new TextView(getApplicationContext());
                            TextView row_content = new TextView(getApplicationContext());
                            TableRow tableRow = new TableRow(getApplicationContext());
                            legis_table.addView(tableRow);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Name:");
                            row_head.setText(legis_name_content);
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
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Email:");
                            row_head.setText(legis_email);
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
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Chamber:");
                            row_head.setText(legis_chamber.substring(0, 1).toUpperCase() + legis_chamber.substring(1));
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
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Contact:");
                            row_head.setText(legis_contact);
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
                            Date dateStart = null;
                            try {
                                dateStart = dt.parse(legis_term_start);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date dateEnd = null;
                            try {
                                dateEnd = dt.parse(legis_term_end);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Date dateBirth = null;
                            try {
                                dateBirth = dt.parse(legis_birthday);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd, yyyy");

                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Start Term:");
                            row_head.setText(dt1.format(dateStart));
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
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("End Term:");
                            row_head.setText(dt1.format(dateEnd));
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);

                            tableRow.addView(row_content);
                            tableRow.addView(row_head);

                            row_content.setTextSize(18);
                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));

                            //----------------------------

                            Date now = new Date();
                            float progress = (float) (now.getTime() - dateStart.getTime()) / (dateEnd.getTime() - dateStart.getTime());
                            Log.i(TAG, "Progress " + progress);
//                            ProgressBar term = new ProgressBar(getApplicationContext(), null, android.R.style.Widget_ProgressBar_Horizontal);
                            ProgressBar term = (ProgressBar) findViewById(R.id.progressBar);
//                            RelativeLayout parent = (RelativeLayout) term.getParent();
//                            parent.removeView(term);

                            row_head = (TextView) findViewById(R.id.progressNum);
                            term.setProgress((int) (progress*100));
                            RelativeLayout progressHolder = (RelativeLayout) findViewById(R.id.progressLayout);
                            ViewGroup parent = (ViewGroup) progressHolder.getParent();
                            parent.removeView(progressHolder);

                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());


                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Term:");
                            row_head.setText((int) (progress*100) + " %");
                            tableRow.setPadding(0, 20, 0, 20);
                            row_head.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                            row_head.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);


                            tableRow.addView(row_content);
                            tableRow.addView(progressHolder);

                            row_content.setTextSize(18);
//                            row_head.setTextSize(18);
                            row_content.setTextColor(Color.parseColor("#000000"));



                            //----------------------------

                            row_head = new TextView(getApplicationContext());
                            row_content = new TextView(getApplicationContext());
                            tableRow = new TableRow(getApplicationContext());
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Office:");
                            row_head.setText(legis_office);
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
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("State:");
                            row_head.setText(legis_state);
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
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Fax:");
                            if (legis_fax.equals("null")){
                                legis_fax = "NA";
                            }
                            row_head.setText(legis_fax);
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
                            legis_table.addView(tableRow);
                            tableRow.setBaselineAligned(false);
//                            row_content.setTextAppearance(getApplicationContext(),android.R.style.TextAppearance_Medium);
                            row_content.setTypeface(null, Typeface.BOLD);
                            row_content.setText("Birthday:");
                            row_head.setText(dt1.format(dateBirth));
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

        ImageView legisImage = (ImageView) findViewById(R.id.imageView4);
        Picasso.with(this).load("https://theunitedstates.io/images/congress/original/" + bioguide_id + ".jpg").resize(300, 300)
                .centerCrop().into(legisImage);

        favIcon = (ImageButton) findViewById(R.id.imageButton7);
        if(tracker.containsBioguide(bioguide_id)){
            favIcon.setImageResource(R.drawable.favoritesicon2);
        }
        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tracker.containsBioguide(bioguide_id)){
                    tracker.removeBioguide(bioguide_id);
                    favIcon.setImageResource(R.drawable.favoritesicon);

                }
                else {
                    tracker.insertBioguide(bioguide_id);
                    favIcon.setImageResource(R.drawable.favoritesicon2);
                }
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
