package com.testproject.kaera.ringtestapp.controllers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.squareup.picasso.Picasso;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.controllers.DialogController.DialogCallback.Result;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.service.command.SaveImageCommand;
import com.testproject.kaera.ringtestapp.ui.util.SwipeLayoutProgressSwitcher;
import com.testproject.kaera.ringtestapp.util.BundleBuilder;
import com.testproject.kaera.ringtestapp.util.SimpleTargetCallback;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import butterknife.BindView;
import io.techery.janet.ActionPipe;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class GalleryController extends BaseController {

    private static final String KEY_URL = "key_url";
    private Bitmap loadedBitmap;

    @Inject ActionPipe<SaveImageCommand> saveImageCommand;

    @BindView(R.id.thumbnail_image) ImageView imgThumb;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;

    public GalleryController(String thumbUrl) {
        this(new BundleBuilder()
                .putCharSequence(KEY_URL, thumbUrl)
                .build());
        setHasOptionsMenu(true);
    }

    public GalleryController(Bundle args) {
        super(args);
    }

    @Override protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 0);
        bindPipe(saveImageCommand)
                .afterEach(new SwipeLayoutProgressSwitcher<>(refreshLayout))
                .onSuccess(command -> showToast(R.string.success_message_save_image))
                .onFail((command, throwable) -> showToast(R.string.error_message_save_image));
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_gallery, container, false);
    }

    @Override
    public void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        RingApplication.getComponent().inject(this);
        loadImage();
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this::loadImage);
    }

    private void loadImage() {
        Picasso.get()
                .load(getArgs().getCharSequence(KEY_URL).toString())
                .into(new SimpleTargetCallback() {
                    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        refreshLayout.setRefreshing(false);
                        loadedBitmap = bitmap;
                        imgThumb.setImageBitmap(bitmap);
                    }

                    @Override public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        super.onBitmapFailed(e, errorDrawable);
                        refreshLayout.setRefreshing(false);
                        showToast(R.string.error_message_load_image);
                    }

                    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
                        super.onPrepareLoad(placeHolderDrawable);
                        refreshLayout.setRefreshing(true);
                    }
                });
    }

    private void showToast(@StringRes int message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);
        if (changeType == ControllerChangeType.POP_ENTER && loadedBitmap == null) {
            loadImage();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            showDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        getRouter().pushController(RouterTransaction.with(new DialogController("Conductor", "Do you want to save image")
                .setDialogCallback(this::onResult))
                .popChangeHandler(new FadeChangeHandler())
                .pushChangeHandler(new FadeChangeHandler(false)));
    }

    private void onResult(Result result) {
        if (result == Result.OK) {
            saveImageCommand.send(new SaveImageCommand(loadedBitmap, getImageName(getArgs().getCharSequence(KEY_URL).toString())));
        }
    }

    private String getImageName(String url) {
        String result = "temporary_name";
        try {
            result = new URI(url).getPath().replaceAll("/", "");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }
}