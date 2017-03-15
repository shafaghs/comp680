package com.comp680.sunlink.search;

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

import com.comp680.sunlink.R;

public class SearchDetail extends AppCompatActivity {
    private TextView jobTitle, companyName, positionType, companyAdd, postedDate, jobDes, jobDuties, essentialSkills, desiredSkills;
    private Button saveJob, applyJob;
    String jobId, address, method, differenceDate, companyUrl;
    private View rootView;
    private Context ctx;
    private String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        jobTitle = (TextView) findViewById(R.id.search_detail_job_title);
        companyName = (TextView) findViewById(R.id.search_detail_company_name);
        positionType = (TextView) findViewById(R.id.search_detail_position_type);
        companyAdd = (TextView) findViewById(R.id.search_detail_company_add);
        postedDate = (TextView) findViewById(R.id.search_detail_posted_date);
        jobDes = (TextView) findViewById(R.id.search_detail_job_des_detail);
        jobDuties = (TextView) findViewById(R.id.search_detail_job_duty_detail);
        essentialSkills = (TextView) findViewById(R.id.search_detail_ess_detail);
        desiredSkills = (TextView) findViewById(R.id.search_detail_des_detail);

        saveJob = (Button) findViewById(R.id.search_detail_save_button);
        applyJob = (Button) findViewById(R.id.search_detail_apply_button);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String userId = pref.getString("user_id", "");

        jobId = getIntent().getExtras().getString("jobId");
        address = getIntent().getExtras().getString("address");
        differenceDate = getIntent().getExtras().getString("postedDate");
        companyId = getIntent().getExtras().getString("companyId");
        companyUrl = getIntent().getExtras().getString("companyUrl");

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        saveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userId.isEmpty() && !jobId.isEmpty()) {
                    if (saveJob.getText().toString().equals(getString(R.string.save_to_favorite))) {
                        method = "saveJob";
                        SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
                        bgTask.execute(method, jobId, userId);
                    }
                    if (saveJob.getText().toString().equals(getString(R.string.unsave))) {
                        method = "unSaveJob";
                        SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
                        bgTask.execute(method, jobId, userId);
                    }
                }
            }
        });

        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!companyUrl.isEmpty()) {
                    Uri uri = Uri.parse(companyUrl); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        if (!jobId.isEmpty() && !userId.isEmpty()) {
            method = "showJobDetail";
            SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
            bgTask.execute(method, jobId, userId, address, differenceDate);
        }

    }

}
