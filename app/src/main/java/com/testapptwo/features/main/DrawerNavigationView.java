package com.testapptwo.features.main;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

/**
 * Created on 31.01.2017.
 */

public class DrawerNavigationView {

    private DrawerLayout drawerLayout;

    private OnDrawerNavigationListener navigationListener;

    public DrawerNavigationView(NavigationView navigationView, @Nullable DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener());
    }

    private class OnNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(final MenuItem menuItem) {
            if (drawerLayout != null) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (navigationListener != null) {
                navigationListener.navigate(menuItem.getItemId());
            }
            return true;
        }
    }

    public void setNavigationListener(OnDrawerNavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public interface OnDrawerNavigationListener {
        void navigate(int itemId);
    }
}
