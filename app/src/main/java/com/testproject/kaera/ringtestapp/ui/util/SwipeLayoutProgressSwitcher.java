package com.testproject.kaera.ringtestapp.ui.util;

import android.support.v4.widget.SwipeRefreshLayout;

import io.techery.janet.ActionState;
import rx.functions.Action1;

import static io.techery.janet.ActionState.Status.FAIL;
import static io.techery.janet.ActionState.Status.START;
import static io.techery.janet.ActionState.Status.SUCCESS;

public class SwipeLayoutProgressSwitcher<A> implements Action1<ActionState<A>> {
    private final SwipeRefreshLayout refreshLayout;

    public SwipeLayoutProgressSwitcher(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    @Override public void call(ActionState<A> state) {
        if (state.status == FAIL || state.status == SUCCESS) {
            refreshLayout.setRefreshing(false);
        }
        if (state.status == START){
            refreshLayout.setRefreshing(true);
        }
    }
}
