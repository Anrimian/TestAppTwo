package com.testapptwo.features.main.photos.comments;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testapptwo.R;
import com.testapptwo.api.data.ImageInfo;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 03.02.2017.
 */

public class FullImageView {

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.tv_time)
    TextView tvTime;

    private ImageInfo imageInfo;

    public FullImageView(View view) {
        ButterKnife.bind(this, view);
    }

    public void bind(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
        bindTime();
        Glide.with(imageView.getContext())
                .load(imageInfo.getUrl())
                .fitCenter()
                .into(imageView);
    }

    private void bindTime() {
        Date date = new Date(imageInfo.getDate()*1000);
        String formatDate = DateFormat.format("dd.MM.yyyy H:mm", date).toString();
        tvTime.setText(formatDate);
    }
}
