package com.testapptwo.features.main.map;

import com.testapptwo.api.data.ImageInfo;

import java.util.List;

/**
 * Created on 17.01.2017.
 */

public interface MapModelObserver {
    void addMarkers(List<ImageInfo> imageInfoList);
}
