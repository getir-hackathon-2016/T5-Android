package com.getir.getirandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.getir.getirandroid.R;
import com.getir.getirandroid.models.Address;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.utilities.AppData;

import java.util.ArrayList;

/* Created by guray on 20/02/16.*/
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView addressTV;
        protected ImageView tickIV;
        protected LinearLayout rowLL;
        protected TextView titleTV;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.titleTV);
            rowLL = (LinearLayout) itemView.findViewById(R.id.rowLL);
            tickIV = (ImageView) itemView.findViewById(R.id.tickIV);
            addressTV = (TextView) itemView.findViewById(R.id.addressTV);
        }
    }

    LayoutInflater inflater;
    ArrayList<Address> addresses;
    public AddressAdapter(LayoutInflater inflater, ArrayList<Address> addresses){
        this.addresses = addresses;
        this.inflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_address, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String selectedAddressId =  AppData.getInstance().selectedAddressId;
        final Address address = addresses.get(position);
        if(selectedAddressId!=null && selectedAddressId.equals(address.id)){
            holder.tickIV.setVisibility(View.VISIBLE);
        }else{
            holder.tickIV.setVisibility(View.GONE);
        }

        holder.addressTV.setText(address.description);
        holder.titleTV.setText(address.title);
        holder.rowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppData.setSelectedAddres( address.id);
                UserSelf.Update();
                holder.tickIV.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

}
