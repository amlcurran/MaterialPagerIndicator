package uk.co.amlcurran.materialpagerindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

public class MaterialIndicator extends View implements ViewPager.OnPageChangeListener {

    private final Paint indicatorPaint;
    private final Paint selectedIndicatorPaint;
    private final float indicatorRadius = 20;
    private final RectF selectorRect;
    private int count;
    private int selectedPage = 0;
    private float deselectedAlpha = 0.2f;
    private float offset;
    private final Interpolator interpolator = new FastOutSlowInInterpolator();

    public MaterialIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedIndicatorPaint.setColor(Color.RED);
        indicatorPaint = new Paint(selectedIndicatorPaint);
        indicatorPaint.setColor(Color.BLACK);
        indicatorPaint.setAlpha((int) (deselectedAlpha * 255));
        selectorRect = new RectF();
        if (isInEditMode()) {
            count = 3;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        selectedPage = position;
        offset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        selectedPage = position;
        offset = 0;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setAdapter(PagerAdapter adapter) {
        this.count = adapter.getCount();
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getSuggestedMinimumHeight());
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) (indicatorRadius * count) + ViewCompat.getPaddingStart(this) + ViewCompat.getPaddingEnd(this);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return getPaddingTop() + getPaddingBottom() + (int) indicatorDiameter();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float gap = (getWidth() - indicatorDiameter()) / (count + 1);
        for (int i = 0; i < count; i++) {
            float position = pageStartX(gap, i);
            canvas.drawCircle(position + indicatorRadius, midY(), indicatorRadius, indicatorPaint);
        }
        float extenderStart = pageStartX(gap, selectedPage) + Math.max(gap * (interpolatedOffset() - 0.5f) * 2, 0);
        float extenderEnd = pageStartX(gap, selectedPage) + indicatorDiameter() + Math.min(gap * interpolatedOffset() * 2, gap);
        selectorRect.set(extenderStart, midY() - indicatorRadius, extenderEnd, midY() + indicatorRadius);
        canvas.drawRoundRect(selectorRect, indicatorRadius, indicatorRadius, selectedIndicatorPaint);
    }

    private float pageStartX(float gap, int page) {
        return gap * (page + 1);
    }

    private float interpolatedOffset() {
        return interpolator.getInterpolation(offset);
    }

    private float indicatorDiameter() {
        return indicatorRadius * 2;
    }

    private float midY() {
        return getHeight() / 2f;
    }

}
