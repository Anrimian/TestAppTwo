package com.testapptwo.utils.android.file;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created on 29.01.2017.
 */

interface FileCompressor {
    boolean compress(FileOutputStream stream) throws IOException;
}
