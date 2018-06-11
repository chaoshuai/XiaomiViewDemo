package cn.cibn.xiaomiviewdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Random;

/**
 * Created by 15210 on 2017/10/17.
 */

public class XiaoMiSportView extends View {
    private Context context;
    private Paint paint;
    private Paint paint_2;
    private Paint paint_3;
    private Paint paint_4;
    private Paint paint_5;
    private int maxNum;
    private int startAngle;
    private int sweepAngle;
    private int radius;
    private int mWidth;
    private int mHeight;
    private int loadingLineWidth = dp2px(1);
    private int roundWidth = dp2px(10);
    private final int topDistance = 100;//外圆距离顶部距离（px）
    private int[] doughnutColors = new int[]{Color.GREEN, Color.YELLOW, Color.RED};
    //    private int[] doughnutColors = new int[]{Color.parseColor("#ffffff"),Color.parseColor("#00ffffff")};
    private Paint mPaint;
    private SweepGradient mSweepGradient;
    int degree;
    private int currentNum = 0;//需设置setter、getter 供属性动画使用
    private int sweepInWidth=dp2px(3);//内圆的宽度
    private int sweepOutWidth = dp2px(3);//外圆的宽度
    private int[] indicatorColor = {0xffffffff,0x00ffffff,0x99ffffff,0xffffffff};
    Random rand = new Random();
    private AnimationThread animationThread;
    private boolean isNeedFresh = true;

    /** 初始圆心位置 X 与 Canvas 宽度之比 **/
    static final float START_CIRCLE_X_SCALE = 0.5f;
    /** 初始圆心位置 Y 与 Canvas 宽度之比 **/
    static final float START_CIRCLE_Y_SCALE = 0.5f;
    private FireworksCircleGraphics fireworksCircleGraphics;

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
        invalidate();
    }

    public void setCurrentNumAnim(int num) {
        float duration = (float) Math.abs(num - currentNum) / maxNum * 1500 + 500; //根据进度差计算动画时间
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "currentNum", num);
        anim.setDuration((long) Math.min(duration, 4000));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
        anim.start();
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        invalidate();
    }

    public XiaoMiSportView(Context context) {
        this(context, null);
    }

    public XiaoMiSportView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiaoMiSportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setBackgroundColor(Color.parseColor("#880000FF"));
        initAttr(attrs);
        initPaint();
        fireworksCircleGraphics = new FireworksCircleGraphics(context);
        animationThread = new AnimationThread();
        animationThread.start();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffffffff);
        paint_2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_4 = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XiaoMiSportView);
        maxNum = array.getInt(R.styleable.XiaoMiSportView_xMaxNum, 0);
        startAngle = array.getInt(R.styleable.XiaoMiSportView_xStartAngle, 0);
        sweepAngle = array.getInt(R.styleable.XiaoMiSportView_xSweepAngle, 360);
        array.recycle();
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius = getMeasuredWidth() / 4; //不要在构造方法里初始化，那时还没测量宽高
        canvas.save();
        canvas.translate(mWidth / 2, topDistance + radius);
//        drawLoading(canvas);

        drawCenterText(canvas);
        if (isNeedFresh) {
            drawfireworks(canvas);//划线和粒子
        } else {
            drawScale(canvas);
            drawIndicator(canvas);
            drawRound(canvas);
        }
