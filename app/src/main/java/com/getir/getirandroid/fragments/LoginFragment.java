package com.getir.getirandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.activities.MapActivity;
import com.getir.getirandroid.models.User;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.service.AppServices;
import com.getir.getirandroid.utilities.ProgressDialogHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* Created by guray on 20/02/16.*/
public class LoginFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, v);
        prepareView(v);

        return v;
    }

    @OnClick(R.id.loginTV)
    public void onLoginClicked(){
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        ProgressDialogHelper.showCircularProgressDialog(MainActivity.activity);
        AppServices.login(email, password, new AppServices.UserCallback() {
            @Override
            public void onUserReceived(UserSelf user) {
                UserSelf.setInstance(user);
                UserSelf.getInstance().isUserLoggedIn = true;
                UserSelf.Update();

                Intent intent = new Intent(MainActivity.activity, MapActivity.class);
                startActivity(intent);

            }
        });
    }

    EditText emailET, passwordET;
    private void prepareView(View v) {
        emailET = (EditText) v.findViewById(R.id.emailET);
        passwordET = (EditText) v.findViewById(R.id.passwordET);
    }
}
