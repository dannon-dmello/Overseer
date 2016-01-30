package com.virtuotek.overseer;

import android.view.Gravity;

import com.transitionseverywhere.AutoTransition;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.Transition;

/**
 * Created by Danny on 25/01/16.
 */
class TransitionAnimation {

    public static Transition getTransition(TransitionAnimationType transitionAnimationType) {
        Transition transition;
        switch (transitionAnimationType) {
            case LEFT_TO_RIGHT:
                transition = new Slide(Gravity.LEFT);
                transition.setDuration(200);
                return transition;
            case RIGHT_TO_LEFT:
                transition = new Slide(Gravity.RIGHT);
                transition.setDuration(200);
                return transition;
            case BOTTOM_TO_TOP:
                transition = new Slide(Gravity.BOTTOM);
                transition.setDuration(200);
                return transition;
            case FADE:
                transition = new Fade();
                transition.setDuration(200);
                return transition;
            case EXPLODE:
                transition = new Explode();
                transition.setDuration(200);
                return transition;

            default:
                transition = new AutoTransition();
                return transition;
        }

    }
}
