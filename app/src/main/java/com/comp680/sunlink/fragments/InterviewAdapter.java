package com.comp680.sunlink.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.comp680.sunlink.R;

import java.util.ArrayList;
import java.util.List;

class InterviewAdapter extends ArrayAdapter {
    private List list = new ArrayList();
    InterviewAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(InterviewInfo object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ItemHolder itemHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.interview_row_listing_layout,parent,false);
            itemHolder = new ItemHolder();
            itemHolder.interviewTitle = (TextView)row.findViewById(R.id.interview_title);
            itemHolder.interviewDate= (TextView)row.findViewById(R.id.interview_date);
            itemHolder.interviewTime= (TextView)row.findViewById(R.id.interview_time);
            itemHolder.interviewInfo= (TextView)row.findViewById(R.id.interview_info);
            itemHolder.interviewLocation = (TextView)row.findViewById(R.id.interview_location);
            itemHolder.interviewId = (TextView)row.findViewById(R.id.interview_id);
            row.setTag(itemHolder);
        }
        else
        {
            itemHolder =(ItemHolder) row.getTag();
        }
        InterviewInfo itemInfo =(InterviewInfo) this.getItem(position);
        itemHolder.interviewTitle.setText(itemInfo.getInterviewTitle());
        itemHolder.interviewDate.setText(itemInfo.getInterviewDate());
        itemHolder.interviewTime.setText(itemInfo.getInterviewTime());
        itemHolder.interviewLocation.setText(itemInfo.getInterviewLocation());
        itemHolder.interviewInfo.setText(itemInfo.getInterviewInfo());
        itemHolder.interviewId.setText(itemInfo.getInterviewId());
        return row;
    }

    private static class ItemHolder{
        TextView interviewTitle;
        TextView interviewDate;
        TextView interviewLocation;
        TextView interviewInfo;
        TextView interviewId;
        TextView interviewTime;
    }
}