package com.testproject.kaera.ringtestapp.ui.util;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import static com.trello.rxlifecycle.android.RxLifecycleAndroid.bindView;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by Dmitriy Puzak on 7/1/16.
 */
public abstract class RecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> items;

    private RecyclerViewAdapter.OnLongItemClickListener longClickListener;
    private RecyclerViewAdapter.OnItemClickListener clickListener;

    public RecyclerViewAdapter(@Nullable List<T> items) {
        this.items = items == null ? new ArrayList<>() : items;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setOnItemClickListener(RecyclerViewAdapter.OnItemClickListener<T, VH> listener) {
        this.clickListener = listener;
    }

    public void setOnLongClickListener(OnLongItemClickListener<T, VH> longClickListener) {
        this.longClickListener = longClickListener;
    }

    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(final VH holder) {
        RxView.clicks(holder.itemView)
                .filter(empty -> clickListener != null)
                .observeOn(mainThread())
                .compose(bindView(holder.itemView))
                .map(empty -> Math.max(0, holder.getAdapterPosition()))
                .subscribe(pos -> clickListener.onItemClicked(holder, getItem(pos)));

        RxView.longClicks(holder.itemView)
                .filter(empty -> longClickListener != null)
                .observeOn(mainThread())
                .compose(bindView(holder.itemView))
                .map(empty -> Math.max(0, holder.getAdapterPosition()))
                .subscribe(pos -> longClickListener.onLongItemClicked(holder, getItem(pos)));
    }

    @Nullable
    public T getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void putData(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener<T, VH extends RecyclerView.ViewHolder> {
        void onItemClicked(VH holder, T item);
    }

    public interface OnLongItemClickListener<T, VH extends RecyclerView.ViewHolder> {
        boolean onLongItemClicked(VH holder, T item);
    }
}