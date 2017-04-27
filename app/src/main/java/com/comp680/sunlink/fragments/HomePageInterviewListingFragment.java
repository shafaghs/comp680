package com.comp680.sunlink.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.comp680.sunlink.R;
import com.comp680.sunlink.search.SearchDetail;

public class HomePageInterviewListingFragment extends Fragment {
    private Context ctx;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interview_listing_home_page, container, false);
        // Inflate the layout for this fragment
        ListView listView =(ListView) view.findViewById(R.id.interview_listing_fragment);
        ctx = getActivity().getApplicationContext();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                String companyId = ((TextView) view.findViewById(R.id.search_company_id)).getText().toString();
                bundle.putString("companyId", companyId);
                String jobId = ((TextView) view.findViewById(R.id.search_job_id)).getText().toString();
                bundle.putString("jobId", jobId);
                String address = ((TextView) view.findViewById(R.id.event_location)).getText().toString();
                bundle.putString("address", address);
                String postedDate = ((TextView) view.findViewById(R.id.search_posted_date)).getText().toString();
                bundle.putString("postedDate", postedDate);
                String companyUrl = ((TextView) view.findViewById(R.id.search_company_url)).getText().toString();
                bundle.putString("companyUrl", companyUrl);
                Intent intent = new Intent(ctx, SearchDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        FragmentBgTask bgTask = new FragmentBgTask(ctx, listView);
        bgTask.execute("interviewListing");

        return view;
    }

}

