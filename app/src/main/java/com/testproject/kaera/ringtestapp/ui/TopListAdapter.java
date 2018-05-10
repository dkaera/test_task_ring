package com.testproject.kaera.ringtestapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;
import com.testproject.kaera.ringtestapp.ui.util.RecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class TopListAdapter extends RecyclerViewAdapter<APIRedditItem, TopListAdapter.ViewHolder> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM HH:mm");
    private static final String commentsPrefix = "%d comments";
    private static final String authorPrefix = "submitted by %s";

    private OnThumbnailClick thumbnailClickListener = item -> {
        // empty
    };

    public TopListAdapter() {
        super(null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.row_top_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.tv_title) TextView tvTitle;
//        @BindView(R.id.comment_count) TextView tvCommentCount;
//        @BindView(R.id.submitted_by) TextView tvSubmitted;
//        @BindView(R.id.time) TextView tvTime;
//        @BindView(R.id.thumbnail_image) ImageView imgThumb;

        private APIRedditItem item;

        public ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }

        void bind(APIRedditItem item) {
            this.item = item;
//            this.tvTitle.setText(item.getTitle());
//            this.tvCommentCount.setText(String.format(commentsPrefix, item.getCommentsCount()));
//            this.tvSubmitted.setText(String.format(authorPrefix, item.getAuthor()));
//            this.tvTime.setText(dateFormat.format(item.getCreatedDate()));
//            Picasso.get().load(item.getThumbnail()).fit().centerInside().into(this.imgThumb);
        }

//        @OnClick(R.id.thumbnail_image)
        public void onThumbnailClick() {
            thumbnailClickListener.onClick(item);
        }
    }

    public void setThumbnailClickListener(OnThumbnailClick thumbnailClickListener) {
        this.thumbnailClickListener = thumbnailClickListener;
    }

    public void setData(List<APIRedditItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnThumbnailClick {

        void onClick(APIRedditItem item);
    }
}