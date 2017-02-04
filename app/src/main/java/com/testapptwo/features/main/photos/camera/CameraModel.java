package com.testapptwo.features.main.photos.camera;


import android.graphics.Bitmap;
import android.location.Location;
import android.os.Environment;
import android.util.Log;

import com.testapptwo.api.ApiWrapper;
import com.testapptwo.api.ErrorMessages;
import com.testapptwo.api.request.ImageRequestInfo;
import com.testapptwo.features.AppExecutors;
import com.testapptwo.features.user.UserModel;
import com.testapptwo.utils.android.file.FileUtils;
import com.testapptwo.utils.android.images.BitmapUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 29.01.2017.
 */

public class CameraModel {

    private static final int REQUIRED_BITMAP_SIZE = 1280;
    private CameraModelObservable observable = new CameraModelObservable();

    private String errorMessage;

    private double lng;
    private double lat;

    public void registerObserver(CameraModelObserver observer) {
        observable.registerObserver(observer);
    }

    public void unregisterObserver(CameraModelObserver observer) {
        observable.unregisterObserver(observer);
    }

    public void takePicture(byte[] data, int rotation) {
        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        BitmapUtils.createBitmapFromBytes(data, REQUIRED_BITMAP_SIZE)
                .subscribeOn(Schedulers.newThread())
                .map(bitmap -> BitmapUtils.rotateBitmap(bitmap, rotation))
                .flatMap(BitmapUtils::toBase64)
                .map(base64 -> new ImageRequestInfo(base64, currentTimeSeconds, lng, lat))
                .observeOn(AppExecutors.NETWORK_SCHEDULER)
                .flatMap(imageRequestInfo -> ApiWrapper.getApi().postImage(imageRequestInfo, UserModel.getToken()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageResponseInfo -> {
                    Log.d(getClass().getSimpleName(), "response: " + imageResponseInfo);
                    observable.onImageSent();
                }, throwable -> {
                    throwable.printStackTrace();
                    errorMessage = ErrorMessages.selectErrorMessage(throwable);
                    Log.d(getClass().getSimpleName(), "error message: " + errorMessage);
                    observable.onSentImageError();
                });
    }

    private void savePicture(Bitmap bitmap) {
        FileUtils.storeImage(bitmap, getImageDirectory())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(directory -> {
                    observable.onImageSent();
                }, throwable -> {
                    observable.onSentImageError();
                });
    }

    private String getImageDirectory() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        StringBuilder sbImageDirectory = new StringBuilder(extStorageDirectory);
        sbImageDirectory.append("/TestAppTwo");
        return sbImageDirectory.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setLocation(Location location) {
        if (location != null) {
            lng = location.getLongitude();
            lat = location.getLongitude();
        }
    }
}
