package com.cc.bannerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created on 2019-11-04  11:16
 * Description:
 *
 * @author
 */
public class RoundLayout extends RelativeLayout {

    private final String TAG = getClass().getSimpleName();

    private final int TOP_LEFT = BannerCorner.TOP_LEFT.getCornerValue();
    private final int TOP_RIGHT = BannerCorner.TOP_RIGHT.getCornerValue();
    private final int BOTTOM_LEFT = BannerCorner.BOTTOM_LEFT.getCornerValue();
    private final int BOTTOM_RIGHT = BannerCorner.BOTTOM_RIGHT.getCornerValue();
    private final int ALL = BannerCorner.ALL.getCornerValue();

    private int corner;
    private float radius;

    private RectF roundRect;
    private Paint mPaint;
    private Paint mSecondPaint;

    public RoundLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RoundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLayout);
        radius = typedArray.getDimension(R.styleable.RoundLayout_radius, 0);
        corner = typedArray.getInt(R.styleable.RoundLayout_corner, ALL);
        typedArray.recycle();

        roundRect = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        roundRect.set(0, 0, getWidth(), getHeight());
        Log.e(TAG, "onLayout");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.e(TAG, "draw");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e(TAG, "dispatchDraw");
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
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public void refreshLayout() {
        requestLayout();
    }
}
