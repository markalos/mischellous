package com.liuwan.curvechart.widget;

/**
 * Created by mems on 12/16/2017.
 */

public interface Series {
    String getTitle();
    float [] getData();
    int getPos();
    int getSize();
    int getColorId();
}
