package com.testapptwo.features.user.signin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created on 30.01.2017.
 */

public class SignInModelContainer extends Fragment {

    private static final String SIGN_IN_MODEL_TAG = "sign_in_model_tag";

    private SignInModel model;

    public static SignInModel getModel(FragmentManager fm) {
        SignInModelContainer container = (SignInModelContainer) fm.findFragmentByTag(SIGN_IN_MODEL_TAG);
        if (container == null) {
            container = new SignInModelContainer();
            fm.beginTransaction().add(container, SIGN_IN_MODEL_TAG).commit();
        }
        if (container.model == null) {
            container.model = new SignInModel();
        }
        return container.model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
