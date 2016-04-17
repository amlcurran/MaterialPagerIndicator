package uk.co.amlcurran.materialpagerindicator.sample;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import uk.co.amlcurran.materialpagerindicator.MaterialIndicator;

public class DemoActivity extends AppCompatActivity {

    private static final int[] COLORS = new int[]{
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        MaterialIndicator indicator = (MaterialIndicator) findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(indicator);
        indicator.setAdapter(viewPager.getAdapter());
    }

    private static class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView view = new TextView(container.getContext());
            view.setBackgroundColor(COLORS[position % COLORS.length]);
            view.setText(String.format(Locale.getDefault(), "%d", position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
        }
    }
}
