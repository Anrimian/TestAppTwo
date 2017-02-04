package com.testapptwo.utils;

import java.io.Closeable;

/**
 * Created on 04.05.2016.
 */
public class IoUtils {

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }
}
