package com.testapptwo.features.main.photos.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.testapptwo.R;
import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.features.main.photos.comments.CommentsActivity;

/**
 * Created on 31.01.2017.
 */

public class PhotosFragment extends Fragment {

    private PhotosController photosController;
    private PhotoListView photoListView;
    private PhotosModel photosModel;
    private PhotosListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photos_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoListView = new PhotoListView(view);

        photosModel = PhotosModelContainer.getModel(getChildFragmentManager());
        adapter = new PhotosListAdapter(photosModel.getImageInfoList());
        adapter.setOnItemClickListener(this::startCommentsActivity);
        adapter.setOnItemLongClickListener(this::showDeleteConfirmDialog);

        photosController = new PhotosController(photoListView, photosModel, adapter, getActivity());
        photosController.bind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        photosController.unbind();
    }

    private void startCommentsActivity(ImageInfo imageInfo) {
        Intent intent = new Intent(getActivity(), CommentsActivity.class);
        intent.putExtra(CommentsActivity.IMAGE_INFO, imageInfo);
        getActivity().startActivity(intent);
    }

    private void showDeleteConfirmDialog(ImageInfo imageInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_image_confirm);
        builder.setPositiveButton(R.string.delete, (dialog, which) -> {
            Toast.makeText(getContext(), R.string.deleting, Toast.LENGTH_SHORT).show();
            photosModel.deleteImage(imageInfo);
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }
}
