package com.testproject.kaera.ringtestapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.testproject.kaera.ringtestapp.R
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import com.testproject.kaera.ringtestapp.ui.TopListAdapter.ViewHolder
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_top_item.*
import rx.functions.Action1
import java.text.SimpleDateFormat

class TopListAdapter : RecyclerViewAdapter<APIRedditItem, ViewHolder> {

    private val dateFormat = SimpleDateFormat("dd MMM HH:mm")
    private val commentsPrefix = "%d comments"
    private val authorPrefix = "submitted by %s"

    private lateinit var thumbnailClickAction: Action1<APIRedditItem>

    constructor() : super(null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.row_top_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var item: APIRedditItem? = null

        internal fun bind(item: APIRedditItem?) {
            this.item = item
            tv_title.text = item!!.title
            comment_count.text = String.format(commentsPrefix, item.commentsCount)
            submitted_by.text = String.format(authorPrefix, item.author)
            time.text = dateFormat.format(item.createdDate)
            Picasso.get().load(item.thumbnail).fit().centerInside().into(thumbnail_image)
            thumbnail_image.setOnClickListener { thumbnailClickAction?.call(item) }
        }
    }

    fun setThumbnailClickListener(action: Action1<APIRedditItem>) {
        this.thumbnailClickAction = action
    }

    fun setData(items: List<APIRedditItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}