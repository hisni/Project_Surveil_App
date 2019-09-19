package com.aslam.co321_project.Distributor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aslam.co321_project.R;

import java.util.ArrayList;
import java.util.List;

class CustomListAdapter extends ArrayAdapter<Work> {

    private Context mContext;
    private int mResource;

    public CustomListAdapter(Context context, int resource, ArrayList<Work> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the delivery  information
        String pharmacy = getItem(position).getPharmacy();
        String driver = getItem(position).getDriver();
        List<String> boxList = getItem(position).getBoxList();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvPharmacy = convertView.findViewById(R.id.rowTextViewPharmacy);
        TextView tvDriver = convertView.findViewById(R.id.rowTextViewDriver);

        tvDriver.setText(driver);
        tvPharmacy.setText(pharmacy);

        return convertView;
    }
}
