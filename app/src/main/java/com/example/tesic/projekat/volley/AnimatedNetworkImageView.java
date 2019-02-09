package com.example.tesic.projekat.volley;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class AnimatedNetworkImageView extends NetworkImageView {

    private static final int ANIM_DURATION = 2000;
    private boolean shouldAnimate = false;

    public AnimatedNetworkImageView(Context context) {
        super(context);
    }

    public AnimatedNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if(shouldAnimate) {
            ObjectAnimator.ofFloat(this, "alpha", 0, 1).setDuration(ANIM_DURATION).start();
        }
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        shouldAnimate = !imageLoader.isCached(url, 0, 0);
        super.setImageUrl(url, imageLoader);
    }
}