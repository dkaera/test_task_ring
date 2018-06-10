package com.testproject.kaera.ringtestapp.controllers

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testproject.kaera.ringtestapp.R
import com.testproject.kaera.ringtestapp.controllers.base.BaseController
import com.testproject.kaera.ringtestapp.util.BundleBuilder
import kotlinx.android.synthetic.main.controller_dialog.view.*
import rx.functions.Action1


class DialogController(args: Bundle?) : BaseController(args) {

    companion object {
        const val KEY_TITLE = "DialogController.title"
        const val KEY_DESCRIPTION = "DialogController.description"
    }

    var dialogCallback : Action1<Boolean> = Action1 {  }

    constructor(title: CharSequence, description: CharSequence) : this(BundleBuilder(Bundle())
            .putCharSequence(KEY_TITLE, title)
            .putCharSequence(KEY_DESCRIPTION, description)
            .build())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_dialog, container, false)
        view.ok.setOnClickListener({onResult(true)})
        view.dialog_window.setOnClickListener({onResult(false)})
        view.tv_title.text = args.getCharSequence(KEY_TITLE)
        view.tv_description.text = args.getCharSequence(KEY_DESCRIPTION)
        view.tv_description.movementMethod = LinkMovementMethod.getInstance()
        return view
    }

    private fun onResult(isSuccess: Boolean) {
        dialogCallback.call(isSuccess)
        router.popController(this)
    }

    fun setDialogCallback(dialogCallback: Action1<Boolean>) : DialogController {
        this.dialogCallback = dialogCallback
        return this
    }
}