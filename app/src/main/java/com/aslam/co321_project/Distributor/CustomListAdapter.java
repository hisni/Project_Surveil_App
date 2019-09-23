package com.aslam.co321_project.Distributor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aslam.co321_project.R;

import java.util.ArrayList;
import java.util.List;

class ViewHolder{
    TextView pharmacy;
    TextView driver;
}

class CustomListAdapter extends ArrayAdapter<Work> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

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

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.driver = convertView.findViewById(R.id.rowTextViewDriver);
            viewHolder.pharmacy = convertView.findViewById(R.id.rowTextViewPharmacy);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(getContext(),
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.pharmacy.setText(pharmacy);
        viewHolder.driver.setText(driver);

        return convertView;
    }
}
