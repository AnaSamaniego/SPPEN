package com.example.dxtre.sppen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dxtre.sppen.components.interfaces.OnSubService;
import com.example.dxtre.sppen.model.Item;
import com.example.dxtre.sppen.model.SubService;
import com.example.dxtre.sppen.util.ApiHelper;
import com.example.dxtre.sppen.util.Constants;
import com.example.dxtre.glam.R;

import java.util.ArrayList;

/**
 * Created by DXtre on 8/10/16.
 */

public class AdapterSubService extends BaseAdapter {

    private ArrayList<SubService> lstSubServices;
    private Item itemService;
    private OnSubService onSubService;
    private Context context;

    public AdapterSubService(Context context, ArrayList<SubService> lstSubServices,
                             OnSubService onSubService, Item itemService) {
        this.context = context;
        this.lstSubServices = lstSubServices;
        this.onSubService = onSubService;
        this.itemService = itemService;
    }

    @Override
    public int getCount() {
        return lstSubServices.size();
    }

    @Override
    public Object getItem(int position) {
        return lstSubServices.get(position);
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
            convertView = mInflater.inflate(R.layout.item_sub_service, null);

            holder = new ViewHolder();

            holder.tvCount = (TextView) convertView.findViewById(R.id.tvCount);
            holder.tvService = (TextView) convertView.findViewById(R.id.tvService);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tvDelete);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SubService subService = lstSubServices.get(position);

        if (subService.getCount()>0) {
            holder.tvCount.setText(String.valueOf(subService.getCount()));
            holder.tvCount.setVisibility(View.VISIBLE);
            holder.tvDelete.setVisibility(View.VISIBLE);
        } else {
            holder.tvCount.setVisibility(View.INVISIBLE);
            holder.tvDelete.setVisibility(View.INVISIBLE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subService.getCount()!= Constants.maxServices)
                    onSubService.count(position);
            }
        });

        holder.tvService.setText(subService.getName());

        int serviceColor = Color.parseColor(itemService.getColor());

        ApiHelper.setBackGround(holder.tvService, ApiHelper.createDrawableBackground(serviceColor));

        holder.tvPrice.setText("$"+subService.getCost()+" pesos");

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubService.delete(position);
            }
        });

        ApiHelper.setBackGround(holder.tvDelete, ApiHelper.createDrawableBackground(serviceColor));

        return convertView;
    }

    private class ViewHolder {
        TextView tvCount;
        TextView tvService;
        TextView tvPrice;
        TextView tvDelete;
    }

}