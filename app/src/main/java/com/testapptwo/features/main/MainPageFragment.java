package com.testapptwo.features.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.testapptwo.R;
import com.testapptwo.features.main.photos.list.PhotosFragment;
import com.testapptwo.features.main.map.MapFragment;
import com.testapptwo.utils.android.views.drawer.DrawerController;

/**
 * Created on 30.01.2017.
 */

public class MainPageFragment extends Fragment implements DrawerNavigationView.OnDrawerNavigationListener {

    public static final String TAG = "main_fragment_tag";

    private DrawerController drawerController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.main_page_view, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        if (savedInstanceState == null) {
            Fragment fragment = new PhotosFragment();
            replaceFragment(fragment);
        }

        DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView drawerRootView = (NavigationView) view.findViewById(R.id.left_drawer);
        DrawerHeaderView drawerHeaderView = new DrawerHeaderView(drawerRootView.getHeaderView(0));
        drawerHeaderView.bind();
        DrawerNavigationView drawerNavigationView = new DrawerNavigationView(drawerRootView, drawerLayout);
        drawerNavigationView.setNavigationListener(this);

        drawerController = new DrawerController((AppCompatActivity) getActivity());
        drawerController.bind(drawerRootView, drawerLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!drawerController.isDrawerOpen()) {
                    drawerController.openDrawer();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onBackPressed() {
        if (drawerController.isDrawerOpen()) {
            drawerController.closeDrawer();
            return true;
        }
        return false;
    }

    @Override
    public void navigate(final int itemId) {
        switch (itemId) {
            case R.id.photos: {
                replaceFragment(new PhotosFragment());
                break;
            }
            case R.id.map: {
                replaceFragment(new MapFragment());
                break;
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction()
                .replace(R.id.drawer_content_container, fragment)
                .commit();
    }


}
