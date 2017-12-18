package com.liuwan.curvechart.widget;

import java.util.Arrays;

/**
 * Created by mems on 12/16/2017.
 */

public class XYSeries implements Series {

    private final int winSize;
    private final int cacheSize;
    private final int colorId;
    private String title;
    private int currentStartIdx, currentStopIdx;
    private final float [] data;
    private final static int DEFAULT_CACHE_RATIO = 10;
    public XYSeries(float [] data, int colorId, int cacheSize, String title) {
        this.winSize = data.length;
        this.cacheSize =  cacheSize;
        this.title = title;
        this.data = new float[cacheSize];
        this.colorId = colorId;
        currentStopIdx = winSize;
        currentStartIdx = 0;

    }

    public XYSeries(float [] data, int colorId) {
        this(data, colorId, data.length * DEFAULT_CACHE_RATIO, "xy series");
    }

    @Override
    public void updateData(final float signal[]) {
        if (null == signal || 0 == signal.length) {
            return ;
        }
        int dataIdx = currentStopIdx, signalIdx = 0;
        if (signal.length >= winSize) { // signal is redundant
            signalIdx = signal.length - winSize;
            dataIdx = 0;
            currentStartIdx = 0;
            currentStopIdx = winSize;
        } else {
            int nextStopIdx = currentStopIdx + signal.length;
            if (nextStopIdx > cacheSize) { //cache is full
                currentStartIdx = 0;
                currentStopIdx = winSize;
                dataIdx = winSize - signal.length;
                for (int i = 0, j = (nextStopIdx - winSize); i < dataIdx; ++i, ++j) {
                    data[i] = data[j];
                }
            } else {
                currentStopIdx = nextStopIdx;
                currentStartIdx = nextStopIdx - winSize;
            }
        }
        while(signalIdx < signal.length) {
            data[dataIdx] = signal[signalIdx];
            ++dataIdx;
            ++signalIdx;
        }
        // rendererRef.setLatestIndex(currentStopIdx);
    }

    @Override
    public String info() {
        return Arrays.toString(Arrays.copyOfRange(data, currentStartIdx, currentStopIdx));
    }

    @Override
    public float[] getData() {
        return data;
    }

    @Override
    public int getPos() {
        return currentStartIdx;
    }

    @Override
    public int getSize() {
        return winSize;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getColorId() {
        return colorId;
    }
}
