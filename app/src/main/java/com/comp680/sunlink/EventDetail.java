package com.comp680.sunlink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EventDetail extends AppCompatActivity {
    private TextView eventTitle;
    private TextView eventLocation;
    private TextView eventDate;
    private TextView eventTime;
    private TextView eventInfo;
    private Button saveEvent;
    private Button gotoWebsite;
    String jobId, address,method, differenceDate;
    private View rootView;
    private Context ctx;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        eventId = getIntent().getExtras().getString("eventId");
        String title = getIntent().getExtras().getString("eventTitle");
        String date = getIntent().getExtras().getString("eventDate");
        String[] newD =splitDate(date);
        String location = getIntent().getExtras().getString("eventLocation");

        eventTitle = (TextView) findViewById(R.id.search_detail_job_title);
        eventTitle.setText(title);
        eventLocation = (TextView) findViewById(R.id.search_detail_company_name);
        eventLocation.setText(location);
        eventDate = (TextView) findViewById(R.id.search_detail_position_type);
        eventDate.setText(newD[0]);
        eventTime = (TextView) findViewById(R.id.search_detail_company_add);
        eventTime.setText("At: " + newD[1]);
        eventInfo= (TextView) findViewById(R.id.search_detail_job_description);
        saveEvent = (Button) findViewById(R.id.search_detail_save_button);
        gotoWebsite = (Button) findViewById(R.id.search_detail_apply_button);
        gotoWebsite.setText("More Info");
        gotoWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openURL = new Intent(Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("http://www.csun.edu/engineering-computer-science/techfest-information"));
                startActivity(openURL);
            }
        });

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String userId = pref.getString("user_id","");

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userId.isEmpty()){
                    // comment
                }
            }
        });

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }

    public String[] splitDate(String date) {
        String[] parts = date.split(" ");
        return parts;
    }
}

