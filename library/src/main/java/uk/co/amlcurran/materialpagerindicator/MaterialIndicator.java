package uk.co.amlcurran.materialpagerindicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MaterialIndicator extends View implements ViewPager.OnPageChangeListener {

    public MaterialIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(getClass().getSimpleName(), "position: " + position + " positionOffset: " + positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
