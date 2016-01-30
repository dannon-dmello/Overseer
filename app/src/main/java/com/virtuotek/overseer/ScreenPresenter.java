package com.virtuotek.overseer;

/**
 * Created by Danny on 25/01/16.
 * Responsible for populating the data on a "Screen"
 */
public abstract class ScreenPresenter {

    /**
     * Populate your screen with this method. Don't write any biz logic in your Screen.
     * Ideally this method will call your biz layer to receive data and set it on your view
     * Goes without saying, but Don't write any blocking code on this thread.
     *
     * @param screen
     */
    public abstract void populateView(Screen screen);
}
