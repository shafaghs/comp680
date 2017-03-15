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

import com.comp680.sunlink.EventDetail;
import com.comp680.sunlink.R;

public class HomePageEventListingFragment extends Fragment {
    private Context ctx;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_listing_home_page, container, false);
        // Inflate the layout for this fragment
        ListView listView =(ListView) view.findViewById(R.id.job_listing_fragment);
        ctx = getActivity().getApplicationContext();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                String eventId = ((TextView) view.findViewById(R.id.search_event_id)).getText().toString();
                bundle.putString("eventId", eventId);
                String eventTitle = ((TextView) view.findViewById(R.id.search_event_title)).getText().toString();
                bundle.putString("eventTitle", eventTitle);
                String eventDate= ((TextView) view.findViewById(R.id.event_date)).getText().toString();
                bundle.putString("eventDate", eventDate);
                String eventLocation = ((TextView) view.findViewById(R.id.event_location)).getText().toString();
                bundle.putString("eventLocation", eventLocation);
                Intent intent = new Intent(ctx, EventDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        FragmentBgTask bgTask = new FragmentBgTask(ctx, listView);
        bgTask.execute("eventListing");

        return view;
    }

}
