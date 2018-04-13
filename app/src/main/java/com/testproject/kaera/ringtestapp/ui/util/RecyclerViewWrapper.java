package com.testproject.kaera.ringtestapp.ui.util;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rx.functions.Action1;
import rx.functions.Func1;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.AdapterDataObserver;
import static android.support.v7.widget.RecyclerView.OnScrollListener;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RecyclerViewWrapper {

    private final LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Func1<Integer, Boolean> endlessCallback;

    public RecyclerViewWrapper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.layoutManager = new LinearLayoutManager(recyclerView.getContext());
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addOnScrollListener(new OnScrollListener() {
            int lastItemCount;

            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState != SCROLL_STATE_DRAGGING) return;
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (endlessCallback == null) return;
                int itemCount = layoutManager.getItemCount();
                if (itemCount == lastItemCount) return;
                int tillEnd = itemCount - layoutManager.findLastCompletelyVisibleItemPosition();
                if (endlessCallback.call(tillEnd)) lastItemCount = itemCount;
            }
        });
    }

    public void setEndlessCallback(Func1<Integer, Boolean> callback) {
        endlessCallback = callback;
    }

    public void setAdapter(Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public static class EndlessCallback implements Func1<Integer, Boolean> {

        private final int threshold;
        private final Action1<Integer> endlessCallback;

        public EndlessCallback(int threshold, Action1<Integer> endlessCallback) {
            this.threshold = threshold;
            this.endlessCallback = endlessCallback;
        }

        @Override public Boolean call(Integer tillEnd) {
            if (tillEnd <= threshold) {
                endlessCallback.call(tillEnd);
                return true;
            }
            return false;
        }
    }
}
