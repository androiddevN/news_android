package com.news.grab.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;

import com.news.grab.R;
import com.news.grab.data.NewsRepository;
import com.news.grab.databinding.ActivityDetailBinding;
import com.news.grab.model.Article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


public class DetailActivity extends AppCompatActivity {
    public static final String PARAM_ARTICLE = "param-article";
    private ActivityDetailBinding binding;
    private Article article;
    private NewsRepository newsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        makeUiFullscreen();
        initView();
        setupArticleAndListener();
        setupToolbar();
        newsRepository = NewsRepository.getInstance(this);
    }

    private void initView(){
        binding.articleWebview.getSettings().setJavaScriptEnabled(true);
        binding.articleWebview.getSettings().setAppCacheEnabled(true);
        binding.articleWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }


    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            if(article!=null && article.getSource()!=null)
            getSupportActionBar().setTitle(article.getSource().getName());
        }
    }

    private void makeUiFullscreen() {
        // When applying fullscreen layout, transparent bar works only for VERSION < 21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            binding.getRoot().setFitsSystemWindows(true);
        }
        // Make UI fullscreen and make it load stable
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    /**
     * Extracts Article from Arguments and Adds button listeners
     */
    private void setupArticleAndListener() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(PARAM_ARTICLE)) {
            final Article article = bundle.getParcelable(PARAM_ARTICLE);
            if (article != null) {
                this.article = article;
                binding.articleWebview.loadUrl(article.getUrl());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_enter_transition, R.anim.slide_down_animation);
    }
}