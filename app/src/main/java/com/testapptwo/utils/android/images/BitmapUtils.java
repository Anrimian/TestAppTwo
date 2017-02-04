package com.testapptwo.utils.android.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.testapptwo.utils.android.images.exceptions.ConvertationException;

import java.io.ByteArrayOutputStream;

import rx.Observable;

import static android.graphics.BitmapFactory.decodeByteArray;

/**
 * Created on 01.02.2017.
 */

public class BitmapUtils {

    public static Observable<String> toBase64(Bitmap bitmap) {
        return Observable.create(subscriber -> {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            boolean compressResult = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            if (!compressResult) {
                subscriber.onError(new ConvertationException());
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            subscriber.onNext(encoded);
        });
    }

    public static Observable<Bitmap> createBitmapFromBytes(byte[] data, int requiredSize) {
        return Observable.create(subscriber -> {
            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
            decodeOptions.inJustDecodeBounds = true;
            decodeByteArray(data, 0, data.length, decodeOptions);
            int scale = 1;
            while(decodeOptions.outWidth / scale >= requiredSize ||
                    decodeOptions.outHeight / scale >= requiredSize) {
                scale *= 2;
            }

            Log.d(BitmapUtils.class.getSimpleName(), "decodeOptions.outWidth: " + decodeOptions.outWidth);
            Log.d(BitmapUtils.class.getSimpleName(), "decodeOptions.outHeight: " + decodeOptions.outHeight);
            Log.d(BitmapUtils.class.getSimpleName(), "scale: " + scale);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = scale;
            Bitmap bitmap = decodeByteArray(data, 0, data.length, options);
            Log.d(BitmapUtils.class.getSimpleName(), "bitmap.getWidth(): " + bitmap.getWidth());
            Log.d(BitmapUtils.class.getSimpleName(), "bitmap.getHeight(): " + bitmap.getHeight());
            subscriber.onNext(bitmap);
        });
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int rotation) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix rotationMatrix = new Matrix();
        rotationMatrix.postRotate(rotation);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, rotationMatrix, true);
    }

    public static int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }
}
