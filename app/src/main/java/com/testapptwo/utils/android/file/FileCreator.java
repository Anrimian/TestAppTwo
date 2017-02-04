package com.testapptwo.utils.android.file;

/**
 * Created on 29.01.2017.
 */

import java.io.File;
import java.io.IOException;

interface FileCreator {
    File createFile(File imageStorageDir) throws IOException;
}
