package com.testapptwo.features.main.photos.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testapptwo.R;
import com.testapptwo.api.data.CommentInfo;
import com.testapptwo.utils.android.views.recyclerview.adapter.HeaderFooterRecyclerViewAdapter;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemClickListener;
import com.testapptwo.utils.android.views.recyclerview.adapter.OnItemLongClickListener;

import java.util.List;

/**
 * Created on 31.01.2017.
 */

public class CommentsListAdapter extends HeaderFooterRecyclerViewAdapter<CommentViewHolder> {

    private final List<CommentInfo> commentInfoList;

    private OnItemClickListener<CommentInfo> onItemClickListener;
    private OnItemLongClickListener<CommentInfo> onItemLongClickListener;

    public CommentsListAdapter(List<CommentInfo> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }

    @Override
    public CommentViewHolder createVH(ViewGroup parent, int type) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_item, parent, false);
        return new CommentViewHolder(itemView, onItemClickListener, onItemLongClickListener);
    }

    @Override
    public void bindVH(CommentViewHolder viewHolder, int position) {
        viewHolder.bind(commentInfoList.get(position));
    }

    @Override
    public int getCount() {
        return commentInfoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener<CommentInfo> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<CommentInfo> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
