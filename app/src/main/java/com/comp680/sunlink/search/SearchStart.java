package com.comp680.sunlink.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.comp680.sunlink.R;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.WHITE;


public class SearchStart extends AppCompatActivity {

    private static String userId;
    private static Context ctx;
    private SearchView searchView;
    private String searchKey;
    private View rootView;
    private String method;
    private SharedPreferences pref;
    private static final String SEARCH_KEY = "searchKey";
    private static final String SEARCH_JOB = "searchJob";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_start2);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = pref.getString("user_id", "");
        searchKey = pref.getString(SEARCH_KEY,"");

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Result"));
        tabLayout.addTab(tabLayout.newTab().setText("Saved Jobs"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorHeight(10);
        tabLayout.setTabTextColors(BLUE,WHITE);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        if(viewPager.getCurrentItem()==0 && !"".equals(searchKey)){
            method = SEARCH_JOB;
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    searchKey = pref.getString(SEARCH_KEY,"");
                    if(!"".equals(searchKey)){
                        method = SEARCH_JOB;
                        SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                        bgTask.execute(method, searchKey);
                    }
                }
                if(tab.getPosition()==1){
                    method = "showSavedJob";
                    SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                    bgTask.execute(method, userId);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //this method is empty because I implemented it's signiture as part of OnTabSelectedListener
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //this method is empty because I implemented it's signiture as part of OnTabSelectedListener

            }
        });

        searchView = (SearchView) findViewById(R.id.search_start_searchView);
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {return false;}

            @Override
            public boolean onQueryTextSubmit(String query) {
                viewPager.setCurrentItem(0);
                method = SEARCH_JOB;
                searchKey = searchView.getQuery().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(SEARCH_KEY,searchKey);
                editor.apply();
                SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                bgTask.execute(method, searchKey);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchKey = pref.getString(SEARCH_KEY,"");
        if(!"".equals(searchKey) && !"".equals(userId)){
            method = SEARCH_JOB;
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        searchKey = pref.getString(SEARCH_KEY,"");
        if(!searchKey.isEmpty() && !"".equals(searchKey)){
            method = SEARCH_JOB;
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchKey = pref.getString(SEARCH_KEY,"");
        if (!searchKey.isEmpty() && !"".equals(searchKey)){
            method = SEARCH_JOB;
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
    }
}