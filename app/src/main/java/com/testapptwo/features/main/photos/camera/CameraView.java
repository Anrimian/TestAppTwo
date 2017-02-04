package com.testapptwo.features.main.photos.camera;

import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.testapptwo.R;

/**
 * Created on 28.01.2017.
 */

public class CameraView {

    private ViewActions viewActions;

    private Button btnTakePicture;
    private TextureView textureView;

    public CameraView(View view) {
        textureView = (TextureView) view.findViewById(R.id.texture_view);
        btnTakePicture = (Button) view.findViewById(R.id.btn_take_picture);
        btnTakePicture.setOnClickListener(v -> viewActions.takePicture());
    }

    public void bind(ViewActions viewActions, TextureView.SurfaceTextureListener textureListener) {
        this.viewActions = viewActions;
        textureView.setSurfaceTextureListener(textureListener);
    }

    public void lock() {
        btnTakePicture.setEnabled(false);
        btnTakePicture.setClickable(false);
    }

    public void unlock() {
        btnTakePicture.setEnabled(true);
        btnTakePicture.setClickable(true);
    }
}
