package com.testapptwo.features.main.photos.camera;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.testapptwo.R;
import com.testapptwo.utils.android.location.SimpleLocationListener;
import com.testapptwo.utils.android.location.SimpleLocationManager;

/**
 * Created on 30.01.2017.
 */

public class CameraActivity extends AppCompatActivity implements SimpleLocationListener {

    private CameraView cameraView;
    private CameraModel cameraModel;
    private CameraController cameraController;

    private SimpleLocationManager simpleLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.camera_view, null);
        setContentView(view);

        cameraView = new CameraView(view);

        cameraModel = CameraModelContainer.getModel();
        cameraController = new CameraController(cameraView, cameraModel, this);
        cameraController.bind();

        simpleLocationManager = new SimpleLocationManager(this, this);
        simpleLocationManager.bind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraController.bindCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraController.unbindCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraController.unbind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SimpleLocationManager.ACCESS_FINE_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                simpleLocationManager.bind();
            }
        }
    }

    @Override
    public void onLocationError(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowLocation(Location lastLocation) {
        cameraModel.setLocation(lastLocation);
    }
}
