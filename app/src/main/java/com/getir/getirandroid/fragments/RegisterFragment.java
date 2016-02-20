package com.getir.getirandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.service.AppServices;
import com.getir.getirandroid.utilities.ProgressDialogHelper;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* Created by guray on 20/02/16.*/
public class RegisterFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, v);
        prepareView(v);
        return v;
    }

    @OnClick(R.id.registerTV)
    public void onRegisterClicked(){
        HashMap<String,Object> params = new HashMap<>();
        params.put("name", nameET.getText().toString().trim());
        params.put("surname", surnameET.getText().toString().trim());
        params.put("email", emailET.getText().toString().trim());
        params.put("password", passwordET.getText().toString().trim());

        ProgressDialogHelper.showCircularProgressDialog(getActivity());
        AppServices.register(params, new AppServices.UserCallback() {
            @Override
            public void onUserReceived(UserSelf user) {
                ProgressDialogHelper.dismiss();
                UserSelf.setInstance(user);
                UserSelf.Update();
                gotoLoginFragment();
            }
        });
    }

    private void gotoLoginFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, new LoginFragment())
                .addToBackStack("login")
                .commit();
    }

    @OnClick(R.id.alreadyRegisteredTV)
    public void onAlreadyRegisteredClicked(){
        gotoLoginFragment();
    }

    EditText nameET, surnameET, emailET, passwordET;
    private void prepareView(View v) {
        nameET = (EditText) v.findViewById(R.id.nameET);
        surnameET = (EditText) v.findViewById(R.id.surnameET);
        emailET = (EditText) v.findViewById(R.id.emailET);
        passwordET = (EditText) v.findViewById(R.id.passwordET);
    }
}
