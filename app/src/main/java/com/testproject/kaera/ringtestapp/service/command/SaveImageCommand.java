package com.testproject.kaera.ringtestapp.service.command;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.OutputStream;

import javax.inject.Inject;

import io.techery.janet.Command;
import io.techery.janet.command.annotations.CommandAction;
import rx.Observable;

@CommandAction
public class SaveImageCommand extends Command<Boolean> {

    @Inject Context context;

    private Bitmap bitmap;
    private String title;

    public SaveImageCommand(Bitmap bitmap, String title) {
        this.bitmap = bitmap;
        this.title = title;
    }

    @Override protected void run(CommandCallback<Boolean> callback) throws Throwable {
        if (bitmap == null) {
            callback.onFail(new Throwable("Bitmap is null"));
            return;
        }
        Observable.just(bitmap)
                .doOnNext(image -> {
                    ContentResolver contentResolver = context.getContentResolver();
                    String description = "ring app saved image";
                    insertImage(contentResolver, image, title, description);
                })
                .map(image -> true)
                .subscribe(callback::onSuccess, throwable -> callback.onFail(throwable));

    }

    private String insertImage(ContentResolver cr, Bitmap source, String title, String description) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);

            if (source != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }
            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }
        return stringUrl;
    }
}
