package com.testapptwo.features.main.photos.comments;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.testapptwo.R;
import com.testapptwo.api.data.CommentInfo;
import com.testapptwo.utils.android.views.recyclerview.adapter.HeaderFooterRecyclerViewAdapter;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemClickListener;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemLongClickListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 31.01.2017.
 */

public class CommentViewHolder extends HeaderFooterRecyclerViewAdapter.HeaderFooterViewHolder {

    @BindView(R.id.tv_text)
    TextView tvText;

    @BindView(R.id.tv_time)
    TextView tvTime;

    private CommentInfo commentInfo;

    public CommentViewHolder(View itemView,
                             OnItemClickListener<CommentInfo> itemClickListener,
                             OnItemLongClickListener<CommentInfo> longClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(commentInfo);
            }
        });
        itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(commentInfo);
                return true;
            }
            return false;
        });
    }

    public void bind(CommentInfo commentInfo) {
        this.commentInfo = commentInfo;
        bindText();
        bindTime();
    }

    private void bindText() {
        tvText.setText(commentInfo.getText());
    }

    private void bindTime() {
        Date date = new Date(commentInfo.getDate()*1000);
        String formatDate = DateFormat.format("dd.MM.yyyy h:mm a", date).toString();
        tvTime.setText(formatDate);
    }
}
