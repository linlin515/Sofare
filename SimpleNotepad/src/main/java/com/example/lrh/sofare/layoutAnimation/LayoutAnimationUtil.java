package com.example.lrh.sofare.layoutAnimation;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

/**
 * Created by LRH on 2017/7/12.
 */

public class LayoutAnimationUtil {
    public static void setLayoutAnimation(Animation animation, float dely, ViewGroup layout){
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation, dely);
        layout.setLayoutAnimation(layoutAnimationController);
    }
}
