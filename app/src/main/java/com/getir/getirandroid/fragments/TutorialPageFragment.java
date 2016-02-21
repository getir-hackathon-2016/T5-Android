package com.getir.getirandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getir.getirandroid.R;
import com.getir.getirandroid.activities.MainActivity;
import com.getir.getirandroid.activities.MapActivity;
import com.getir.getirandroid.activities.SplashActivity;
import com.getir.getirandroid.activities.TutorialActivity;
import com.getir.getirandroid.models.UserSelf;
import com.getir.getirandroid.service.AppServices;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guray on 21/02/16.
 */
public class TutorialPageFragment extends Fragment {

    int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tutorial_page, container, false);
        ButterKnife.inject(this, v);
        prepareView(v);
        preparePageTexts();
        return v;
    }

    @OnClick(R.id.enterTV)
    public void onEnterClicked(){
        if(UserSelf.getInstance()!=null && UserSelf.getInstance().user!=null && UserSelf.getInstance().user.email!=null && UserSelf.getInstance().user.password!=null){
            UserSelf.getInstance().isUserLoggedIn = true;
            AppServices.getUser(UserSelf.getInstance().id, new AppServices.UserCallback() {
                @Override
                public void onUserReceived(UserSelf user) {
                    UserSelf.setInstance(user);
                    UserSelf.getInstance().isUserLoggedIn = true;
                    Intent intent = new Intent(TutorialActivity.activity, MapActivity.class);
                    startActivity(intent);
                    TutorialActivity.activity.finish();
                }
            });
        }else{
            Intent intent = new Intent(TutorialActivity.activity, MainActivity.class);
            intent.putExtra("target","Register");
            startActivity(intent);
            TutorialActivity.activity.finish();
        }
    }

    TextView textTV, enterTV;
    private void prepareView(View v) {
        enterTV = (TextView) v.findViewById(R.id.enterTV);
        textTV = (TextView) v.findViewById(R.id.textTV);
    }

    private void preparePageTexts() {
        switch (page){
            case 0:
                textTV.setText(R.string.tutorial_page1);
                break;
            case 1:
                textTV.setText(R.string.tutorial_page2);
                break;
            case 2:
                textTV.setText(R.string.tutorial_page3);
                break;
            case 3:
                textTV.setText(R.string.tutorial_page4);
                break;
            default:
                textTV.setText(R.string.tutorial_page1);
                break;
        }

        if(page==3){
            enterTV.setVisibility(View.VISIBLE);
        }else{
            enterTV.setVisibility(View.GONE);
        }
    }

    public static TutorialPageFragment newInstance(int page) {
        TutorialPageFragment tutorialPageFragment = new TutorialPageFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        tutorialPageFragment.setArguments(args);
        return tutorialPageFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page");
    }
}
