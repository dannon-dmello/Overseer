package com.virtuotek.overseer;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Dannon(danno99@gmail.com) on 25/01/16.
 * Represents a self contained GUI Screen.
 */
public abstract class Screen {
    public View view;
    public boolean addToHistoryStack;
    public Activity activity;
    public boolean allowMultipleInstances;
    protected ScreenPresenter screenPresenter;
    private int layoutResourceId;


    /**
     * @param activity          Activity hosting this screen
     * @param layoutResourceId  layout file for this screen
     * @param screenPresenter   responsible for populating this screen
     * @param addToHistoryStack if false, will not be added to history stack. Ideal for full screen dialog like screens, like an info or cart screen
     */
    public Screen(Activity activity, int layoutResourceId, ScreenPresenter screenPresenter, boolean addToHistoryStack) {
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.screenPresenter = screenPresenter;
        this.addToHistoryStack = addToHistoryStack;
    }


    public void destroyView() {
        if (view != null) {
            view.destroyDrawingCache();
            view = null;
        }
        onViewDestroyed();
        screenPresenter.cleanup();
    }

    void create(LayoutInflater layoutInflater) {
        if (view == null) {
            view = layoutInflater.inflate(layoutResourceId, null);
        }
        onViewCreated();
        if (screenPresenter != null) {
            screenPresenter.populateView(this);
        }
    }

    void destroy() {
        destroyView();
        screenPresenter.destroy();
        layoutResourceId = 0;
        screenPresenter = null;
        activity = null;
    }

    public abstract void onViewCreated();

    public abstract void onViewDestroyed();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Screen) {
            return layoutResourceId == ((Screen) o).layoutResourceId;
        }
        return super.equals(o);
    }


    @Override
    public int hashCode() {
        return layoutResourceId;
    }

    public abstract void onActivityPause();

    public abstract void onActivityResume();

    public abstract void onActivityCreate(Bundle bundle);

    public abstract void onActivityDestroy();

    public abstract void onActivitySaveState(Bundle bundle);
}
