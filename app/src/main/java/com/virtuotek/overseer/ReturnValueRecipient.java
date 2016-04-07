package com.virtuotek.overseer;

/**
 * Created by Danny on 23/03/16.
 */
public interface ReturnValueRecipient {

    /**
     * Called by the screen manager, if a different screen returns a value to this screen
     *
     * @param value       The return value
     * @param requestCode The original Request code
     */
    void onValueReturned(Object value, int requestCode);
}
