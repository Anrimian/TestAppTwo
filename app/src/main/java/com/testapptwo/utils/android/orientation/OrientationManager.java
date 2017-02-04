package com.testapptwo.utils.android.orientation;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created on 02.02.2017.
 */

public class OrientationManager extends OrientationEventListener {

    private int deviceDefaultOrientation;
    private int screenOrientation;
    private SimpleOrientationListener listener;

    private Context context;

    public OrientationManager(Context context, int rate) {
        super(context, rate);
        this.context = context;
    }

    public OrientationManager(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void enable() {
        super.enable();
        deviceDefaultOrientation = getDeviceDefaultOrientation();
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation == -1){
            return;
        }
        int newOrientation = 0;
        if (orientation >= 60 && orientation <= 140){
            newOrientation = 90;
        } else if (orientation >= 140 && orientation <= 220) {
            newOrientation = 180;
        } else if (orientation >= 220 && orientation <= 300) {
            newOrientation = 270;
        }

        if (deviceDefaultOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            newOrientation = (newOrientation + 270) % 360;
        }
        if (newOrientation != screenOrientation) {
            Log.d(getClass().getSimpleName(), "newOrientation: " + newOrientation);
            screenOrientation = newOrientation;
            if (listener != null) {
                listener.onOrientationChanged(newOrientation);
            }
        }
    }

    public int getDeviceDefaultOrientation() {
        WindowManager windowManager =  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Configuration config = context.getResources().getConfiguration();
        int rotation = windowManager.getDefaultDisplay().getRotation();

        if ( ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) &&
                config.orientation == Configuration.ORIENTATION_LANDSCAPE)
                || ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) &&
                config.orientation == Configuration.ORIENTATION_PORTRAIT)) {
            return Configuration.ORIENTATION_LANDSCAPE;
        } else {
            return Configuration.ORIENTATION_PORTRAIT;
        }
    }

    public void setOrientationListener(SimpleOrientationListener listener){
        this.listener = listener;
    }

    public int getScreenOrientation(){
        return screenOrientation;
    }
}
