package com.virtuotek.overseer;

import android.app.Activity;

/**
 * Created by Danny on 24/04/16.
 * This screen will be displayed as a Dialog over the current screen being displayed.
 */
public abstract class DialogScreen extends Screen {
    /**
     * @param activity         Activity hosting this screen
     * @param layoutResourceId layout file for this screen
     * @param screenPresenter  responsible for populating this screen
     */
    public DialogScreen(Activity activity, int layoutResourceId, ScreenPresenter screenPresenter) {
        super(activity, layoutResourceId, screenPresenter, false);
    }
}
