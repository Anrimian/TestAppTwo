package com.testapptwo.features.user.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created on 30.01.2017.
 */

public class SignUpModelContainer extends Fragment {

    private static final String SIGN_UP_MODEL_TAG = "sign_up_model_tag";

    private SignUpModel model;

    public static SignUpModel getModel(FragmentManager fm) {
        SignUpModelContainer container = (SignUpModelContainer) fm.findFragmentByTag(SIGN_UP_MODEL_TAG);
        if (container == null) {
            container = new SignUpModelContainer();
            fm.beginTransaction().add(container, SIGN_UP_MODEL_TAG).commit();
        }
        if (container.model == null) {
            container.model = new SignUpModel();
        }
        return container.model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
