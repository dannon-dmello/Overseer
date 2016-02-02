package com.virtuotek.overseer;

import android.app.Activity;
import android.os.Bundle;

import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionManager;

import java.util.Stack;

/**
 * Created by Dannon(danno99@gmail.com) on 25/01/16.
 * The Overseer. Manages all the "Screens", including the transitioning between them, the backstack etc.
 */
public class ScreenManager {
    private Activity overseerActivity;
    private Stack<Screen> historyStack;
    private Screen currentScreen;
    private ScreenContainer screenContainer;

    public ScreenManager(Activity overseerActivity, ScreenContainer screenContainer) {
        this.overseerActivity = overseerActivity;
        this.screenContainer = screenContainer;
        historyStack = new Stack<>();
    }

    /**
     * Navigate to the next Screen
     *
     * @param screen                  the screen to transition to
     * @param bringToFrontIfExists    if it exists in the history stack, all screen over it will be cleared and it will be brought to the front
     * @param transitionAnimationType specify from one of the inbuilt transitions
     */
    public void goToScreen(Screen screen, boolean bringToFrontIfExists, TransitionAnimationType transitionAnimationType) {
        int pos = historyStack.search(screen);
        if (bringToFrontIfExists && pos > -1) {
            popTillPosition(pos);
        }
        switchToScreen(screen, false, transitionAnimationType);
    }

    /**
     * Returns true if the history stack contains a screen and it has successfully navigated back.
     * Returns false if there are no more screens in the stack
     *
     * @return if back is handled or not
     */
    public boolean goBack() {
        if (historyStack.size() > 0) {
            switchToScreen(historyStack.pop(), true, TransitionAnimationType.RIGHT_TO_LEFT);
            return true;
        }
        return false;
    }


    private void switchToScreen(Screen newScreen, boolean goingBack, TransitionAnimationType transitionAnimationType) {
        newScreen.create();
        if (currentScreen != null) {
            if (!currentScreen.equals(newScreen)) {
                final Scene scene = new Scene(screenContainer, newScreen.view);
                TransitionManager.go(scene, TransitionAnimation.getTransition(transitionAnimationType));
                if (currentScreen.addToHistoryStack && !goingBack) {
                    currentScreen.destroyView();
                    historyStack.push(currentScreen);
                } else {
                    currentScreen.destroy();
                    currentScreen = null;
                }
            }
        } else {
            screenContainer.addView(newScreen.view);
        }
        currentScreen = newScreen;
    }

    private void popTillPosition(int pos) {
        int size = historyStack.size();
        int popCount = size - pos;
        while (popCount > 0) {
            final Screen poppedScreen = historyStack.pop();
            poppedScreen.destroy();
            popCount--;
        }
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void onActivityPause() {
        if (currentScreen != null) {
            currentScreen.onActivityPause();
        }
    }

    public void onActivityResume() {
        if (currentScreen != null) {
            currentScreen.onActivityResume();
        }
    }

    public void onActivityCreate(Bundle bundle) {
        if (currentScreen != null) {
            currentScreen.onActivityCreate(bundle);
        }
    }

    public void onActivityDestroy() {
        if (currentScreen != null) {
            currentScreen.onActivityDestroy();
        }
    }

    public void onActivitySaveState(Bundle bundle) {
        if (currentScreen != null) {
            currentScreen.onActivitySaveState(bundle);
        }
    }
}
