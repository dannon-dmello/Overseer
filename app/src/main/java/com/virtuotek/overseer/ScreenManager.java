package com.virtuotek.overseer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import com.transitionseverywhere.Scene;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import java.util.Stack;

/**
 * Created by Dannon(danno99@gmail.com) on 25/01/16.
 * The Overseer. Manages all the "Screens", including the transitioning between them, the backstack etc.
 */
public class ScreenManager {
    public Screen currentScreen;
    private Activity overseerActivity;
    private Stack<Screen> historyStack;
    private ScreenContainer screenContainer;
    private LayoutInflater layoutInflater;
    private Transition lastTransition;

    public ScreenManager(Activity overseerActivity, ScreenContainer screenContainer) {
        this.overseerActivity = overseerActivity;
        this.screenContainer = screenContainer;
        historyStack = new Stack<>();
        layoutInflater = LayoutInflater.from(overseerActivity);
    }

    private static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Navigate to the next Screen
     *
     * @param screen                  the screen to transition to
     * @param bringToFrontIfExists    if it exists in the history stack, all screens over it will be cleared and it will be brought to the front
     * @param transitionAnimationType specify from one of the inbuilt transitions
     */
    public void goToScreen(Screen screen, boolean bringToFrontIfExists, TransitionAnimationType transitionAnimationType) {
        int pos = historyStack.search(screen);
        if (bringToFrontIfExists && pos > -1) {
            popTillPosition(pos);
        }
        switchToScreen(screen, false, TransitionAnimation.getTransition(transitionAnimationType));
    }

    /**
     * Navigate to the next Screen
     *
     * @param screen               the screen to transition to
     * @param bringToFrontIfExists if it exists in the history stack, all screens over it will be cleared and it will be brought to the front
     * @param customTransition     Your custom transition between the screens
     */
    public void goToScreen(Screen screen, boolean bringToFrontIfExists, Transition customTransition) {
        int pos = historyStack.search(screen);
        if (bringToFrontIfExists && pos > -1) {
            popTillPosition(pos);
        }
        switchToScreen(screen, false, customTransition);
    }

    /**
     * Returns true if the history stack contains a screen and it has successfully navigated back.
     * Returns false if there are no more screens in the stack
     *
     * @return if back is handled or not
     */
    public boolean goBack() {
        if (historyStack.size() > 0) {
            switchToScreen(historyStack.pop(), true, lastTransition != null ? lastTransition : TransitionAnimation.getTransition(TransitionAnimationType.RIGHT_TO_LEFT));
            return true;
        }
        return false;
    }

    private void switchToScreen(Screen newScreen, boolean goingBack, Transition transition) {
        if (lastTransition != transition) {
            lastTransition = transition;
        } else {
            lastTransition = TransitionAnimation.getTransition(TransitionAnimationType.RIGHT_TO_LEFT);
        }
        hideSoftKeyboard(overseerActivity);
        newScreen.create(layoutInflater);
        if (currentScreen != null) {
            if (!currentScreen.equals(newScreen)) {
                final Scene scene = new Scene(screenContainer, newScreen.view);
                TransitionManager.go(scene, transition);
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

    /**
     * Call onPause of your activity to pass the callback to the Current Screen
     */
    public void onActivityPause() {
        if (currentScreen != null) {
            currentScreen.onActivityPause();
        }
    }

    /**
     * Call onResume of your activity to pass the callback to the Current Screen
     */
    public void onActivityResume() {
        if (currentScreen != null) {
            currentScreen.onActivityResume();
        }
    }

    /**
     * Call onCreate of your activity to pass the callback to the Current Screen
     */
    public void onActivityCreate(Bundle bundle) {
        if (currentScreen != null) {
            currentScreen.onActivityCreate(bundle);
        }
    }

    /**
     * Call onDestroy of your activity to pass the callback to the Current Screen
     */
    public void onActivityDestroy() {
        if (currentScreen != null) {
            currentScreen.onActivityDestroy();
        }
    }

    /**
     * Call from the activity onSaveInstanceState, if you want the Current Screen to save its state
     *
     * @param bundle
     */
    public void onActivitySaveState(Bundle bundle) {
        if (currentScreen != null) {
            currentScreen.onActivitySaveState(bundle);
        }
    }

    /**
     * Clear the history stack.
     */
    public void clearHistory() {
        historyStack.clear();
        lastTransition = null;
    }
}
