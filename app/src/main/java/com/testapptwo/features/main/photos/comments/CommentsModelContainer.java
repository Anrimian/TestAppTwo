package com.testapptwo.features.main.photos.comments;

import android.support.v4.util.LruCache;

/**
 * Created on 14.07.2015.
 */
public class CommentsModelContainer {

    private static final int MODEL_CACHE_SIZE = 2;

    private static LruCache<Integer, CommentsModel> cache = new LruCache<>(MODEL_CACHE_SIZE);

    public static CommentsModel getInstance(int imageId) {
        CommentsModel model = cache.get(imageId);
        if (model == null) {
            model = new CommentsModel(imageId);
            cache.put(imageId, model);
        }
        return model;
    }
}
