package com.virtuotek.overseer;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Dannon(danno99@gmail.com) on 25/01/16.
 * Main screen container. Will only display one active screen at a time. Place this is your main activity layout
 */
public class ScreenContainer extends FrameLayout {


    public ScreenContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}