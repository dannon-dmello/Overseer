package com.virtuotek.overseer;

import android.view.Gravity;

import com.transitionseverywhere.AutoTransition;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionSet;

/**
 * Created by Danny on 25/01/16.
 */
class TransitionAnimation {
    private static final int DEFAULT_TRANSITION_SPEED = 300;

    public static TransitionSet getTransition(TransitionAnimationType transitionAnimationType) {
        Transition transition;
        switch (transitionAnimationType) {
            case LEFT_TO_RIGHT:
                transition = new Slide(Gravity.LEFT);
                break;
            case RIGHT_TO_LEFT:
                transition = new Slide(Gravity.RIGHT);
                break;
            case BOTTOM_TO_TOP:
                transition = new Slide(Gravity.BOTTOM);
                break;
            case FADE:
                transition = new Fade();
                break;
            case EXPLODE:
                transition = new Explode();
                break;
            default:
                transition = new AutoTransition();
                break;
        }

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transition);
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeImageTransform());
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.setDuration(DEFAULT_TRANSITION_SPEED);
        return transitionSet;
    }
}
