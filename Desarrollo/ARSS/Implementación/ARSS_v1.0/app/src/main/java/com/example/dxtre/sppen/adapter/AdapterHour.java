package com.example.dxtre.sppen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dxtre.glam.R;

import java.util.ArrayList;

/**
 * Created by DXtre on 13/07/16.
 */

public class AdapterHour extends BaseAdapter {

    private Context context;
    private ArrayList<String> hours;
    private int selected;

    public AdapterHour(Context context, ArrayList<String> listahours) {
        this.context = context;
        this.hours = listahours;
        selected = -1;
    }
    @Override
    public int getCount() {
        return this.hours.size();
    }

    @Override
    public Object getItem(int position) {
        return this.hours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null	|| !(convertView.getTag() instanceof ViewHolder)) {
            // Create a new view into the list.
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_hour, null);

            holder = new ViewHolder();

            holder.rlHour = (RelativeLayout) convertView.findViewById(R.id.rlHour);
            holder.tvHour = (TextView) convertView.findViewById(R.id.tvHour);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvHour.setText(hours.get(position) + " - " + getNextHour(hours.get(position)));

        if (selected == position) {
            holder.rlHour.setBackgroundResource(R.drawable.background_hour_selected);
            holder.tvHour.setTextColor(Color.RED);
        } else {
            holder.rlHour.setBackgroundResource(R.drawable.background_hour);
            holder.tvHour.setTextColor(Color.WHITE);
        }

        return convertView;
    }

    private String getNextHour(String hour) {

        try {
            String hourNext = hour.substring(0, hour.indexOf(":"));

            int hourNew = Integer.valueOf(hourNext)+1;

            return hourNew+":00";
        } catch (Exception e) {
            return "0:00";
        }
    }

    private class ViewHolder {
        RelativeLayout rlHour;
        TextView tvHour;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}