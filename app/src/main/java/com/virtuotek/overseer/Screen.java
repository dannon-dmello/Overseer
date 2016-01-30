package com.virtuotek.overseer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Dannon(danno99@gmail.com) on 25/01/16.
 * Represents a self contained GUI Screen.
 */
public abstract class Screen {
    protected LayoutInflater layoutInflater;
    protected ScreenPresenter screenPresenter;
    private int layoutResourceId;
    private View view;


    public Screen(Context context, int layoutResourceId, ScreenPresenter screenPresenter) {
        this.layoutResourceId = layoutResourceId;
        this.layoutInflater = LayoutInflater.from(context);
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
