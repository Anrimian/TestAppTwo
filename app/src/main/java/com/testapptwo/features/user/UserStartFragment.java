package com.testapptwo.features.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapptwo.R;
import com.testapptwo.features.user.signin.SignInFragment;
import com.testapptwo.features.user.signup.SignUpFragment;
import com.testapptwo.utils.android.views.ViewPagerAdapter;

/**
 * Created on 30.01.2017.
 */

public class UserStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.user_start_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new SignInFragment(), getString(R.string.auth));
        pagerAdapter.addFragment(new SignUpFragment(), getString(R.string.register));
        viewPager.setAdapter(pagerAdapter);
    }
}
