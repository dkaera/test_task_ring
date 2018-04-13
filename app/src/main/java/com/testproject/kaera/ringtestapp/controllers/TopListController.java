package com.testproject.kaera.ringtestapp.controllers;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;
import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand;
import com.testproject.kaera.ringtestapp.ui.TopListAdapter;
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewWrapper;
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewWrapper.EndlessCallback;
import com.testproject.kaera.ringtestapp.util.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import io.techery.janet.ActionPipe;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func1;

import static android.provider.Contacts.PresenceColumns.IDLE;

public class TopListController extends BaseController {

    public static final String RECYCLER_VIEW_SAVED_POSITION = "recycler_view_saved_position";
    public static final int THRESHOLD = 5;

    @Inject ActionPipe<GetTopSubredditCommand> getTopSubredditCommand;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private TopListAdapter adapter;
    private RecyclerViewWrapper recyclerViewWrapper;
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
        bindPipe(getTopSubredditCommand).onSuccess(this::putData);
        loadData();
    }

    private void putData(GetTopSubredditCommand command) {
        adapter.setData(command.getResult());
//        recyclerViewWrapper.getLayoutManager().scrollToPosition(scrollToPosition);
//        scrollToPosition = Constants.NO_POSITION;
    }

    @Override protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        RingApplication.getComponent().inject(this);
        recyclerViewWrapper = new RecyclerViewWrapper(recyclerView);
        adapter = new TopListAdapter(view.getContext());
        adapter.setThumbnailClickListener(this::onThumbnailClick);
        recyclerViewWrapper.setAdapter(adapter);
        recyclerViewWrapper.setEndlessCallback(new EndlessCallback(THRESHOLD, integer -> {
            int itemCount = adapter.getItemCount();
            String name = adapter.getItem(itemCount - 1).getName();
            loadNext(name, itemCount);
        }));
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

    @Override protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        LinearLayoutManager layoutManager = recyclerViewWrapper.getLayoutManager();
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