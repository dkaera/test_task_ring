package com.testproject.kaera.ringtestapp.controllers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.util.BundleBuilder;

import static com.testproject.kaera.ringtestapp.controllers.DialogController.DialogCallback.Result.CANCEL;
import static com.testproject.kaera.ringtestapp.controllers.DialogController.DialogCallback.Result.OK;

public class DialogController extends BaseController {

    private static final String KEY_TITLE = "DialogController.title";
    private static final String KEY_DESCRIPTION = "DialogController.description";

//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tv_description)
//    TextView tvDescription;

    private DialogCallback callback = result -> {
        // empty
    };

    public DialogController(CharSequence title, CharSequence description) {
        this(new BundleBuilder(new Bundle())
                .putCharSequence(KEY_TITLE, title)
                .putCharSequence(KEY_DESCRIPTION, description)
                .build());
    }

    public DialogController(Bundle args) {
        super(args);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_dialog, container, false);
    }

    @Override
    public void onViewBound(@NonNull View view) {
        super.onViewBound(view);
//        tvTitle.setText(getArgs().getCharSequence(KEY_TITLE));
//        tvDescription.setText(getArgs().getCharSequence(KEY_DESCRIPTION));
//        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
    }

//    @OnClick(R.id.ok)
    public void dismissSuccess() {
        onResult(OK);
    }

//    @OnClick(R.id.dialog_window)
    public void dismissCancel() {
        onResult(CANCEL);
    }

    private void onResult(DialogCallback.Result result) {
        callback.onResult(result);
        callback = null;
        getRouter().popController(this);
    }

    @Override
    public boolean handleBack() {
        return super.handleBack();
    }

    public DialogController setDialogCallback(DialogCallback callback) {
        this.callback = callback;
        return this;
    }

    public interface DialogCallback {

        enum Result {
            OK, CANCEL
        }

        void onResult(Result result);
    }
}
