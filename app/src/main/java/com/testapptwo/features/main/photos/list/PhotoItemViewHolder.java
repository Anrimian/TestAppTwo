package com.testapptwo.features.main.photos.list;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testapptwo.R;
import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.utils.android.views.recyclerview.adapter.HeaderFooterRecyclerViewAdapter;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemClickListener;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemLongClickListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 31.01.2017.
 */

public class PhotoItemViewHolder extends HeaderFooterRecyclerViewAdapter.HeaderFooterViewHolder {

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.tv_time)
    TextView tvTime;

    private ImageInfo imageInfo;

    public PhotoItemViewHolder(View itemView,
                               OnItemClickListener<ImageInfo> itemClickListener,
                               OnItemLongClickListener<ImageInfo> longClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(imageInfo);
            }
        });
        itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(imageInfo);
                return true;
            }
            return false;
        });
    }

    public void bind(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
        bindTime();
        Glide.with(imageView.getContext())
                .load(imageInfo.getUrl())
                .into(imageView);
    }

    private void bindTime() {
        Date date = new Date(imageInfo.getDate()*1000);
        String formatDate = DateFormat.format("dd.MM.yyyy", date).toString();
        tvTime.setText(formatDate);
    }
}
