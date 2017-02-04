package com.testapptwo.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.testapptwo.api.data.ImageInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created on 18.01.2017.
 */

public class ImageInfoDAO extends BaseDaoImpl<ImageInfo, Integer> {

    public ImageInfoDAO(ConnectionSource connectionSource, Class<ImageInfo> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ImageInfo> getImageInfoList() throws SQLException {
        return this.queryForAll();
    }

    public void addImageInfo(List<ImageInfo> imageInfoList) throws SQLException {
        for (ImageInfo imageInfo : imageInfoList) {
            createIfNotExists(imageInfo);
        }
    }

    public void deleteAll() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ImageInfo.class);
    }

    public void deleteImageInfo(ImageInfo imageInfo) throws SQLException {
        delete(imageInfo);
    }
}
