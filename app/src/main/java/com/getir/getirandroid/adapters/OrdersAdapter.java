package com.getir.getirandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.models.OrderItem;

import java.util.ArrayList;

/* Created by guray on 21/02/16.*/
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView foodsTV, priceTV;
        public ViewHolder(View v) {
            super(v);
            foodsTV = (TextView) v.findViewById(R.id.foodsTV);
            priceTV = (TextView) v.findViewById(R.id.priceTV);
        }
    }

    LayoutInflater inflater;
    ArrayList<OrderItem> orderItems;
    public OrdersAdapter(LayoutInflater inflater, ArrayList<OrderItem> orderItems){
        this.orderItems = orderItems;
        this.inflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_order, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderItem orderItem = orderItems.get(position);
        holder.foodsTV.setText(TextUtils.join(", ", orderItem.foodNames));
        holder.priceTV.setText(orderItem.totalPrice+" "+ MainActivity.activity.getString(R.string.tl));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

}
