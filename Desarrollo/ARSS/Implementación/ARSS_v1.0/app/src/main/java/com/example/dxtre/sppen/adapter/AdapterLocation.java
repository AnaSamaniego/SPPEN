package com.example.dxtre.sppen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dxtre.sppen.model.LocationService;
import com.example.dxtre.glam.R;

import java.util.ArrayList;

/**
 * Created by DXtre on 15/07/16.
 */

public class AdapterLocation extends BaseAdapter {

    private Context context;
    private ArrayList<LocationService> lstLocationServices;
    private int selected;

    public AdapterLocation(Context context, ArrayList<LocationService> lstLocationServices) {
        this.context = context;
        this.lstLocationServices = lstLocationServices;
        selected = -1;
    }
    @Override
    public int getCount() {
        return this.lstLocationServices.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lstLocationServices.get(position);
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
            convertView = mInflater.inflate(R.layout.item_location, null);

            holder = new ViewHolder();

            holder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
            holder.ivLocation = (ImageView) convertView.findViewById(R.id.ivLocation);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LocationService itemCurrent = this.lstLocationServices.get(position);

        if(position == selected){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorGlamourApp));
            holder.tvLocation.setTextColor(Color.WHITE);
            holder.ivLocation.setBackgroundResource(R.drawable.ic_location_item_selected);
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
            holder.tvLocation.setTextColor(Color.BLACK);
            holder.ivLocation.setBackgroundResource(R.drawable.ic_location_item);
        }

        holder.tvLocation.setText(itemCurrent.getLocationName().toUpperCase());

        return convertView;
    }

    private class ViewHolder {
        TextView tvLocation;
        ImageView ivLocation;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}