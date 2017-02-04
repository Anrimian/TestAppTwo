package com.testapptwo.features.main;

import android.view.View;
import android.widget.TextView;

import com.testapptwo.R;
import com.testapptwo.api.data.UserInfo;
import com.testapptwo.features.user.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 01.02.2017.
 */

public class DrawerHeaderView {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    public DrawerHeaderView(View view) {
        ButterKnife.bind(this, view);
    }

    public void bind() {
        UserInfo userInfo = UserModel.getInstance().getUserInfo();
        if (userInfo != null) {
            tvUserName.setText(userInfo.getName());
        }
    }
}
