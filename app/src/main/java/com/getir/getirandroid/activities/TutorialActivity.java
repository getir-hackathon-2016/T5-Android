package com.getir.getirandroid.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.getir.getirandroid.R;
import com.getir.getirandroid.adapters.TutorialPagerAdapter;
import com.getir.getirandroid.extensions.PageSelectedVPListener;
import com.orhanobut.hawk.Hawk;

/**
 * Created by guray on 21/02/16.
 */
public class TutorialActivity extends BaseActivity {

    public static LayoutInflater inflater;
    public static TutorialActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        activity = this;
        preapreView();
        setAdapter();
    }

    ViewPager tutorialPager;
    LinearLayout indicatorLL;
    private void preapreView() {
        indicatorLL = (LinearLayout) findViewById(R.id.indicatorLL);
        tutorialPager = (ViewPager) findViewById(R.id.tutorialPager);
        Hawk.put("tutorialSeen", true);
    }

    private void setAdapter() {
        tutorialPager.setAdapter(new TutorialPagerAdapter(getSupportFragmentManager()));
        tutorialPager.addOnPageChangeListener(new PageSelectedVPListener() {
            @Override
            public void onPageSelected(int position) {
                displayIndicator();
            }
        });
        displayIndicator();
    }

    private void displayIndicator(){
        indicatorLL.removeAllViews();
        Integer currentItem = tutorialPager.getCurrentItem() % 4;
        for (Integer i = 0 ; i < 4 ; i++)
        {
            View v = inflater.inflate(R.layout.indicator, indicatorLL, false);
            ImageView indicatorIV = (ImageView) v.findViewById(R.id.indicatorIV);
            ImageView dotIndicatorIV = (ImageView) v.findViewById(R.id.dotIndicatorIV);
            if(i.equals(currentItem)){
                indicatorIV.setVisibility(View.VISIBLE);
                dotIndicatorIV.setVisibility(View.GONE);
            }else{
                indicatorIV.setVisibility(View.GONE);
                dotIndicatorIV.setVisibility(View.VISIBLE);
            }
            indicatorLL.addView(v);
        }
        indicatorLL.setVisibility(View.VISIBLE);
    }
}
