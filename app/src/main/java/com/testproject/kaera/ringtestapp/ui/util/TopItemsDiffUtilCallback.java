package com.testproject.kaera.ringtestapp.ui.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;

import java.util.List;

public class TopItemsDiffUtilCallback extends DiffUtil.Callback{

    List<APIRedditItem> oldPersons;
    List<APIRedditItem> newPersons;

    public TopItemsDiffUtilCallback(List<APIRedditItem> newPersons, List<APIRedditItem> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
