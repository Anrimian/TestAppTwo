package com.testapptwo.features.main.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapptwo.R;

/**
 * Created on 31.01.2017.
 */

public class MapFragment extends Fragment {

    private MapController contactsController;
    private MapView contactsView;
    private MapModel mapModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.map_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactsView = new MapView(view, getChildFragmentManager());
        mapModel = MapModelContainer.getModel(getChildFragmentManager());

        contactsController = new MapController(contactsView, mapModel, getActivity());
        contactsController.bind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contactsController.unbind();
    }
}
