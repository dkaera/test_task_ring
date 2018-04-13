package com.testproject.kaera.ringtestapp.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.util.BundleBuilder;

import butterknife.BindView;

public class GalleryController extends BaseController {

    private static final String KEY_URL = "key_url";

    @BindView(R.id.thumbnail_image) ImageView imgThumb;

    public GalleryController(String thumbUrl) {
        this(new BundleBuilder(new Bundle())
                .putCharSequence(KEY_URL, thumbUrl)
                .build());
    }

    public GalleryController(Bundle args) {
        super(args);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_gallery, container, false);
    }

    @Override
    public void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        Picasso.get().load(getArgs().getCharSequence(KEY_URL).toString()).fit().centerInside().into(this.imgThumb);
    }
}
