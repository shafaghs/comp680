package com.comp680.sunlink.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.comp680.sunlink.R;
import java.util.ArrayList;

class ItemAdapterKeyword extends ArrayAdapter {
    private ArrayList list = new ArrayList();

    ItemAdapterKeyword(Context context, int resource) {
        super(context, resource);
    }

    public void add(ItemInfoKeyword object) {
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
        ItemHolder1 itemHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.keyword_row_layout,parent,false);
            itemHolder = new ItemHolder1();
            itemHolder.keyword = (TextView)row.findViewById(R.id.keyword);
            row.setTag(itemHolder);
        }
        else
        {
            itemHolder =(ItemHolder1) row.getTag();
        }
        ItemInfoKeyword itemInfo =(ItemInfoKeyword) this.getItem(position);
        itemHolder.keyword.setText(itemInfo.getKeyword());
        return row;
    }

    private static class ItemHolder1{
        TextView keyword;
    }
}
