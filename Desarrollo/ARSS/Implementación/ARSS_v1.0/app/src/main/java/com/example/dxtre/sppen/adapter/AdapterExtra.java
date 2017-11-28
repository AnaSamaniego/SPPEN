package com.example.dxtre.sppen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.dxtre.sppen.components.interfaces.OnExtra;
import com.example.dxtre.sppen.model.Extra;
import com.example.dxtre.sppen.util.Constants;
import com.example.dxtre.glam.R;

import java.util.ArrayList;

/**
 * Created by DXtre on 13/07/16.
 */

public class AdapterExtra extends BaseAdapter {

    private Context context;
    private ArrayList<Extra> lstExtras;
    private OnExtra onExtra;

    public AdapterExtra(Context context, ArrayList<Extra> lstExtras, OnExtra onExtra) {
        this.context = context;
        this.lstExtras = lstExtras;
        this.onExtra = onExtra;
    }
    @Override
    public int getCount() {
        return this.lstExtras.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lstExtras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null	|| !(convertView.getTag() instanceof ViewHolder)) {
            // Create a new view into the list.
            LayoutInflater mInflater = LayoutInflater.from(context);

            convertView = mInflater.inflate(R.layout.item_extra, null);

            holder = new ViewHolder();

            holder.tvExtra = (TextView) convertView.findViewById(R.id.tvExtra);
            holder.btnDecrease = (Button) convertView.findViewById(R.id.btnDecrease);
            holder.btnIncrease = (Button) convertView.findViewById(R.id.btnIncrease);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tvCount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Extra itemExtra = lstExtras.get(position);

        holder.tvExtra.setText(itemExtra.getName());
        holder.tvCount.setText(String.valueOf(itemExtra.getCount()));

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemExtra.getCount()!= Constants.maxServices)
                    onExtra.increase(position);
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemExtra.getCount()!=0)
                    onExtra.decrease(position);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        Button btnDecrease;
        Button btnIncrease;
        TextView tvCount;
        TextView tvExtra;
    }

}