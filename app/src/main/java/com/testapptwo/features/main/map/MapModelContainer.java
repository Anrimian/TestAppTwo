package com.testapptwo.features.main.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created on 01.02.2017.
 */

public class MapModelContainer extends Fragment {

    private static final String MAP_MODEL_TAG = "map_model_tag";

    private MapModel model;

    public static MapModel getModel(FragmentManager fm) {
        MapModelContainer container = (MapModelContainer) fm.findFragmentByTag(MAP_MODEL_TAG);
        if (container == null) {
            container = new MapModelContainer();
            fm.beginTransaction().add(container, MAP_MODEL_TAG).commit();
        }
        if (container.model == null) {
            container.model = new MapModel();
        }
        return container.model;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
