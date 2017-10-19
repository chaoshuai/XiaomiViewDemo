package cn.cibn.xiaomiviewdemo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by chaoshuai on 2016/11/17.
 * chaoshuai
 * 仿小米手环圆盘view
 */

public class RoundIndicatorView extends View {

    private Paint paint;
    private Paint paint_2;
    private Paint paint_3;
    private Paint paint_4;
    private Context context;
    private int maxNum;
    private int startAngle;
    private int sweepAngle;
    private int radius;
    private int mWidth;
    private int mHeight;
    private int sweepInWidth;//内圆的宽度
    private int sweepOutWidth;//外圆的宽度
    private int currentNum = 0;//需设置setter、getter 供属性动画使用
    private final int topDistance = 100;//外圆距离顶部距离（px）
    private int[] indicatorColor = {0xffc79b70, 0x00c79b70, 0x99c79b70, 0xffc79b70};

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
        invalidate();
    }

    public RoundIndicatorView(Context context) {
        this(context, null);
    }

    public RoundIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundIndicatorView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setBackgroundColor(0xFFEEEBE5);
        initAttr(attrs);
        initPaint();
    }

    public void setCurrentNumAnim(int num) {
        float duration = (float) Math.abs(num - currentNum) / maxNum * 1500 + 500; //根据进度差计算动画时间
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "currentNum", num);
        anim.setDuration((long) Math.min(duration, 2000));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                int value = (int) animation.getAnimatedValue();
//                int color = calculateColor(value);
//                setBackgroundColor(0xFFEEEBE5);
            }
        });
        anim.start();
    }

    private int calculateColor(int value) {
        ArgbEvaluator evealuator = new ArgbEvaluator();
        float fraction = 0;
        int color = 0;
        if (value <= maxNum / 2) {
            fraction = (float) value / (maxNum / 2);
            color = (int) evealuator.evaluate(fraction, 0xFFFF6347, 0xFFFF8C00); //由红到橙
        } else {
            fraction = ((float) value - maxNum / 2) / (maxNum / 2);
            color = (int) evealuator.evaluate(fraction, 0xFFFF8C00, 0xFF00CED1); //由橙到蓝
        }
        return color;
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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundIndicatorView);
        maxNum = array.getInt(R.styleable.RoundIndicatorView_maxNum, 0);
        startAngle = array.getInt(R.styleable.RoundIndicatorView_startAngle, 90);
        sweepAngle = array.getInt(R.styleable.RoundIndicatorView_sweepAngle, 360);
        //内外圆的宽度
        sweepInWidth = dp2px(3);
        sweepOutWidth = dp2px(8);
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
            mHeight = mWidth/2+topDistance*2;
        }
        setMeasuredDimension(mWidth, mHeight);
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
        canvas.translate(mWidth / 2,  topDistance+radius);
        drawRound(canvas);  //画内外圆
        drawIndicator(canvas); //画当前进度值
        drawCenterText(canvas);//画中间的文字
        canvas.restore();
    }

    private void drawCenterText(Canvas canvas) {
        canvas.save();
        paint_4.setStyle(Paint.Style.FILL);
        //具体的步数
        paint_4.setTextSize(radius / 2);
        paint_4.setColor(0xff7d5e3f);
        float c = -paint_4.measureText(currentNum + "") / 2;
        canvas.drawText(currentNum + "", c, 0, paint_4);

        //数字步
        paint_4.setTextSize(radius / 8);
        canvas.drawText("步", paint_4.measureText(currentNum + "") * 2, 0, paint_4);

        //底部时间
        paint_4.setTextSize(radius / 8);
        paint_4.setColor(0xffc79b70);
        String content = "最新更新时间"+ TimeUtils.getHomeTimeInString();
        Rect r = new Rect();
        paint_4.getTextBounds(content, 0, content.length(), r);
        float a = -r.width() / 2;
        float b = r.height() + radius * 3 / 4;
        canvas.drawText(content, a, b, paint_4);

        //运动目标
        paint_4.setTextSize(radius / 8);
        String content1 = "运动目标" + maxNum + "步";
        paint_4.getTextBounds(content1, 0, content1.length(), r);
        canvas.drawText(content1, -paint_4.measureText(content1 + "") / 2, -(r.height() + radius / 2), paint_4);

//       消耗的文字显示
        paint_4.setTextSize(radius / 6);
        String content2 = SportUtils.getKilometresByStepNumber(0, 1.75, currentNum) + "米 | " + SportUtils.getCalorieBySex(0, 64, currentNum) + "千卡";
        paint_4.getTextBounds(content2, 0, content2.length(), r);
        canvas.drawText(content2, -paint_4.measureText(content2 + "") / 2, r.height() + radius / 4, paint_4);
        canvas.restore();
    }

    private void drawIndicator(Canvas canvas) {
        canvas.save();
        paint_2.setStyle(Paint.Style.STROKE);
        int sweep;
        if (currentNum <= maxNum) {
            sweep = (int) ((float) currentNum / (float) maxNum * sweepAngle);
        } else {
            sweep = sweepAngle;
        }
        paint_2.setStrokeWidth(sweepOutWidth);
//        Shader shader =new SweepGradient(0,0,indicatorColor,null);
//        paint_2.setShader(shader);
        paint_2.setColor(0xffc79b70);
        int w = dp2px(10);
        RectF rectf = new RectF(-radius - w, -radius - w, radius + w, radius + w);
        canvas.drawArc(rectf, startAngle, sweep, false, paint_2);
        float x = (float) ((radius + dp2px(10)) * Math.cos(Math.toRadians(startAngle + sweep)));
        float y = (float) ((radius + dp2px(10)) * Math.sin(Math.toRadians(startAngle + sweep)));
        paint_3.setStyle(Paint.Style.FILL);
        paint_3.setColor(0xffc79b70);
        paint_3.setMaskFilter(new BlurMaskFilter(dp2px(3), BlurMaskFilter.Blur.SOLID)); //需关闭硬件加速
        canvas.drawCircle(x, y, dp2px(4), paint_3);
        canvas.restore();
    }

    private void drawRound(Canvas canvas) {
        canvas.save();
        //内圆
        paint.setColor(0xffe7ddcb);
        paint.setStrokeWidth(sweepInWidth);
        RectF rectf = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(rectf, 140, 260, false, paint);
        //外圆
        paint.setStrokeWidth(sweepOutWidth);
        paint.setColor(0xffffffff);
        int w = dp2px(10);
        RectF rectf2 = new RectF(-radius - w, -radius - w, radius + w, radius + w);
        canvas.drawArc(rectf2, startAngle, sweepAngle, false, paint);
        canvas.restore();
    }

    //一些工具方法
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }

    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp,
                getResources().getDisplayMetrics());
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        invalidate();
    }
}
