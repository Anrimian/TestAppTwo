package com.testapptwo.utils.android.views.recyclerview;

/**
 * Created on 09.05.2016.
 */
public class ListPosition {

    private int index;
    private int top;

    public ListPosition(int index, int top) {
        this.index = index;
        this.top = top;
    }

    public int getIndex() {
        return index;
    }

    public int getTop() {
        return top;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("position, ");
        sb.append("index: ");
        sb.append(index);
        sb.append(", top: ");
        sb.append(top);
        return sb.toString();
    }
}
