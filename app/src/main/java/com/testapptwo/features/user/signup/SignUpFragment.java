package com.testapptwo.features.user.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapptwo.R;

/**
 * Created on 30.01.2017.
 */

public class SignUpFragment extends Fragment {

    private SignUpView signUpView;
    private SignUpModel signUpModel;
    private SignUpController signInController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.sign_up_fragment_view, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpView = new SignUpView(view);

        signUpModel = SignUpModelContainer.getModel(getChildFragmentManager());

        signInController = new SignUpController(getActivity(), this.signUpView, signUpModel);
        signInController.bind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        signInController.unbind();
    }
}
