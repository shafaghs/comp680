package com.comp680.sunlink.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.comp680.sunlink.R;

import java.util.ArrayList;
import java.util.List;

class ProfileInfoAdapter extends ArrayAdapter {
    //Variables:
    private List list = new ArrayList();

    ProfileInfoAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(ProfileInfo object) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ProfileInfoAdapter.ItemHolder itemHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.profile_paragraph_layout,parent,false);
            itemHolder = new ProfileInfoAdapter.ItemHolder();
            itemHolder.title = (TextView)row.findViewById(R.id.header_profile_fragment);
            itemHolder.subtitle= (TextView)row.findViewById(R.id.sub_profile_fragment);
            row.setTag(itemHolder);
        }
        else
        {
            itemHolder =(ProfileInfoAdapter.ItemHolder) row.getTag();
        }
        ProfileInfo itemInfo =(ProfileInfo) this.getItem(position);
        itemHolder.title.setText(itemInfo.getHeader());
        itemHolder.subtitle.setText(itemInfo.getInfo());
                return row;
    }

    private static class ItemHolder {
        TextView title, subtitle;
    }
}

