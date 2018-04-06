package com.testproject.kaera.ringtestapp.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.controllers.HomeController.HomeScreen;
import com.testproject.kaera.ringtestapp.util.RecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerViewAdapter<HomeScreen, HomeAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;

    public HomeAdapter(Context context, HomeScreen[] items) {
        super(items);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.row_home, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position, getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_dot)
        ImageView imgDot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position, HomeScreen item) {
            this.tvTitle.setText(item.title);
            this.imgDot.getDrawable().setColorFilter(ContextCompat.getColor(context, item.color), PorterDuff.Mode.SRC_ATOP);
            ViewCompat.setTransitionName(tvTitle, context.getString(R.string.transition_tag_title_indexed, position));
            ViewCompat.setTransitionName(imgDot, context.getString(R.string.transition_tag_dot_indexed, position));
        }
    }
}