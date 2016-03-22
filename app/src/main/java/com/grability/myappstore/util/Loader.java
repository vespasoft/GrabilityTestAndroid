package com.grability.myappstore.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.grability.myappstore.R;

/**
 * Created by Luis Vespa on 22/03/2016.
 */
public class Loader extends ImageView {

    public Loader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Loader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Loader(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.animation_list_filling);
        final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable(){
            public void run(){
                frameAnimation.start();
            }
        });
    }
}