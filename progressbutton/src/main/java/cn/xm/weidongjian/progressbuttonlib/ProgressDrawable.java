package cn.xm.weidongjian.progressbuttonlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

/**
 * Created by Weidongjian on 2015/7/15.
 */
public class ProgressDrawable extends Drawable {
    private Paint mPaint;
    private float width;
    private static final int STAGE_NULL = 0, STAGE_ROTATE = 1, STAGE_FINISH = 2, STAGE_ERROR = 3;
    private int stage = 0;
    private RectF rectF;
    private float centerX, centerY;
    private float degreen = 0;
    private ValueAnimator animator;
    private Path pathFinish, pathErrorOne, pathErrorTwo;
    private float lenFinish, lenError, length;
    private int colorDefault = Color.WHITE, colorError = Color.RED;
    private Button button;
    private Animatable animatable;

    public ProgressDrawable(float size, Button button) {
        this.width = size;
        this.button = button;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        rectF = new RectF(0, 0, width, width);
        centerX = width/2;
        centerY = width/2;
        initPath();
    }

    private void initPath() {
        pathFinish = new Path();
        pathFinish.moveTo(width * 1f, width * 0.2f);
        pathFinish.lineTo(width * 0.4f, width * 0.8f);
        pathFinish.lineTo(0f, width * 0.4f);
        PathMeasure pm = new PathMeasure(pathFinish, false);
        lenFinish = pm.getLength();

        pathErrorOne = new Path();
        pathErrorOne.moveTo(width * 0.9f, width * 0.9f);
        pathErrorOne.lineTo(width * 0.1f, width * 0.1f);
        pm.setPath(pathErrorOne, false);
        lenError = pm.getLength();

        pathErrorTwo = new Path();
        pathErrorTwo.moveTo(width * 0.9f, width * 0.1f);
        pathErrorTwo.lineTo(width * 0.1f, width * 0.9f);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) width;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) width;
    }

    @Override
    public void draw(Canvas canvas) {
        if (stage == STAGE_NULL)
            return;
        if (stage == STAGE_ROTATE) {
            canvas.save();
            canvas.rotate(degreen, centerX, centerY);
            canvas.drawArc(rectF, -90f, 100f, false, mPaint);
            canvas.restore();
            return;
        }
        if (stage == STAGE_FINISH) {
            canvas.drawPath(pathFinish, mPaint);
            mPaint.setPathEffect(null);
            return;
        }
        if (stage == STAGE_ERROR) {
            canvas.drawPath(pathErrorOne, mPaint);
            canvas.drawPath(pathErrorTwo, mPaint);
            mPaint.setPathEffect(null);
            return;
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void startRotate() {
        stage = STAGE_ROTATE;
        mPaint.setColor(colorDefault);
        button.setClickable(false);
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(2000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    degreen += 5;
                    invalidateSelf();
                }
            });
        }
        animator.start();
    }

    public void stopRotate() {
        if (animator != null && animator.isRunning()) {
            animator.end();
        }
    }

    private void setPhase(float phase) {
        mPaint.setPathEffect(new DashPathEffect(new float[]{length, length}, -length * phase));
        invalidateSelf();
    }

    public void animFinish() {
        stage = STAGE_FINISH;
        length = lenFinish;
        mPaint.setColor(colorDefault);
        startAnim();
    }

    public void animError() {
        stage = STAGE_ERROR;
        length = lenError;
        mPaint.setColor(colorError);
        startAnim();
    }

    private void startAnim() {
        stopRotate();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "Phase", 1f, 0f);
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                button.setClickable(true);
                if (animatable != null){
                    animatable.stop();
                }
            }
        });
        animator.start();
    }

    public void setColorDefault(int color) {
        this.colorDefault = color;
    }

    public void setAnimatable(Animatable animatable) {
        this.animatable = animatable;
    }

}
