package com.testproject.kaera.ringtestapp.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.changehandler.FabToDialogTransitionChangeHandler;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.service.command.AuthenticateCommand;
import com.testproject.kaera.ringtestapp.ui.HomeAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.techery.janet.ActionPipe;

public class HomeController extends BaseController {

    public enum HomeScreen {
        NAVIGATION("Navigation Demos", android.R.color.holo_red_dark);

        public String title;
        @ColorRes
        public int color;

        HomeScreen(String title, @ColorRes int color) {
            this.title = title;
            this.color = color;
        }
    }

    private static final String KEY_FAB_VISIBILITY = "HomeController.fabVisibility";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    View fab;

    public HomeController() {
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_home, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        HomeAdapter adapter = new HomeAdapter(view.getContext(), HomeScreen.values());
        adapter.setOnItemClickListener((holder, model) -> onModelRowClick(model));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        outState.putInt(KEY_FAB_VISIBILITY, fab.getVisibility());
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
        //noinspection WrongConstant
        fab.setVisibility(savedViewState.getInt(KEY_FAB_VISIBILITY));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    protected void onChangeStarted(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        setOptionsMenuHidden(!changeType.isEnter);
        if (changeType.isEnter) setTitle();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            onFabClicked(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected String getTitle() {
        return "Conductor Demos";
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        onFabClicked(true);
    }

    // Show dialog sample
    private void onFabClicked(boolean fromFab) {
        SpannableString details = new SpannableString("A small, yet full-featured framework that allows building View-based Android applications");
        details.setSpan(new AbsoluteSizeSpan(16, true), 0, details.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        final String url = "https://github.com/bluelinelabs/Conductor";
        SpannableString link = new SpannableString(url);
        link.setSpan(new URLSpan(url) {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        }, 0, link.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder description = new SpannableStringBuilder();
        description.append(details);
        description.append("\n\n");
        description.append(link);

        ControllerChangeHandler pushHandler = fromFab ? new TransitionChangeHandlerCompat(new FabToDialogTransitionChangeHandler(), new FadeChangeHandler(false)) : new FadeChangeHandler(false);
        ControllerChangeHandler popHandler = fromFab ? new TransitionChangeHandlerCompat(new FabToDialogTransitionChangeHandler(), new FadeChangeHandler()) : new FadeChangeHandler();

        getRouter()
                .pushController(RouterTransaction.with(new DialogController("Conductor", description))
                        .pushChangeHandler(pushHandler)
                        .popChangeHandler(popHandler));

    }

    void onModelRowClick(HomeScreen model) {
        switch (model) {
            case NAVIGATION:
                getRouter().pushController(RouterTransaction.with(new NavigationDemoController(0, NavigationDemoController.DisplayUpMode.SHOW_FOR_CHILDREN_ONLY))
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
                        .tag(NavigationDemoController.TAG_UP_TRANSACTION)
                );
                break;
        }
    }
}