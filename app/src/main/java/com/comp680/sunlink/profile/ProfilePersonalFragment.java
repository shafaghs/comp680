package com.comp680.sunlink.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.comp680.sunlink.R;


public class ProfilePersonalFragment extends Fragment {
    protected UserPersonal userCurr;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_listing_home_page, container, false);
        // Inflate the layout for this fragment
        ListView listView =(ListView) view.findViewById(R.id.job_listing_fragment);
        Context ctx = getActivity().getApplicationContext();

        ProfileBgTask bgTask = new ProfileBgTask(ctx, listView);
        bgTask.execute("personalFragment");
        userCurr = bgTask.getUserPersonal();
        return view;
    }

    public UserPersonal getUserCurr(){
        return this.userCurr;
    }
}
