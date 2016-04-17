package uk.co.amlcurran.materialpagerindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class MaterialIndicator extends View implements ViewPager.OnPageChangeListener {

    private final Paint indicatorPaint;
    private final Paint selectedIndicatorPaint;
    private final float indicatorRadius = 20;
    private final RectF selectorRect;
    private int count;
    private int selectedPage = 0;
    private float deselectedAlpha = 0.2f;
    private float offset;

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
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float gap = (getWidth() - indicatorDiameter()) / (count + 1);
        for (int i = 0; i < count; i++) {
            float position = gap * (i + 1);
            canvas.drawCircle(position + indicatorRadius, midY(), indicatorRadius, indicatorPaint);
        }
        float extenderStart = gap * (selectedPage + 1) + Math.max(gap * (offset - 0.5f) * 2, 0);
        float extenderEnd = gap * (selectedPage + 1) + indicatorDiameter() + Math.min(gap * offset * 2, gap);
        selectorRect.set(extenderStart, midY() - indicatorRadius, extenderEnd, midY() + indicatorRadius);
        canvas.drawRoundRect(selectorRect, indicatorRadius, indicatorRadius, selectedIndicatorPaint);
    }

    private float indicatorDiameter() {
        return indicatorRadius * 2;
    }

    private float midY() {
        return getHeight() / 2f;
    }

}
