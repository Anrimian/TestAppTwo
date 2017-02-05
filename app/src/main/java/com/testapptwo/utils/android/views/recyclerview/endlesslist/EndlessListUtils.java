package com.testapptwo.utils.android.views.recyclerview.endlesslist;

import java.util.List;

/**
 * Created on 04.02.2017.
 */

public class EndlessListUtils {

    public static int getNextPage(List contentList, int pageSize) {
        int size = contentList.size();
        if (size == 0) {
            return 0;
        }
        int pages = size / pageSize;
        int lastPageSize = size % pageSize;
        if (lastPageSize == 0) {
            return pages;
        }
        return ++pages;
    }
}
