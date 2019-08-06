package com.fyp.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.fyp.ModelsClass.ServiceList;

import java.util.ArrayList;


public class MySpinnerAdapter extends ArrayAdapter<ServiceList> {

    private Context context;
    private ArrayList<ServiceList> myObjs;
    ArrayList<String> spinnerList = new ArrayList<>();

    public MySpinnerAdapter(Context context, int textViewResourceId, ArrayList<ServiceList> myObjs) {
        super(context, textViewResourceId, myObjs);
        this.context = context;
        this.myObjs = myObjs;
    }

    public int getCount() {
        return myObjs.size();
    }

    public ServiceList getItem(int position) {
        return myObjs.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(myObjs.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(20);
        label.setTextColor(context.getResources().getColor(android.R.color.black));
        label.setPadding(10, 0, 0, 10);
        label.setText(myObjs.get(position).getName());
        return label;
    }
}