package com.testapptwo.features.main.photos.comments.addcomment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapptwo.R;

/**
 * Created on 03.02.2017.
 */

public class AddCommentDialogFragment extends DialogFragment {

    private static final String IMAGE_ID = "image_id";

    public static AddCommentDialogFragment newInstance(int imageId) {
        Bundle args = new Bundle();
        args.putInt(IMAGE_ID, imageId);
        AddCommentDialogFragment fragment = new AddCommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private AddDialogView addDialogView;
    private AddCommentModel addCommentModel;
    private AddCommentController addCommentController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_comment_dialog_view, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle(R.string.add_comment);
        addDialogView = new AddDialogView(view);
        addCommentModel = AddCommentModelContainer.getModel(getImageId(), getChildFragmentManager());
        addCommentController = new AddCommentController(getActivity(), getDialog(), addDialogView, addCommentModel);
        addCommentController.bind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addCommentController.unbind();
    }

    private int getImageId() {
        return getArguments().getInt(IMAGE_ID);
    }
}
