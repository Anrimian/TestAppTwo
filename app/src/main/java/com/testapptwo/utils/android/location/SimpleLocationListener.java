package com.testapptwo.utils.android.location;

import android.location.Location;

/**
 * Created on 01.02.2017.
 */

public interface SimpleLocationListener {

    void onLocationError(int messageId);

    void onShowLocation(Location lastLocation);
}
