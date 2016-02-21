package com.getir.getirandroid.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.models.OrderItem;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.service.AppServices;
import com.getir.getirandroid.utilities.AppData;
import com.getir.getirandroid.utilities.Commons;
import com.getir.getirandroid.utilities.ProgressDialogHelper;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* Created by guray on 20/02/16.*/
public class AddToCartFragment extends BaseFragment {

    OrderItem order;

    boolean userHasAddress = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addtocart, container, false);
        ButterKnife.inject(this, v);
        prepareView(v);
        return v;
    }

    @OnClick(R.id.addOrderTV)
    public void onAddOrderClicked(){
        ProgressDialogHelper.showCircularProgressDialog(MainActivity.activity);
        AppServices.addOrder(AppData.getInstance().selectedAddressId, UserSelf.getInstance().id, order.Id, new AppServices.DefaultCallback() {
            @Override
            public void onResponse() {
                new MaterialDialog.Builder(MainActivity.activity)
                        .content(getString(R.string.order_added))
                        .positiveText(getString(R.string.ok))
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                MainActivity.activity.onBackPressed();
                            }
                        })
                        .show();
            }
        });
    }

    TextView menuTV, receiverAddressTV, totalPriceTV;
    private void prepareView(View v) {
        menuTV = (TextView) v.findViewById(R.id.menuTV);
        receiverAddressTV = (TextView) v.findViewById(R.id.receiverAddressTV);
        totalPriceTV = (TextView) v.findViewById(R.id.totalPriceTV);
        menuTV.setText(TextUtils.join(",", order.foodNames));
        if(UserSelf.getInstance().user.address!=null && UserSelf.getInstance().user.address.size()>0){
            userHasAddress = true;
            receiverAddressTV.setText(UserSelf.getInstance().user.address.get(UserSelf.getInstance().user.address.size()-1).description);
        }else{
            userHasAddress = false;
            receiverAddressTV.setText(getString(R.string.please_select_address));
        }
        totalPriceTV.setText(getString(R.string.will_pay)+order.totalPrice+" "+getString(R.string.tl));

        if(!userHasAddress){
            new MaterialDialog.Builder(MainActivity.activity)
                    .content(getString(R.string.please_select_address))
                    .positiveText("ok")
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            MainActivity.activity.onBackPressed();
                        }
                    })
                    .show();
        }
    }

    public static AddToCartFragment newInstance(OrderItem orderItem) {
        AddToCartFragment userDetailFragment = new AddToCartFragment();
        Bundle args = new Bundle();
        args.putString("orderItem", new Gson().toJson(orderItem, OrderItem.class));
        userDetailFragment.setArguments(args);
        return userDetailFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = new Gson().fromJson(getArguments().getString("orderItem"), OrderItem.class);
    }

}
