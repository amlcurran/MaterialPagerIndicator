package uk.co.amlcurran.materialpagerindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MaterialIndicator extends View implements ViewPager.OnPageChangeListener {

    private final Paint indicatorPaint;
    private final Paint selectedIndicatorPaint;
    private final float indicatorRadius = 20;
    private int count;
    private int selectedPage = 0;
    private float deselectedAlpha = 0.2f;

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
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(getClass().getSimpleName(), "position: " + position + " positionOffset: " + positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        selectedPage = position;
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
        float gap = (getWidth() - 2 * indicatorRadius) / (count + 1);
        for (int i = 0; i < count; i++) {
            float position = gap * (i + 1);
            canvas.drawCircle(position + indicatorRadius, getHeight() / 2f, indicatorRadius, getIndicatorPaint(i));
        }
    }

    @NonNull
    private Paint getIndicatorPaint(int position) {
        if (position == selectedPage) {
            return selectedIndicatorPaint;
        }
        return indicatorPaint;
    }
}
