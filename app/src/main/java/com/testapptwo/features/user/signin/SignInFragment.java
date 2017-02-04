package com.testapptwo.features.user.signin;

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

public class SignInFragment extends Fragment {

    private SignInView signInView;
    private SignInModel signInModel;
    private SignInController signInController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.sign_in_fragment_view, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signInView = new SignInView(view);

        signInModel = SignInModelContainer.getModel(getChildFragmentManager());

        signInController = new SignInController(getActivity(), signInView, signInModel);
        signInController.bind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        signInController.unbind();
    }

}
