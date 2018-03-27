package id.haqiqi_studio.kamussasak;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroViewPageAdapter extends PagerAdapter {

    private int[] layouts;
    private LayoutInflater layInflater;
    private Context context;

    public IntroViewPageAdapter (int[] layouts, Context context){
        this.layouts = layouts;
        this.context = context;
        layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int pos) {
        View view = layInflater.inflate(layouts[pos], container, false);
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int pos, Object object) {
        View view = (View) object;
        container.removeView(view);

    }
}
