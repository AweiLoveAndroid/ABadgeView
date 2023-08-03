package com.lzw.badgeview.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Badge drag state.
 */
@IntDef({
        ABadgeDragState.STATE_START,
        ABadgeDragState.STATE_DRAGGING,
        ABadgeDragState.STATE_DRAGGING_OUT_OF_RANGE,
        ABadgeDragState.STATE_CANCELED,
        ABadgeDragState.STATE_SUCCEED
})
@Retention(RetentionPolicy.SOURCE)
public @interface ABadgeDragState {
    int STATE_START = 1;
    int STATE_DRAGGING = 2;
    int STATE_DRAGGING_OUT_OF_RANGE = 3;
    int STATE_CANCELED = 4;
    int STATE_SUCCEED = 5;
}