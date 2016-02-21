package com.getir.getirandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.getir.getirandroid.R;
import com.getir.getirandroid.models.CloseSellers;
import com.getir.getirandroid.models.UserSelf;

public class NearestMenuAdapter extends RecyclerView.Adapter<NearestMenuAdapter.ViewHolder> {

    public interface AdapterItemClickListener{
        void onClicked(UserSelf neigbour);
    }

    public AdapterItemClickListener listener;
    public void setOnItemClickListener(AdapterItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected LinearLayout rowLL;
        protected TextView nameSurnameTV;
        protected TextView priceTV;
        protected TextView menuTV;
        public ViewHolder(View itemView) {
            super(itemView);
            rowLL = (LinearLayout)itemView.findViewById(R.id.rowLL);
            nameSurnameTV = (TextView) itemView.findViewById(R.id.nameSurnameTV);
            priceTV = (TextView) itemView.findViewById(R.id.priceTV);
            menuTV = (TextView) itemView.findViewById(R.id.menuTV);

        }
    }

    LayoutInflater inflater;
    CloseSellers closeSellers;
    public NearestMenuAdapter(LayoutInflater inflater, CloseSellers closeSellers){
        this.inflater = inflater;
        this.closeSellers = closeSellers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_menu, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserSelf neigbour = closeSellers.data.get(position);
        holder.rowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.onClicked(neigbour);
            }
        });

        holder.nameSurnameTV.setText(neigbour.user.name + " " + neigbour.user.surname);
        holder.priceTV.setText(neigbour.activeMenu.totalPrice+" TL");
        if(neigbour.activeMenu!=null && neigbour.activeMenu.foodNames!=null){
            holder.menuTV.setText(TextUtils.join(",",neigbour.activeMenu.foodNames));
        }

    }

    @Override
    public int getItemCount() {
        return closeSellers.data.size();
    }

}