package com.adrian.simpleandroidframe.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.adrian.basemodule.util.DensityUtils;
import com.adrian.simpleandroidframe.R;

/**
 * Created by qing on 2018/3/27 0027.
 */

public class RadarView extends View {

    private int count = 5;  //几边形
    private int layerCount = 4; //层数
    private float angle;    //每条边对应的圆心角
    private int centerX, centerY;   //圆心坐标x,y
    private float radius;   //半径
    private Paint polygonPaint; //边框paint
    private Paint linePaint;    //连线paint
    private Paint txtPaint; //文字paint
    private Paint circlePaint;  //圆点paint
    private Paint reginColorPaint;   //覆盖区域paint
    private double[] percents = {.91, .35, .12, .8, .5};    //覆盖区域百分比
    private String[] titles = {"王者荣耀", "楚留香", "镇魔曲", "吃鸡", "lol"};  //文字

    public RadarView(Context context) {
        this(context, null, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //计算圆心角
        angle = (float) (Math.PI * 2 / count);

        polygonPaint = new Paint();
        polygonPaint.setColor(ContextCompat.getColor(context, R.color.radarPolygonColor));
        polygonPaint.setAntiAlias(true);
        polygonPaint.setStyle(Paint.Style.STROKE);
        polygonPaint.setStrokeWidth(4f);

        linePaint = new Paint();
        linePaint.setColor(ContextCompat.getColor(context, R.color.radarLineColor));
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2f);

        txtPaint = new Paint();
        txtPaint.setColor(ContextCompat.getColor(context, R.color.radarTxtColor));
        txtPaint.setAntiAlias(true);
        txtPaint.setStyle(Paint.Style.STROKE);
        txtPaint.setTextSize(DensityUtils.sp2px(context, 12));

        circlePaint = new Paint();
        circlePaint.setColor(ContextCompat.getColor(context, R.color.radarCircleColor));
        circlePaint.setAntiAlias(true);

        reginColorPaint = new Paint();
        reginColorPaint.setColor(ContextCompat.getColor(context, R.color.radarRegionColor));
        reginColorPaint.setStyle(Paint.Style.FILL);
        reginColorPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2 * .7f;
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
    }

    public void setCount(int count) {
        this.count = count;
        angle = (float) (Math.PI * 2 / count);
        invalidate();
    }

    public void setLayerCount(int layerCount) {
        this.layerCount = layerCount;
        invalidate();
    }

    /**
     * 绘制五边形
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / layerCount;
        for (int i = 1; i <= layerCount; i++) {
            //当前所在层的半径
            float curR = r * i;
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    //每层第一个坐标点
                    path.moveTo(centerX, centerY - curR);
                } else {
                    //顺时针记录其余顶点的坐标
                    float x = (float) (centerX + Math.sin(angle * j) * curR);
                    float y = (float) (centerY - Math.cos(angle * j) * curR);
                    path.lineTo(x, y);
                }
            }

            //最外层的顶角外面的五个小圆点
            if (i == layerCount) {
                for (int j = 0; j < count; j++) {
                    float x = (float) (centerX + Math.sin(angle * j) * (curR + 12));
                    float y = (float) (centerY - Math.cos(angle * j) * (curR + 12));
                    canvas.drawCircle(x, y, 4, circlePaint);
                }
            }
            path.close();
            canvas.drawPath(path, polygonPaint);
        }
    }

    /**
     * 绘制连线
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        float r = radius / layerCount;
        for (int i = 0; i < count; i++) {
            //起始坐标.从中心开始的话，startX = centerX, startY = centerY;
            float startX = (float) (centerX + Math.sin(angle * i) * r);
            float startY = (float) (centerY - Math.cos(angle * i) * r);

            //末端坐标
            float endX = (float) (centerX + Math.sin(angle * i) * radius);
            float endY = (float) (centerY - Math.cos(angle * i) * radius);

            canvas.drawLine(startX, startY, endX, endY, linePaint);
        }
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < count; i++) {

        }
    }
}
