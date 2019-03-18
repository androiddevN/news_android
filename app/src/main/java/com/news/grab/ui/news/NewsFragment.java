package com.news.grab.ui.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.news.grab.R;
import com.news.grab.databinding.NewsFragmentBinding;
import com.news.grab.model.Article;
import com.news.grab.model.Specification;
import com.news.grab.network.NewsApi;
import com.news.grab.ui.adapter.NewsAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterListener {
    public static final String PARAM_CATEGORY = "param-category";
    public static final String PARAM_LIST_STATE = "param-state";
    private final NewsAdapter newsAdapter = new NewsAdapter(null, this);
    private NewsApi.Category newsCategory;
    private NewsFragmentBinding binding;
    private Parcelable listState;

    public static NewsFragment newInstance(NewsApi.Category category) {
        NewsFragment fragment = new NewsFragment();
        if (category == null) {
            return fragment;
        }
        Bundle args = new Bundle();
        args.putString(PARAM_CATEGORY, category.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsCategory = NewsApi.Category
                    .valueOf(getArguments().getString(PARAM_CATEGORY));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.news_fragment, container, false);
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            binding.rvNewsPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            binding.rvNewsPosts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        binding.rvNewsPosts.setAdapter(newsAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(PARAM_LIST_STATE);
        }
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        Specification specs = new Specification();
        specs.setCategory(newsCategory);
        viewModel.getNewsHeadlines(specs).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    newsAdapter.setArticles(articles);
                    restoreRecyclerViewState();
                }
            }
        });
    }

    @Override
    public void onNewsItemClicked(Article article) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.PARAM_ARTICLE, article);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        binding.rvNewsPosts.setLayoutAnimation(controller);
        binding.rvNewsPosts.scheduleLayoutAnimation();
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().overridePendingTransition(R.anim.slide_up_animation, R.anim.fade_exit_transition);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (binding.rvNewsPosts.getLayoutManager() != null) {
            listState = binding.rvNewsPosts.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(PARAM_LIST_STATE, listState);
        }
    }

    private void restoreRecyclerViewState() {
        if (binding.rvNewsPosts.getLayoutManager() != null) {
            binding.rvNewsPosts.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}
