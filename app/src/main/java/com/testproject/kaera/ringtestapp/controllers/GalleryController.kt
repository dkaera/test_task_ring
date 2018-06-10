package com.testproject.kaera.ringtestapp.controllers

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.StringRes
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.squareup.picasso.Picasso
import com.testproject.kaera.ringtestapp.R
import com.testproject.kaera.ringtestapp.controllers.base.BaseController
import com.testproject.kaera.ringtestapp.service.command.SaveImageCommand
import com.testproject.kaera.ringtestapp.ui.util.SwipeLayoutProgressSwitcher
import com.testproject.kaera.ringtestapp.util.BundleBuilder
import com.testproject.kaera.ringtestapp.util.SimpleTargetCallback
import com.testproject.kaera.ringtestapp.util.getComponent
import io.techery.janet.ActionPipe
import kotlinx.android.synthetic.main.controller_gallery.view.*
import java.lang.Exception
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Inject

class GalleryController(args: Bundle?) : BaseController(args) {

    companion object {
        var KEY_URL: String = "key_url"
    }

    private var loadedBitmap: Bitmap? = null

    @Inject
    lateinit var saveImageCommand: ActionPipe<SaveImageCommand>

    constructor(thumbUrl: String) : this(BundleBuilder()
            .putCharSequence(KEY_URL, thumbUrl)
            .build()) {
        setHasOptionsMenu(false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), 0)
        bindPipe(saveImageCommand)
                .afterEach(SwipeLayoutProgressSwitcher(view.refresh_layout))
                .onSuccess({ showToast(R.string.success_message_save_image) })
                .onFail { t1, t2 -> showToast(R.string.error_message_save_image) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_gallery, container, false)
        applicationContext!!.getComponent().inject(this)
        loadImage()
        view.refresh_layout.setColorSchemeResources(R.color.colorAccent)
        view.refresh_layout.setOnRefreshListener(this::loadImage)
        return view
    }

    private fun loadImage() {
        Picasso.get()
                .load(args.getCharSequence(KEY_URL)?.toString())
                .resize((480 * resources?.displayMetrics?.density!!).toInt(),
                        (640 * resources?.displayMetrics?.density!!).toInt())
                .centerCrop()
                .into(object : SimpleTargetCallback() {
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        view?.refresh_layout?.isRefreshing = false
                        showToast(R.string.error_message_load_image)
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        view?.refresh_layout?.isRefreshing = false
                        loadedBitmap = bitmap
                        view?.thumbnail_image?.setImageBitmap(bitmap)
                    }
                })
    }

    private fun showToast(@StringRes message: Int) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        if (changeType == ControllerChangeType.POP_ENTER && loadedBitmap == null) {
            loadImage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            showDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        router.pushController(RouterTransaction.with(DialogController("Conductor", "Do you want to save image")
                .setDialogCallback({ onResult(it) }))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler(false)))
    }

    private fun onResult(result: DialogController.DialogCallback.Result) {
        if (result == DialogController.DialogCallback.Result.OK) {
            val title = getImageName(args.getCharSequence(KEY_URL).toString())
            saveImageCommand.send(SaveImageCommand(loadedBitmap, title))
        }
    }

    private fun getImageName(url: String): String {
        var result = "temporary_name"
        try {
            result = URI(url).path.replace("/".toRegex(), "")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        return result
    }
}