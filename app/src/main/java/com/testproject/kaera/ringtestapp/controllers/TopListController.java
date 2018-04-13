package com.testproject.kaera.ringtestapp.controllers;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;
import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand;
import com.testproject.kaera.ringtestapp.ui.TopListAdapter;
import com.testproject.kaera.ringtestapp.util.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import io.techery.janet.ActionPipe;
import rx.functions.Action1;
import rx.functions.Action2;

import static android.provider.Contacts.PresenceColumns.IDLE;

public class TopListController extends BaseController {

    public static final String RECYCLER_VIEW_SAVED_POSITION = "recycler_view_saved_position";

    @Inject ActionPipe<GetTopSubredditCommand> getTopSubredditCommand;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private TopListAdapter adapter;
    private int scrollToPosition = Constants.NO_POSITION;

    public TopListController() {
        super();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

        return inflater.inflate(R.layout.controller_top_list, container, false);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        bindPipe(getTopSubredditCommand)
                .onProgress((command, progress) -> putData(command))
                .onSuccess(this::putData);

        loadData();
    }

    private void putData(GetTopSubredditCommand command) {
        adapter.putData(command.getCachedData());
        recyclerView.getLayoutManager().scrollToPosition(scrollToPosition);
        scrollToPosition = Constants.NO_POSITION;
    }

    @Override protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        RingApplication.getComponent().inject(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new TopListAdapter(view.getContext());
        adapter.setOnItemClickListener((holder, model) -> onItemClick(model));
        adapter.setThumbnailClickListener(this::onThumbnailClick);
        recyclerView.setAdapter(adapter);
    }

    private void onItemClick(APIRedditItem model) {
        Log.e("TAG", "on item click" + model);
    }

    private void onThumbnailClick(APIRedditItem model) {
        Log.e("TAG", "on thumbnail click " + model);
    }

    private void loadData() {
        getTopSubredditCommand.send(new GetTopSubredditCommand());
    }

    private void loadNext(String afterId, int count) {
        getTopSubredditCommand.send(new GetTopSubredditCommand());
    }

    @Override protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        outState.putInt(RECYCLER_VIEW_SAVED_POSITION, layoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
        if (savedViewState != null && savedViewState.containsKey(RECYCLER_VIEW_SAVED_POSITION)) {
            scrollToPosition = savedViewState.getInt(RECYCLER_VIEW_SAVED_POSITION);
        }
    }
}