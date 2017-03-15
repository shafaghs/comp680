package com.comp680.sunlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.comp680.sunlink.fragments.HomePageEventListingFragment;
import com.comp680.sunlink.fragments.HomePageJobListingFragment;
import com.comp680.sunlink.profile.ProfileActivity;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        HomePageJobListingFragment jobListing = new HomePageJobListingFragment();
        HomePageEventListingFragment eventListing = new HomePageEventListingFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.home_page_job_listing, jobListing, "Job Listing");
        transaction.add(R.id.home_page_event_listing, eventListing, "Event Listing");
        transaction.commit();

        ImageButton profileButton = (ImageButton) findViewById(R.id.profile_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}