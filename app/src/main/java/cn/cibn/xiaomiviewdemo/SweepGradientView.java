package cn.cibn.xiaomiviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by Phil on 2016/7/25.
 */


public class SweepGradientView extends View {

    private Paint mPaint = null;

    // 梯度渐变扫描渲染
    private SweepGradient mSweepGradient = null;

    public SweepGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mSweepGradient = new SweepGradient(this.getWidth() / 2, this.getHeight() / 2, new int[]{Color.TRANSPARENT, Color.RED, Color.TRANSPARENT, Color.YELLOW, Color.BLUE}, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setShader(mSweepGradient);

        canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2, 300, mPaint);
    }
}
