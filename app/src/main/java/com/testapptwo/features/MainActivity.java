package com.testapptwo.features;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;
import com.testapptwo.R;
import com.testapptwo.features.main.MainPageFragment;
import com.testapptwo.features.user.UserModel;
import com.testapptwo.features.user.UserStartFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
            replaceFragment(fTrans);
            fTrans.commit();
        }
        initMap();
    }

    private void initMap() {
        try {
            MapView mapView = new MapView(MainActivity.this);
            mapView.onCreate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private void replaceFragment(FragmentTransaction fragmentTransaction) {
        if (UserModel.isLogin()) {
            fragmentTransaction.replace(R.id.main_activity_container, new MainPageFragment(), MainPageFragment.TAG);
        } else {
            fragmentTransaction.replace(R.id.main_activity_container, new UserStartFragment());
        }
    }

    @Override
    public void onBackPressed() {
        final MainPageFragment fragment = (MainPageFragment) getSupportFragmentManager().findFragmentByTag(MainPageFragment.TAG);
        if (fragment != null && fragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
