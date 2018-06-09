package com.testproject.kaera.ringtestapp.controllers

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.RouterTransaction
import com.testproject.kaera.ringtestapp.R
import com.testproject.kaera.ringtestapp.controllers.base.BaseController
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand
import com.testproject.kaera.ringtestapp.ui.TopListAdapter
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewWrapper
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewWrapper.EndlessCallback
import com.testproject.kaera.ringtestapp.ui.util.SwipeLayoutProgressSwitcher
import com.testproject.kaera.ringtestapp.util.getComponent
import io.techery.janet.ActionPipe
import kotlinx.android.synthetic.main.controller_top_list.view.*
import rx.functions.Action1
import javax.inject.Inject

/**
 * Created by Dmitriy Puzak on 5/11/18.
 */
class TopListController(args: Bundle?) : BaseController(args) {

    val THRESHOLD = 5

    private lateinit var adapter: TopListAdapter
    private lateinit var recyclerViewWrapper: RecyclerViewWrapper

    @Inject
    lateinit var getTopSubredditPipe: ActionPipe<GetTopSubredditCommand>

    override fun onAttach(view: View) {
        super.onAttach(view)
        bindPipe(getTopSubredditPipe)
                .afterEach(SwipeLayoutProgressSwitcher(view.refresh_layout))
                .onSuccess({ this.putData(it) })
        loadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        applicationContext!!.getComponent().inject(this)
        val view = inflater.inflate(R.layout.controller_top_list, container, false)
        recyclerViewWrapper = RecyclerViewWrapper(view.recycler_view)
        adapter = TopListAdapter()
        adapter.setThumbnailClickListener(Action1 { onThumbnailClick(it) })
        recyclerViewWrapper.setAdapter(adapter)
        recyclerViewWrapper.setEndlessCallback(EndlessCallback(THRESHOLD) {
            val itemCount = adapter.itemCount
            val name = adapter.getItem(itemCount - 1)!!.name
            loadNext(name, itemCount)
        })
        view.refresh_layout.setColorSchemeResources(R.color.colorAccent)
        view.refresh_layout.setOnRefreshListener({ this.loadData() })
        return view
    }

    private fun onThumbnailClick(model: APIRedditItem) {
        if (TextUtils.isEmpty(model.thumbFullSize)) return
        router.pushController(RouterTransaction.with(GalleryController(model.thumbFullSize)))
    }

    private fun loadData() {
        getTopSubredditPipe.send(GetTopSubredditCommand(null, 0, true))
    }

    private fun loadNext(afterId: String, count: Int) {
        getTopSubredditPipe.send(GetTopSubredditCommand(afterId, count, false))
    }

    private fun putData(command: GetTopSubredditCommand) {
        adapter.setData(command.result)
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        recyclerViewWrapper.saveViewState(outState)
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        recyclerViewWrapper.restoreViewState(savedViewState)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        recyclerViewWrapper.onDestroyView()
    }
}