//        drawRound(canvas);  //画内外圆
//        drawIndicator(canvas); //画当前进度值
//        drawCenterText(canvas);//画中间的文字
        canvas.restore();
    }

    private void drawfireworks(Canvas canvas) {
//        fireworksCircleGraphics.setRadius(radius);
        fireworksCircleGraphics.draw(canvas);
    }

    private void drawRound(Canvas canvas) {
        canvas.save();
        canvas.rotate(degree, 0, 0);
        paint_5 = new Paint();
        mSweepGradient = new SweepGradient(0, 0, new int[]{Color.WHITE, Color.parseColor("#22ffffff"), Color.parseColor("#22ffffff"), Color.WHITE}, null);
        paint_5.setAntiAlias(true);
        paint_5.setShader(mSweepGradient);
        paint_5.setStrokeWidth(roundWidth);
        paint_5.setStyle(Paint.Style.STROKE);
        radius += 40;
        RectF rectf0 = new RectF(-radius, -radius, radius, radius);
        canvas.drawOval(rectf0, paint_5);
        mSweepGradient = new SweepGradient(0, 0, new int[]{Color.parseColor("#aaffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#aaffffff")}, null);
        paint_5.setShader(mSweepGradient);
        RectF rectf1 = new RectF(-radius, -radius, radius+10, radius);
        canvas.drawOval(rectf1, paint_5);
        mSweepGradient = new SweepGradient(0, 0, new int[]{Color.parseColor("#55ffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#55ffffff")}, null);
        paint_5.setShader(mSweepGradient);
        RectF rectf2 = new RectF(-radius, -radius, radius+20, radius);
        canvas.drawOval(rectf2, paint_5);
        mSweepGradient = new SweepGradient(0, 0, new int[]{Color.parseColor("#11ffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#11ffffff")}, null);
        paint_5.setShader(mSweepGradient);
        RectF rectf3 = new RectF(-radius, -radius, radius+30, radius);
        canvas.drawOval(rectf3, paint_5);
        radius -= 40;
        canvas.restore();
    }

    private void drawIndicator(Canvas canvas) {
        canvas.save();
        paint_2.setStyle(Paint.Style.STROKE);
        int sweep;
        if(currentNum<=maxNum){
            sweep = (int)((float)currentNum/(float)maxNum*sweepAngle);
        }else {
            sweep = sweepAngle;
        }
        paint_2.setStrokeWidth(sweepOutWidth);
        paint_2.setColor(Color.WHITE);
//        Shader shader =new SweepGradient(0,0,indicatorColor,null);
//        paint_2.setShader(shader);
        RectF rectf = new RectF(-radius , -radius , radius , radius);
        if(sweep > 0){
            canvas.drawArc(rectf,startAngle-90,sweep,false,paint_2);
        }
        float x = (float) ((radius)*Math.cos(Math.toRadians(startAngle-90+sweep)));
        float y = (float) ((radius)*Math.sin(Math.toRadians(startAngle-90+sweep)));
        paint_3.setStyle(Paint.Style.FILL);
        paint_3.setColor(0xffffffff);
        paint_3.setMaskFilter(new BlurMaskFilter(dp2px(3), BlurMaskFilter.Blur.SOLID)); //需关闭硬件加速
        canvas.drawCircle(x,y,dp2px(3),paint_3);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        float angle = (float) sweepAngle / 180;//刻度间隔
//        canvas.rotate(startAngle); //将起始刻度点旋转到正上方（270)
        for (int i = 0; i <= 180; i++) {
            paint.setStrokeWidth(dp2px(1));
            canvas.drawLine(0, -radius - sweepInWidth / 2, 0, -radius + sweepInWidth / 2, paint);
            canvas.rotate(angle); //逆时针
        }
        canvas.restore();
    }


    private void drawCenterText(Canvas canvas) {
        canvas.save();
        paint_4.setStyle(Paint.Style.FILL);
        //具体的步数
        paint_4.setTextSize(radius / 2);
        paint_4.setColor(Color.WHITE);
        float c = -paint_4.measureText(currentNum + "") / 2;
        canvas.drawText(currentNum + "", c, 0, paint_4);

        Rect r = new Rect();
        //数字步
        paint_4.setTextSize(radius / 8);
        canvas.drawText("步", paint_4.measureText(currentNum + "") * 2, 0, paint_4);

//       消耗的文字显示
        paint_4.setTextSize(radius / 6);
        String content2 = SportUtils.getKilometresByStepNumber(0, 1.75, currentNum) + "米 | " + SportUtils.getCalorieBySex(0, 64, currentNum) + "千卡";
        paint_4.getTextBounds(content2, 0, content2.length(), r);
        canvas.drawText(content2, -paint_4.measureText(content2 + "") / 2, r.height() + radius / 4, paint_4);
        canvas.restore();
    }

    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 360);

    {
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE && animationThread.isInterrupted()) {
            animationThread.start();
        } else {
            animationThread.interrupt();
        }
    }



    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

//    private void drawLoading(Canvas canvas) {
//        canvas.save();
//        mSweepGradient = new SweepGradient(0, 0, new int[]{Color.TRANSPARENT, Color.WHITE}, null);
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setShader(mSweepGradient);
//        mPaint.setStrokeWidth(loadingLineWidth);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.rotate(degree, 0, 0);
//        Log.d("degree",degree+"");
//        radius -= 20;
//        RectF rectf0 = new RectF(-radius, -radius, radius, radius);
//        canvas.drawOval(rectf0, mPaint);
//        RectF rectf1 = new RectF(-radius + 13, -radius - 1, radius + 5, radius - 17);
//        canvas.drawOval(rectf1, mPaint);
//        RectF rectf2 = new RectF(-radius + 3, -radius - 14, radius + 10, radius - 10);
//        canvas.drawOval(rectf2, mPaint);
//        RectF rectf3 = new RectF(-radius + 20, -radius - 6, radius + 15, radius - 4);
//        canvas.drawOval(rectf3, mPaint);
//
////        paint_3.setStyle(Paint.Style.FILL);
////        paint_3.setColor(0xffffffff);
////        paint_3.setMaskFilter(new BlurMaskFilter(dp2px(3), BlurMaskFilter.Blur.SOLID)); //需关闭硬件加速
////        canvas.drawCircle(radius+5, 0, dp2px(4), paint_3);
////        for (int i=0;i<5;i++) {
////            canvas.drawCircle(radius, -30*i, dp2px(2), paint_3);
////        }
//        radius += 20;
//        canvas.restore();
//    }

    class AnimationThread extends Thread{
        int count=0;
        @Override
        public void run() {
            while(true) {
                fireworksCircleGraphics.next();
                try {
                    // 尽可能 17ms 更新一次
                    sleep(17);
                    Log.d("XiaoMi",""+count++);
                } catch (InterruptedException ignore) {
                }
            }
        }
    }

    public void setIsNeedFresh(boolean isNeedFresh){
        this.isNeedFresh = isNeedFresh;
    }
}
