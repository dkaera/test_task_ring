package com.testproject.kaera.ringtestapp.controllers.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.Controller;
import com.testproject.kaera.ringtestapp.R;

import butterknife.BindView;
import butterknife.Optional;
import butterknife.Unbinder;

import static butterknife.ButterKnife.bind;

public abstract class ButterKnifeController extends Controller {

    private Unbinder unbinder;

    public ButterKnifeController() {
        super();
    }

    public ButterKnifeController(Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        unbinder = bind(this, view);
        onViewBound(view);
        return view;
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        unbinder.unbind();
        unbinder = null;
    }

    protected abstract View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    protected void onViewBound(@NonNull View view) { }
}