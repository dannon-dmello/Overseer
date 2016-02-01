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
    protected Activity activity;
    protected LayoutInflater layoutInflater;
    protected ScreenPresenter screenPresenter;
    private int layoutResourceId;
    private View view;


    public Screen(Activity activity, int layoutResourceId, ScreenPresenter screenPresenter) {
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.layoutInflater = LayoutInflater.from(activity);
        this.screenPresenter = screenPresenter;
    }


    public View getView() {
        return view;
    }


    public void destroyView() {
        if (view != null) {
            view.destroyDrawingCache();
            view = null;
        }
        onViewDestroyed();
    }

    void create() {
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
        layoutInflater = null;
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
