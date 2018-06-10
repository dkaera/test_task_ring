package com.testproject.kaera.ringtestapp.service.command

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import io.techery.janet.Command
import io.techery.janet.command.annotations.CommandAction
import rx.Observable
import rx.functions.Action1
import javax.inject.Inject

@CommandAction
class SaveImageCommand constructor(var bitmap: Bitmap?, var title: String) : Command<Boolean>() {

    @Inject
    lateinit var context: Context

    override fun run(callback: CommandCallback<Boolean>) {
        if (bitmap == null) {
            callback.onFail(Throwable("Bitmap is null"))
            return
        }
        Observable.just<Bitmap>(bitmap)
                .doOnNext { image ->
                    val contentResolver = context.contentResolver
                    val description = "ring app saved image"
                    insertImage(contentResolver, image, this.title, description)
                }
                .map { image -> true }
                .subscribe(Action1<Boolean> { callback.onSuccess(it) },
                        Action1<Throwable> { callback.onFail(it) })

    }

    private fun insertImage(cr: ContentResolver, source: Bitmap?, title: String, description: String): String? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, title)
        values.put(MediaStore.Images.Media.DESCRIPTION, description)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        var url: Uri? = null
        var stringUrl: String? = null    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values)

            if (source != null) {
                val imageOut = cr.openOutputStream(url!!)
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut)
                } finally {
                    imageOut!!.close()
                }
            } else {
                cr.delete(url!!, null, null)
                url = null
            }
        } catch (e: Exception) {
            if (url != null) {
                cr.delete(url, null, null)
                url = null
            }
        }

        if (url != null) {
            stringUrl = url.toString()
        }
        return stringUrl
    }
}