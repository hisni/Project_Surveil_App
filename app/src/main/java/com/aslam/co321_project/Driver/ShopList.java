package com.aslam.co321_project.Driver;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aslam.co321_project.Delivery;
import com.aslam.co321_project.R;

import java.util.List;

public class ShopList extends ArrayAdapter<Delivery> {
    private Activity context;
    private List<Delivery> shopList;

    public ShopList(Activity context, List<Delivery> shopList){
        super(context, R.layout.list_layout, shopList);
        this.context=context;
        this.shopList=shopList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvName = listViewItem.findViewById(R.id.tvName);
        TextView tvAddress = listViewItem.findViewById(R.id.tvAddress);
        TextView tvPhone = listViewItem.findViewById(R.id.tvPhone);
        TextView tvId = listViewItem.findViewById(R.id.tvId);



        Delivery shop = shopList.get(position);

        tvName.setText(shop.getShopName());
        tvAddress.setText(shop.getShopAddress());
        tvPhone.setText(shop.getShopPhone());
        tvId.setText(shop.getId());

        return listViewItem;
    }
}
