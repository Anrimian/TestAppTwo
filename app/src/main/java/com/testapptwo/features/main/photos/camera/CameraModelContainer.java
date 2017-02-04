package com.testapptwo.features.main.photos.camera;

/**
 * Created on 29.01.2017.
 */

public class CameraModelContainer {

    private volatile static CameraModel model;

    private CameraModelContainer() {
    }

    public static CameraModel getModel() {
        if (model == null) {
            synchronized (CameraModelContainer.class) {
                if (model == null) {
                    model = new CameraModel();
                }
            }
        }
        return model;
    }
}
