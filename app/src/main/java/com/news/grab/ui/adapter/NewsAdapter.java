package com.news.grab.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.news.grab.R;
import com.news.grab.databinding.NewsItemBinding;
import com.news.grab.model.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private final NewsAdapterListener listener;
    private List<Article> articles;
    private LayoutInflater layoutInflater;

    public NewsAdapter(List<Article> articles, NewsAdapterListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        NewsItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.news_item, parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        newsViewHolder.binding.setNews(articles.get(i));
        newsViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public void setArticles(List<Article> articles) {
        if (articles != null) {
            this.articles = articles;
            notifyDataSetChanged();
        }
    }

    public interface NewsAdapterListener {
        void onNewsItemClicked(Article article);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final NewsItemBinding binding;

        public NewsViewHolder(final NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = this.getAdapterPosition();
            if (listener!=null) {
                listener.onNewsItemClicked(articles.get(index));
            }
        }
    }
}
