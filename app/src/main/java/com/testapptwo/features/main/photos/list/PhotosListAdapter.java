package com.testapptwo.features.main.photos.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapptwo.R;
import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.utils.android.views.recyclerview.adapter.HeaderFooterRecyclerViewAdapter;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemClickListener;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemLongClickListener;

import java.util.List;

/**
 * Created on 31.01.2017.
 */

public class PhotosListAdapter extends HeaderFooterRecyclerViewAdapter<PhotoItemViewHolder> {

    private final List<ImageInfo> imageInfoList;

    private OnItemClickListener<ImageInfo> onItemClickListener;
    private OnItemLongClickListener<ImageInfo> onItemLongClickListener;

    public PhotosListAdapter(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }

    @Override
    public PhotoItemViewHolder createVH(ViewGroup parent, int type) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item_view, parent, false);
        return new PhotoItemViewHolder(itemView, onItemClickListener, onItemLongClickListener);
    }

    @Override
    public void bindVH(PhotoItemViewHolder viewHolder, int position) {
        viewHolder.bind(imageInfoList.get(position));
    }

    @Override
    public int getCount() {
        return imageInfoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener<ImageInfo> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<ImageInfo> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
