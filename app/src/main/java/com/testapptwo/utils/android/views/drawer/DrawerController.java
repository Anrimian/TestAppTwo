package com.testapptwo.utils.android.views.drawer;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.testapptwo.R;

/**
 * Created on 31.01.2017.
 */

public class DrawerController {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private View drawerRootView;

    private AppCompatActivity activity;

    public DrawerController(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void bind(View drawerRootView, DrawerLayout drawerLayout) {
        this.drawerRootView = drawerRootView;
        this.drawerLayout = drawerLayout;
        if (drawerLayout == null) {
            return;
        }

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mDrawerToggle = new DrawerController.MyActionBarDrawerToggle(activity,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.post(mDrawerToggle::syncState);
        drawerLayout.addDrawerListener(mDrawerToggle);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isDrawerOpen() {
        return drawerLayout != null
                && drawerLayout.isDrawerOpen(drawerRootView);
    }

    public void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void setDrawerIndicatorEnabled(Boolean enabled) {
        if (mDrawerToggle != null)
            mDrawerToggle.setDrawerIndicatorEnabled(enabled);
    }

    private ActionBar getActionBar() {
        return activity.getSupportActionBar();
    }

    private class MyActionBarDrawerToggle extends ActionBarDrawerToggle {

        public MyActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            activity.supportInvalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            activity.supportInvalidateOptionsMenu();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
