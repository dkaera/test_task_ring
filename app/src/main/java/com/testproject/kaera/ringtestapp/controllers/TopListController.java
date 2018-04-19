package com.testproject.kaera.ringtestapp.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;
import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand;
import com.testproject.kaera.ringtestapp.ui.TopListAdapter;
import com.testproject.kaera.ringtestapp.ui.util.SwipeLayoutProgressSwitcher;
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewWrapper;
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewWrapper.EndlessCallback;

import javax.inject.Inject;

import butterknife.BindView;
import io.techery.janet.ActionPipe;

public class TopListController extends BaseController {

    public static final int THRESHOLD = 5;

    private TopListAdapter adapter;
    private RecyclerViewWrapper recyclerViewWrapper;

    @Inject ActionPipe<GetTopSubredditCommand> getTopSubredditCommand;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;

    public TopListController() {
        super();
    }

    @Override protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_top_list, container, false);
    }

    @Override protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        bindPipe(getTopSubredditCommand)
                .afterEach(new SwipeLayoutProgressSwitcher<>(refreshLayout))
                .onSuccess(this::putData);
        loadData();
    }

    @Override protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        RingApplication.getComponent().inject(this);
        recyclerViewWrapper = new RecyclerViewWrapper(recyclerView);
        adapter = new TopListAdapter();
        adapter.setThumbnailClickListener(this::onThumbnailClick);
        recyclerViewWrapper.setAdapter(adapter);
        recyclerViewWrapper.setEndlessCallback(new EndlessCallback(THRESHOLD, integer -> {
            int itemCount = adapter.getItemCount();
            String name = adapter.getItem(itemCount - 1).getName();
            loadNext(name, itemCount);
        }));
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this::loadData);
    }

    private void onThumbnailClick(APIRedditItem model) {
        if(TextUtils.isEmpty(model.getThumbFullSize())) return;
        getRouter().pushController(RouterTransaction.with(new GalleryController(model.getThumbFullSize())));
    }

    private void loadData() {
        getTopSubredditCommand.send(new GetTopSubredditCommand(true));
    }

    private void loadNext(String afterId, int count) {
        getTopSubredditCommand.send(new GetTopSubredditCommand(afterId, count));
    }

    private void putData(GetTopSubredditCommand command) {
        adapter.setData(command.getResult());
    }

    @Override protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        recyclerViewWrapper.saveViewState(outState);
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
        recyclerViewWrapper.restoreViewState(savedViewState);
    }

    @Override protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        recyclerViewWrapper.onDestroyView();
    }
}