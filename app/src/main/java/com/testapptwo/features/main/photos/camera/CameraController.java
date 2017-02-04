package com.testapptwo.features.main.photos.camera;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.TextureView;
import android.widget.Toast;

import com.testapptwo.R;
import com.testapptwo.utils.android.orientation.OrientationManager;

import java.io.IOException;

/**
 * Created on 28.01.2017.
 */


public class CameraController implements ViewActions, CameraModelObserver {

    private CameraModel cameraModel;
    private CameraView cameraView;

    private Activity activity;

    private TextureListener textureListener;
    private Camera camera;

    private OrientationManager orientationManager;

    public CameraController(CameraView cameraView, CameraModel cameraModel, Activity activity) {
        this.cameraView = cameraView;
        this.cameraModel = cameraModel;
        this.activity = activity;
        orientationManager = new OrientationManager(activity);
    }

    public void bind() {
        orientationManager.enable();
        textureListener = new TextureListener();
        cameraView.bind(this, textureListener);
        cameraModel.registerObserver(this);
    }

    public void bindCamera() {
    }

    public void unbindCamera() {
    }

    public void unbind() {
        orientationManager.disable();
        cameraModel.unregisterObserver(this);
    }

    private class TextureListener implements TextureView.SurfaceTextureListener {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.d(getClass().getSimpleName(), "onSurfaceTextureAvailable");
            camera = openCamera();
            if (camera == null) {
                Toast.makeText(activity, R.string.open_camera_error, Toast.LENGTH_LONG).show();
                return;
            }
            try {
                camera.setPreviewTexture(surface);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setUpCameraParams();
            camera.startPreview();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            Log.d(getClass().getSimpleName(), "onSurfaceTextureSizeChanged");

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            Log.d(getClass().getSimpleName(), "onSurfaceTextureDestroyed");
            if (camera != null) {
                camera.stopPreview();
                camera.release();
            }
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            //Log.d(getClass().getSimpleName(), "onSurfaceTextureUpdated");
        }
    }

    @Nullable
    private Camera openCamera() {
        try {
            return Camera.open();
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public void takePicture() {
        if (camera != null) {
            Toast.makeText(activity, "takePicture...", Toast.LENGTH_SHORT).show();
            cameraView.lock();
            camera.takePicture(null, null, (data, cam) -> {
                Toast.makeText(activity, "sending...", Toast.LENGTH_SHORT).show();
                cameraModel.takePicture(data, calculateCurrentDisplayOrientation());
                cameraView.unlock();
                camera.startPreview();
            });
        }
    }

    @Override
    public void onImageSent() {
        Toast.makeText(activity, "onImageSent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSentImageError() {
        Toast.makeText(activity, cameraModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    private void setUpCameraParams() {
        Camera.Parameters parameters = camera.getParameters();
        camera.setDisplayOrientation(getCameraDisplayOrientationForFixedScreen());
        camera.setParameters(parameters);
    }

    private int getCameraDisplayOrientationForFixedScreen() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(getCurrentCameraId(), info);
        int orientation = info.orientation;
        if (orientationManager.getDeviceDefaultOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
            orientation = (orientation + 90) % 360;
        }
        return orientation;
    }

    private int calculateCurrentDisplayOrientation() {
        int degrees = orientationManager.getScreenOrientation();
        int defaultOrientation = getCameraDisplayOrientationForFixedScreen();
        Log.d(getClass().getSimpleName(), "degrees: " + degrees);
        Log.d(getClass().getSimpleName(), "defaultOrientation: " + defaultOrientation);
        int result = (degrees + defaultOrientation) % 360;
        Log.d(getClass().getSimpleName(), "result: " + result);
        return result;
    }

    private int getCurrentCameraId() {
        return 0;
    }
}
