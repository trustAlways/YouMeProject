package com.youme.candid.youmeapp.Activity.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youme.candid.youmeapp.R;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    // array of integers for images IDs
    private Integer[] mImageIds = {
            R.drawable.real_identity,
            R.drawable.search_across_globe, R.drawable.ads_free,
            R.drawable.chat_unlimited, R.drawable.bookmarks,R.drawable.go_backs

    };

    public SlidingImage_Adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


        imageView.setImageResource(mImageIds[position]);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}