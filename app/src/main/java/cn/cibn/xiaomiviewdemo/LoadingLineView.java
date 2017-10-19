package cn.cibn.xiaomiviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 15210 on 2017/10/19.
 */

public class LoadingLineView extends View {
    private int radius;
    private int mWidth;
    private int mHeight;
    private int loadingLineWidth = dp2px(1);
    private final int topDistance = 100;//外圆距离顶部距离（px）
    private Paint mPaint;
    private SweepGradient mSweepGradient;

    public LoadingLineView(Context context) {
        this(context, null);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.parseColor("#880000FF"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        if (wMode == MeasureSpec.EXACTLY) {
            mWidth = wSize;
        } else {
            mWidth = dp2px(300);
        }
        if (hMode == MeasureSpec.EXACTLY) {
            mHeight = hSize;
        } else {
//            mHeight = dp2px(300);
            mHeight = mWidth / 2 + topDistance * 2;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius = getMeasuredWidth() / 4; //不要在构造方法里初始化，那时还没测量宽高
        canvas.save();
        canvas.translate(mWidth / 2, topDistance + radius);
        drawLoading(canvas);
        canvas.restore();
    }

    private void drawLoading(Canvas canvas) {
        mSweepGradient = new SweepGradient(0, 0, new int[]{Color.TRANSPARENT, Color.WHITE}, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mSweepGradient);
        mPaint.setStrokeWidth(loadingLineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectf0 = new RectF(-radius, -radius, radius, radius);
        canvas.drawOval(rectf0, mPaint);
        RectF rectf1 = new RectF(-radius+13, -radius-1, radius+5, radius-17);
        canvas.drawOval(rectf1, mPaint);
        RectF rectf2 = new RectF(-radius+3, -radius-14, radius+10, radius-10);
        canvas.drawOval(rectf2, mPaint);
        RectF rectf3 = new RectF(-radius+20, -radius-6, radius+15, radius-4);
        canvas.drawOval(rectf3, mPaint);
    }
}
