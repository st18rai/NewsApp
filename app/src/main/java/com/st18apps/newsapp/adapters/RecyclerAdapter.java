package com.st18apps.newsapp.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.st18apps.newsapp.R;
import com.st18apps.newsapp.model.Article;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Article> articleArrayList;

    public RecyclerAdapter(List<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(articleArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        if (articleArrayList == null)
            return 0;
        return articleArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView date;
        ImageView titleImage;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            time = itemView.findViewById(R.id.textView_time);
            date = itemView.findViewById(R.id.textView_date);
            titleImage = itemView.findViewById(R.id.imageView);
        }

        public void bind(Article article) {


            String newsDate = article.getPublishedAt().substring(0,10);
            String newsTime = article.getPublishedAt().substring(11,16);

            title.setText(article.getTitle());
            date.setText(newsDate);
            time.setText(newsTime);

            if (article.getUrlToImage() != null && article.getUrlToImage().contains("http")) {
                Uri uri = Uri.parse(article.getUrlToImage());
                Glide.with(itemView.getContext())
                        .load(uri)
                        .fitCenter()
                        .thumbnail(0.5f)
                        .priority(Priority.IMMEDIATE)
                        .placeholder(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(titleImage);
            }
        }
    }
}
