package com.testapptwo.utils.android.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.testapptwo.utils.IoUtils;
import com.testapptwo.utils.android.file.exceptions.AccessingFileException;
import com.testapptwo.utils.android.file.exceptions.CreatingDirectoryException;
import com.testapptwo.utils.android.file.exceptions.NotMountedSdCardException;
import com.testapptwo.utils.android.file.exceptions.SaveFileException;
import com.testapptwo.utils.android.file.exceptions.WriteFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;

/**
 * Created on 29.01.2017.
 */

public class FileUtils {

    public static Observable<String> storeImage(Bitmap bitmap, String directory) {
        return storeFile(directory,
                imageStorageDir -> File.createTempFile("pic", ".png", imageStorageDir),
                stream -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
    }

    public static Observable<String> storeImage(byte[] data, String directory) {
        return storeFile(directory,
                imageStorageDir -> File.createTempFile("pic", ".png", imageStorageDir),
                stream -> {
                    stream.write(data);
                    return true;
                });
    }

    public static Observable<String> storeFile(String imageDirectory,
                                               FileCreator fileCreator,
                                               FileCompressor fileCompressor) {
        return Observable.create(subscriber -> {

            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                subscriber.onError(new NotMountedSdCardException());
                return;
            }

            File imageStorageDir = new File(imageDirectory);
            if (!imageStorageDir.exists()){
                if (!imageStorageDir.mkdirs()){
                    subscriber.onError(new CreatingDirectoryException());
                    return;
                }
            }

            FileOutputStream fos = null;
            try {
                File pictureFile = fileCreator.createFile(imageStorageDir);
                fos = new FileOutputStream(pictureFile);
                boolean compressedResult = fileCompressor.compress(fos);
                if (!compressedResult) {
                    subscriber.onError(new WriteFileException());
                }
                subscriber.onNext(imageDirectory);
            } catch (FileNotFoundException e) {
                subscriber.onError(e);
            } catch (IOException e) {
                subscriber.onError(new AccessingFileException());
            } catch (Exception e) {
                subscriber.onError(new SaveFileException());
            } finally {
                IoUtils.closeSilently(fos);
            }
        });
    }

    private static Observable<Uri> saveImageInAppCache(byte[] bytes, Context ctx) {
        return saveInCache((stream -> {
                stream.write(bytes);
                return true;
        }), imageStorageDir -> File.createTempFile("pic", ".png", imageStorageDir), ctx);
    }

    private static Observable<Uri> saveInCache(FileCompressor fileCompressor, FileCreator fileCreator, Context ctx) {
        return Observable.create(subscriber -> {
            FileOutputStream stream = null;
            try {
                File cachePath = new File(ctx.getCacheDir(), "images");
                if (!cachePath.exists()){
                    if (!cachePath.mkdirs()) {
                        subscriber.onError(new CreatingDirectoryException());
                        return;
                    }
                }

                File shareFile = fileCreator.createFile(cachePath);
                stream = new FileOutputStream(shareFile);
                boolean compressedResult = fileCompressor.compress(stream);
                if (!compressedResult) {
                    subscriber.onError(new WriteFileException());
                }

                Uri uri = FileProvider.getUriForFile(ctx, "FILEPROVIDERPATH", shareFile);//TODO file provider path
                if (uri == null) {
                    subscriber.onError(new AccessingFileException());
                    return;
                }
                subscriber.onNext(uri);
            } catch (FileNotFoundException e) {
                subscriber.onError(e);
            } catch (IOException e) {
                subscriber.onError(new AccessingFileException());
            } finally {
                IoUtils.closeSilently(stream);
            }
        });
    }
}
