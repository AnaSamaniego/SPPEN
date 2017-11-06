package com.example.dxtre.sppen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dxtre.glam.R;
import com.example.dxtre.sppen.model.ImageService;
import com.example.dxtre.sppen.util.ApiHelper;

import java.util.ArrayList;

/**
 * Created by DXtre on 7/10/16.
 */

public class AdapterPagerImage extends PagerAdapter{

    private ArrayList<ImageService> lstImagesService;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public AdapterPagerImage(Context context, ArrayList<ImageService> lstImagesService) {
        this.context = context;
        this.lstImagesService = lstImagesService;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lstImagesService.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_pager_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivItem);

        ApiHelper.displayImagen(context, lstImagesService.get(position).getPhoto(), imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}