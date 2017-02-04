package com.testapptwo.features.main.photos.comments.addcomment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created on 01.02.2017.
 */

public class AddCommentModelContainer extends Fragment {

    private static final String ADD_TAG_MODEL_TAG = "add_tag_model_tag";

    private AddCommentModel model;

    public static AddCommentModel getModel(int imageId, FragmentManager fm) {
        AddCommentModelContainer container = (AddCommentModelContainer) fm.findFragmentByTag(ADD_TAG_MODEL_TAG);
        if (container == null) {
            container = new AddCommentModelContainer();
            fm.beginTransaction().add(container, ADD_TAG_MODEL_TAG).commit();
        }
        if (container.model == null) {
            container.model = new AddCommentModel(imageId);
        }
        return container.model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
