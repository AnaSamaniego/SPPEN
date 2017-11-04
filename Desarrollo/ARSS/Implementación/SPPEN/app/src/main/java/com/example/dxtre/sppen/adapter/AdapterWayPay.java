package com.example.dxtre.sppen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dxtre.sppen.model.WayPay;
import com.example.dxtre.glam.R;

import java.util.ArrayList;

/**
 * Created by DXtre on 8/10/16.
 */

public class AdapterWayPay extends BaseAdapter {

    private ArrayList<WayPay> lstWayPays;
    private Context context;
    private int selected;

    public AdapterWayPay(Context context, ArrayList<WayPay> lstWayPays) {
        this.context = context;
        this.lstWayPays = lstWayPays;

        selected = -1;
    }

    @Override
    public int getCount() {
        return lstWayPays.size();
    }

    @Override
    public Object getItem(int position) {
        return lstWayPays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null
                || !(convertView.getTag() instanceof ViewHolder)) {

            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_way_pay, null);

            holder = new ViewHolder();

            holder.rlWayPay = (RelativeLayout) convertView.findViewById(R.id.rlWayPay);
            holder.ivWayPay = (ImageView) convertView.findViewById(R.id.ivWayPay);
            holder.tvWayPay = (TextView) convertView.findViewById(R.id.tvWayPay);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WayPay wayPay = lstWayPays.get(position);

        if (selected == position){
            holder.rlWayPay.setBackgroundResource(R.drawable.background_circle_glam);
        } else {
            holder.rlWayPay.setBackgroundResource(R.color.clr_transparente);
        }

        holder.ivWayPay.setBackgroundResource(wayPay.getIdImage());

        holder.tvWayPay.setText(context.getString(wayPay.getIdName()));

        return convertView;
    }

    private class ViewHolder {
        RelativeLayout rlWayPay;
        ImageView ivWayPay;
        TextView tvWayPay;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}