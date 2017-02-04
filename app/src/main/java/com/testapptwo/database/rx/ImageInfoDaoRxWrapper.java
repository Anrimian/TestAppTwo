package com.testapptwo.database.rx;

import com.testapptwo.api.data.ImageInfo;
import com.testapptwo.database.HelperFactory;
import com.testapptwo.features.AppExecutors;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;

/**
 * Created on 18.01.2017.
 */

public class ImageInfoDaoRxWrapper {

    public static Observable<List<ImageInfo>> getImageInfoList() {
        return getImageInfoListObservable().subscribeOn(AppExecutors.DB_SCHEDULER);
    }

    private static Observable<List<ImageInfo>> getImageInfoListObservable() {
        return Observable.create(subscriber -> {
            try {
                List<ImageInfo> imageInfoList = HelperFactory.getHelper().getImageInfoDAO().getImageInfoList();
                subscriber.onNext(imageInfoList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void addImageInfo(List<ImageInfo> imageInfoList) {
        Observable.create(subscriber -> {
            try {
                HelperFactory.getHelper().getImageInfoDAO().addImageInfo(imageInfoList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).subscribeOn(AppExecutors.DB_SCHEDULER).subscribe();
    }

    public static void deleteAll() {
        Observable.create(subscriber -> {
            try {
                HelperFactory.getHelper().getImageInfoDAO().deleteAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).subscribeOn(AppExecutors.DB_SCHEDULER).subscribe();
    }

    public static void deleteImageInfo(ImageInfo imageInfo) {
        Observable.create(subscriber -> {
            try {
                HelperFactory.getHelper().getImageInfoDAO().deleteImageInfo(imageInfo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).subscribeOn(AppExecutors.DB_SCHEDULER).subscribe();
    }
}
