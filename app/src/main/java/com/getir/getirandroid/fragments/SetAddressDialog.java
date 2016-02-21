package com.getir.getirandroid.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.getir.getirandroid.R;

/**
 * Created by guray on 20/02/16.
 */
public class SetAddressDialog extends DialogFragment {

    public interface ClickListener{
        void onClicked(Dialog dialogFragment, String title, String address);
    }

    ClickListener savelistener;

    public void setOnSaveClickListener(final ClickListener listener){
        this.savelistener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.set_address_dialog, container, false);
        prepareView(v);
        return v;
    }


    private void prepareView(View v){
        final EditText addressET            =           (EditText) v.findViewById(R.id.addressET);
        final EditText buildingnoET         =           (EditText) v.findViewById(R.id.buildingnoET);
        final EditText apartmentnoET        =           (EditText) v.findViewById(R.id.apartmentnoET);
        final EditText addressdescriptionET =           (EditText) v.findViewById(R.id.addressdescriptionET);
        final EditText titleET              =           (EditText) v.findViewById(R.id.titleET);
        TextView saveTV                     =           (TextView) v.findViewById(R.id.saveTV);
        TextView cancelTV                   =           (TextView) v.findViewById(R.id.cancelTV);
        saveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(savelistener!=null){
                    String address = addressET.getText().toString().trim()+", "+
                            buildingnoET.getText().toString().trim()+", "+
                            apartmentnoET.getText().toString().trim()+", "+
                            addressdescriptionET.getText().toString().trim();
                    String title = titleET.getText().toString().trim();
                    savelistener.onClicked(getDialog(), title, address);
                }
            }
        });

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        // getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

}
