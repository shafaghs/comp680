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
import com.comp680.sunlink.search.SearchStart;
import com.comp680.sunlink.search.SearchStartBgTask;

public class TabFragment3 extends Fragment {
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_3, container, false);
        ctx = getActivity().getApplicationContext();

        ListView result = (ListView) rootView.findViewById(R.id.search_result_list_tab3);

       result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                String keyword = ((TextView) view.findViewById(R.id.keyword)).getText().toString();
                String method = "searchJob";
                View rootView1 = getView();
                       // getView(R.layout.tab_fragment_1);
                SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView1);
                bgTask.execute(method, keyword);}
        });
        return rootView;
    }
}