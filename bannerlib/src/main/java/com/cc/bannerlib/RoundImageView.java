package com.cc.bannerlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created on 2019-11-05  14:07
 * Description:
 *
 * @author 644898042@qq.com
 */
public class RoundImageView extends AppCompatImageView {

    private final String TAG = getClass().getSimpleName();

    private final int TOP_LEFT = BannerCorner.TOP_LEFT.getCornerValue();
    private final int TOP_RIGHT = BannerCorner.TOP_RIGHT.getCornerValue();
    private final int BOTTOM_LEFT = BannerCorner.BOTTOM_LEFT.getCornerValue();
    private final int BOTTOM_RIGHT = BannerCorner.BOTTOM_RIGHT.getCornerValue();
    private final int ALL = BannerCorner.ALL.getCornerValue();

    private RectF roundRect;
    private Paint mPaint;
    private Paint mSecondPaint;

    private float radius;
    private int corner;

    private float leftMargin;
    private float rightMargin;

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public void setLeftMargin(float leftMargin) {
        this.leftMargin = leftMargin;
    }

    public void setRightMargin(float rightMargin) {
        this.rightMargin = rightMargin;
    }

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        roundRect = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        roundRect.set(leftMargin, 0, getWidth() - rightMargin, getHeight());
        Log.e(TAG, "RoundImageView onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.saveLayer(roundRect, mPaint);
        } else {
            canvas.saveLayer(roundRect, mPaint, Canvas.ALL_SAVE_FLAG);
        }
        if (corner == ALL) {
            canvas.drawRoundRect(roundRect, radius, radius, mPaint);
        } else {
            float[] roundCorner = new float[8];
            //左上
            if ((corner & TOP_LEFT) == TOP_LEFT) {
                roundCorner[0] = radius;
                roundCorner[1] = radius;
            }
            //右上
            if ((corner & TOP_RIGHT) == TOP_RIGHT) {
                roundCorner[2] = radius;
                roundCorner[3] = radius;
            }
            //左下
            if ((corner & BOTTOM_LEFT) == BOTTOM_LEFT) {
                roundCorner[6] = radius;
                roundCorner[7] = radius;
            }
            //右下
            if ((corner & BOTTOM_RIGHT) == BOTTOM_RIGHT) {
                roundCorner[4] = radius;
                roundCorner[5] = radius;
            }
            Path path = new Path();
            path.addRoundRect(roundRect, roundCorner, Path.Direction.CW);
            canvas.drawPath(path, mPaint);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.saveLayer(roundRect, mSecondPaint);
        } else {
            canvas.saveLayer(roundRect, mSecondPaint, Canvas.ALL_SAVE_FLAG);
        }
        super.onDraw(canvas);
        canvas.restore();
        Log.e(TAG, "RoundImageView onDraw");
    }

}
