package com.liuwan.curvechart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.liuwan.curvechart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mems on 12/16/2017.
 *
 */

public class CurveChartView extends View {


    List<Series> seriesList;

    // 默认边距
    private int margin = 20;
    // 原点坐标
    private int xPoint;
    private int yPoint;
    private float yOffset;
    // X,Y轴的单位长度
    private int xScale;
    private float yScale;
    private float yLabelScale;
    // 画笔
    private Paint paintAxes;
    private Paint paintCoordinate;
    private Paint paintCurve;

    private final int winSize;
    private final int xStep;
    private String [] xLabel;
    private final boolean autoScale;
    private String[] yLabel;
    private static int numOfYLabel = 12;

    public CurveChartView(Context context, int winSize, int xStep, boolean autoScale) {
        super(context);
        seriesList = new ArrayList<>();
        this.winSize = winSize;
        this.xStep = xStep;
        this.autoScale = autoScale;
        xLabel = new String[winSize / xStep];
        for (int i = 0; i < xLabel.length; ++i) {
            xLabel[i] = String.valueOf(i);
        }
        yLabel = new String[numOfYLabel];
    }

    public boolean addSeries(Series series){
        if (seriesList.size() > 0 && series.getSize() != winSize) {
            return false;
        }
        seriesList.add(series);
        return true;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color1));
        init();
        drawAxesLine(canvas, paintAxes);
        drawCoordinate(canvas, paintCoordinate, seriesList.get(0).getData(),
                seriesList.get(0).getPos());
        for (Series series : seriesList) {
            drawCurve(canvas, paintCurve, series.getData(),
                    series.getPos(), series.getColorId());
        }
    }

    /**
     * 初始化数据值和画笔
     */
    public void init() {
        int marginX = 30;
        xPoint = margin + marginX;
        yPoint = this.getHeight() - margin;
        xScale = (this.getWidth() - 2 * margin - marginX) / (winSize - 1);
        yLabelScale = (this.getHeight() - 2 * margin) / (winSize - 1);

        paintAxes = new Paint();
        paintAxes.setStyle(Paint.Style.STROKE);
        paintAxes.setAntiAlias(true);
        paintAxes.setDither(true);
        paintAxes.setColor(ContextCompat.getColor(getContext(), R.color.color14));
        paintAxes.setStrokeWidth(4);

        paintCoordinate = new Paint();
        paintCoordinate.setStyle(Paint.Style.STROKE);
        paintCoordinate.setDither(true);
        paintCoordinate.setAntiAlias(true);
        paintCoordinate.setColor(ContextCompat.getColor(getContext(), R.color.color14));
        paintCoordinate.setTextSize(15);

        Paint paintTable = new Paint();
        paintTable.setStyle(Paint.Style.STROKE);
        paintTable.setAntiAlias(true);
        paintTable.setDither(true);
        paintTable.setColor(ContextCompat.getColor(getContext(), R.color.color4));
        paintTable.setStrokeWidth(2);

        paintCurve = new Paint();
        paintCurve.setStyle(Paint.Style.STROKE);
        paintCurve.setDither(true);
        paintCurve.setAntiAlias(true);
        paintCurve.setStrokeWidth(3);
        PathEffect pathEffect = new CornerPathEffect(25);
        paintCurve.setPathEffect(pathEffect);

        Paint paintRectF = new Paint();
        paintRectF.setStyle(Paint.Style.FILL);
        paintRectF.setDither(true);
        paintRectF.setAntiAlias(true);
        paintRectF.setStrokeWidth(3);

        Paint paintValue = new Paint();
        paintValue.setStyle(Paint.Style.STROKE);
        paintValue.setAntiAlias(true);
        paintValue.setDither(true);
        paintValue.setColor(ContextCompat.getColor(getContext(), R.color.color1));
        paintValue.setTextAlign(Paint.Align.CENTER);
        paintValue.setTextSize(15);
    }

    /**
     * 绘制坐标轴
     */
    private void drawAxesLine(Canvas canvas, Paint paint) {
        // X
        canvas.drawLine(xPoint, yPoint, this.getWidth() - margin / 6, yPoint, paint);
        canvas.drawLine(this.getWidth() - margin / 6, yPoint, this.getWidth() - margin / 2, yPoint - margin / 3, paint);
        canvas.drawLine(this.getWidth() - margin / 6, yPoint, this.getWidth() - margin / 2, yPoint + margin / 3, paint);

        // Y
        canvas.drawLine(xPoint, yPoint, xPoint, margin / 6, paint);
        canvas.drawLine(xPoint, margin / 6, xPoint - margin / 3, margin / 2, paint);
        canvas.drawLine(xPoint, margin / 6, xPoint + margin / 3, margin / 2, paint);
    }


    private void drawXCoordinate(Canvas canvas, Paint paint) {
        // X轴坐标
        for (int i = 0; i < xLabel.length; i += xStep) {
            paint.setTextAlign(Paint.Align.CENTER);
            int startX = xPoint + i * xScale;
            canvas.drawText(xLabel[i], startX, this.getHeight() - margin / 6, paint);
        }
    }


    private void drawYCoordinate(Canvas canvas, Paint paint, float[] data, int pos) {
        if (autoScale) {
            rescale(data, pos);
        }

        // Y轴坐标
        for (int i = 0; i <= (yLabel.length - 1); i++) {
            paint.setTextAlign(Paint.Align.LEFT);
            int startY = (int) (yPoint - i * yLabelScale);
            int offsetX;
            switch (yLabel[i].length()) {
                case 1:
                    offsetX = 28;
                    break;

                case 2:
                    offsetX = 20;
                    break;

                case 3:
                    offsetX = 12;
                    break;

                case 4:
                    offsetX = 5;
                    break;

                default:
                    offsetX = 0;
                    break;
            }
            int offsetY;
            if (i == 0) {
                offsetY = 0;
            } else {
                offsetY = margin / 5;
            }
            // x默认是字符串的左边在屏幕的位置，y默认是字符串是字符串的baseline在屏幕上的位置
            canvas.drawText(yLabel[i], margin / 4 + offsetX, startY + offsetY, paint);
        }
    }

    private void rescale(float [] data, int pos) {
        float min = data[pos], max = data[pos];
        for(int i = 0; i < winSize; ++i) {
            float d = data[i + pos];
            if (min > d)
                min = d;
            if (max < d)
                max = d;
        }
        float diff = (max - min) / 10;
        if (diff < 0.000001) {
            diff = min / 10;
        }
        float start = min - diff;
        yOffset = start;
        for (int i = 0; i < numOfYLabel; ++i, start += diff) {
            yLabel[i] = String.valueOf(start);
        }
        yScale = (this.getHeight() - 2 * margin) / (numOfYLabel * diff);
    }


    /**
     * 绘制刻度
     */
    private void drawCoordinate(Canvas canvas, Paint paint, float [] data, int pos) {
        drawXCoordinate(canvas, paint);
        drawYCoordinate(canvas, paint, data, pos);

    }

    /**
     * 绘制曲线
     */
    private void drawCurve(Canvas canvas, Paint paint, float[] data, int pos, int color) {
        paint.setColor(ContextCompat.getColor(getContext(), color));
        Path path = new Path();
        for (int i = 0; i < winSize; i++) {
            if (i == 0) {
                path.moveTo(xPoint, toY(data[i + pos]));
            } else {
                path.lineTo(xPoint + i * xScale, toY(data[i + pos]));
            }
        }
        canvas.drawPath(path, paint);
    }


    /**
     * 数据按比例转坐标
     */
    private float toY(float num) {
        float y;
        try {
            y = yPoint - (num - yOffset) * yScale;
        } catch (Exception e) {
            return 0;
        }
        return y;
    }

}
