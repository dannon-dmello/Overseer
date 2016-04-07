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
     * @param screen the Screen which should be populated
     */
    public abstract void populateView(Screen screen);

    /**
     * Called when the screen is added to the backstack. You may want to cleanup some memory intensive resources here
     * that can be fetched or constructed again if the screen is brought back from the history stack
     */
    public void cleanup() {

    }

    /**
     * Called on destroy of the screen, when it is removed from the stack and discarded.
     * Free all resources here.
     * Note that cleanup is called before this. So there's no need to duplicate your cleanup code.
     */
    public void destroy() {
        //Cleanup code;
    }
}
