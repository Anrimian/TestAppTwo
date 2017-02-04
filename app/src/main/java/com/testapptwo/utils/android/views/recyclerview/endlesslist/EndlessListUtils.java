package com.testapptwo.utils.android.views.recyclerview.endlesslist;

import java.util.List;

/**
 * Created on 04.02.2017.
 */

public class EndlessListUtils {

    public static int getNextPage(List contentList, int pageSize) {
        int size = contentList.size();
        int page = size / pageSize;
        int lastPageCount = size % 20;
        if (lastPageCount != 0 && page != 0) {
            page++;
        }
        return page;
    }
}
