package com.comp680.sunlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.comp680.sunlink.search.SearchStart;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import com.comp680.sunlink.fragments.HomePageEventListingFragment;
import com.comp680.sunlink.fragments.HomePageJobListingFragment;
import com.comp680.sunlink.profile.ProfileActivity;

public class HomePage extends AppCompatActivity {
    private Intent currIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);

        currIntent = getIntent();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchStart.class);
                if (currIntent.hasExtra("NewUser")) {
                    intent.putExtra("NewUser","NewUser");
                }
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                if (currIntent.hasExtra("NewUser")) {
                    intent.putExtra("NewUser","NewUser");
                }
                startActivity(intent);
            }
        });


        if (currIntent.hasExtra("NewUser")) {
            Target profile = new ViewTarget(R.id.profile_button, this);
            new ShowcaseView.Builder(this, false)
                    .setTarget(profile)
                    .setContentTitle("Hello")
                    .setContentText("Please complete your profile information to start")
                    .setStyle(1)
                    .build();
        }
    }
}