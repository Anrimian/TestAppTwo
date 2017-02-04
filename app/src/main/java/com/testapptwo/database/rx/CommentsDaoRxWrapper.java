package com.testapptwo.database.rx;

import com.testapptwo.api.data.CommentInfo;
import com.testapptwo.database.HelperFactory;
import com.testapptwo.features.AppExecutors;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;

/**
 * Created on 18.01.2017.
 */

public class CommentsDaoRxWrapper {

    public static Observable<List<CommentInfo>> getCommentsList(int imageId) {
        return getCommentsListObservable(imageId).subscribeOn(AppExecutors.DB_SCHEDULER);
    }

    private static Observable<List<CommentInfo>> getCommentsListObservable(int imageId) {
        return Observable.create(subscriber -> {
            try {
                List<CommentInfo> commentInfoList = HelperFactory.getHelper().getCommentsDAO().getCommentsList(imageId);
                subscriber.onNext(commentInfoList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void addComments(List<CommentInfo> commentsList, int imageId) {
        Observable.create(subscriber -> {
            try {
                HelperFactory.getHelper().getCommentsDAO().addComments(commentsList, imageId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).subscribeOn(AppExecutors.DB_SCHEDULER).subscribe();
    }

    public static void deleteComments(int imageId) {
        Observable.create(subscriber -> {
            try {
                HelperFactory.getHelper().getCommentsDAO().deleteComments(imageId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).subscribeOn(AppExecutors.DB_SCHEDULER).subscribe();
    }

    public static void deleteComment(CommentInfo commentInfo) {
        Observable.create(subscriber -> {
            try {
                HelperFactory.getHelper().getCommentsDAO().deleteComment(commentInfo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).subscribeOn(AppExecutors.DB_SCHEDULER).subscribe();
    }

    public static void addComment(CommentInfo commentInfo, int imageId) {
        Observable.create(subscriber -> {
            try {
                HelperFactory.getHelper().getCommentsDAO().addComment(commentInfo, imageId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).subscribeOn(AppExecutors.DB_SCHEDULER).subscribe();
    }
}
