package com.testapptwo.features.main.photos.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.testapptwo.R;
import com.testapptwo.api.data.CommentInfo;
import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.features.main.photos.comments.addcomment.AddCommentDialogFragment;

/**
 * Created on 03.02.2017.
 */

public class CommentsActivity extends AppCompatActivity {

    public static final String IMAGE_INFO = "image_info";
    private static final String ADD_COMMENT_DIALOG = "add_comment_dialog";

    private CommentsListView commentsListView;
    private CommentsController commentsController;
    private CommentsModel commentsModel;

    private ImageInfo imageInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.comments_layout, null);
        setContentView(view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        Intent intent = getIntent();
        imageInfo = (ImageInfo) intent.getSerializableExtra(IMAGE_INFO);

        commentsListView = new CommentsListView(view);

        commentsModel = CommentsModelContainer.getInstance(imageInfo.getId());
        CommentsListAdapter adapter = new CommentsListAdapter(commentsModel.getCommentsList());
        adapter.setOnItemLongClickListener(this::showDeleteConfirmDialog);

        View headerView = View.inflate(this, R.layout.full_image_layout, null);
        FullImageView fullImageView = new FullImageView(headerView);
        fullImageView.bind(imageInfo);
        adapter.addHeader(headerView);

        commentsController = new CommentsController(commentsListView, commentsModel, adapter, this);
        commentsController.bind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentsController.unbind();
    }

    private void showDeleteConfirmDialog(CommentInfo commentInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_comment_confirm);
        builder.setPositiveButton(R.string.delete, (dialog, which) -> commentsModel.deleteComment(commentInfo));
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comments_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.add_comment: {
                AddCommentDialogFragment dialog = AddCommentDialogFragment.newInstance(imageInfo.getId());
                dialog.show(getSupportFragmentManager(), ADD_COMMENT_DIALOG);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
