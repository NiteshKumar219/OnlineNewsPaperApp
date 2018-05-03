package com.mgr.cse.olnp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {

    private String[] urls;
    private Bitmap[] bitmaps;
    private ArrayList<Contact> alldata;
    private Activity context;

    public CustomListView(Activity context, String[] urls, Bitmap[] bitmaps, ArrayList<Contact> alldata) {
        super(context, R.layout.activity_custom_list_view, urls);
        this.context = context;
        this.urls= urls;
        this.bitmaps= bitmaps;
        this.alldata = alldata;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_list_view, null, true);
      TextView textViewt = listViewItem.findViewById(R.id.dtitle);
        TextView textViewc = listViewItem.findViewById(R.id. dcontent);
        ImageView image = listViewItem.findViewById(R.id.imageDownloaded);

      //  textViewURL.setText(urls[position]);
       image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],250,250,false));
       textViewt.setText(alldata.get(position).title);
        textViewc.setText(alldata.get(position).content);
        return  listViewItem;
    }
}