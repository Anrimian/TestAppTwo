package com.testapptwo.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.testapptwo.api.data.CommentInfo;

import java.sql.SQLException;
import java.util.List;

import static com.testapptwo.api.data.CommentInfo.IMAGE_ID;

/**
 * Created on 18.01.2017.
 */

public class CommentsDAO extends BaseDaoImpl<CommentInfo, Integer> {

    public CommentsDAO(ConnectionSource connectionSource, Class<CommentInfo> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<CommentInfo> getCommentsList(int imageId) throws SQLException {
        return this.queryForEq(IMAGE_ID, imageId);
    }

    public void addComments(List<CommentInfo> commentInfoList, int imageId) throws SQLException {
        for (CommentInfo commentInfo : commentInfoList) {
            addComment(commentInfo, imageId);
        }
    }

    public void deleteComments(int imageId) throws SQLException {
        deleteBuilder().where().eq(IMAGE_ID, imageId);
    }

    public void deleteComment(CommentInfo commentInfo) throws SQLException {
        delete(commentInfo);
    }

    public void addComment(CommentInfo commentInfo, int imageId) throws SQLException {
        commentInfo.setImageId(imageId);
        createIfNotExists(commentInfo);
    }
}
