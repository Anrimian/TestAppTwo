package com.testapptwo.features.main.photos.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created on 01.02.2017.
 */

public class PhotosModelContainer extends Fragment {

    private static final String PHOTOS_MODEL_TAG = "photos_model_tag";

    private PhotosModel model;

    public static PhotosModel getModel(FragmentManager fm) {
        PhotosModelContainer container = (PhotosModelContainer) fm.findFragmentByTag(PHOTOS_MODEL_TAG);
        if (container == null) {
            container = new PhotosModelContainer();
            fm.beginTransaction().add(container, PHOTOS_MODEL_TAG).commit();
        }
        if (container.model == null) {
            container.model = new PhotosModel();
        }
        return container.model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
