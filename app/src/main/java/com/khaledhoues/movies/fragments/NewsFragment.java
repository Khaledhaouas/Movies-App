package com.khaledhoues.movies.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.khaledhoues.movies.R;
import com.khaledhoues.movies.activities.ArticleDetailsActivity;
import com.khaledhoues.movies.adapters.CustomRecyclerViewAdapter;
import com.khaledhoues.movies.entities.Article;
import com.khaledhoues.movies.utils.SharedInformation;
import com.khaledhoues.movies.viewmodels.ArticlesViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {
    private ArticlesViewModel mArticleViewModel;

    private RecyclerView mRecyclerView;
    private CustomRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RefreshLayout refreshLayout;

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        mArticleViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();

        mAdapter = new CustomRecyclerViewAdapter(new ArrayList<Article>(), new CustomRecyclerViewAdapter.ArticleItemClickListener() {
            @Override
            public void onArticleItemClick(int adapterPosition, Article article, ImageView imgThumbnail) {
                Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
//                intent.putExtra("Article",  article.getImage());
                SharedInformation.setSharedArticle(article);
                intent.putExtra("TransitionName", "thumbnailTransition");

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        imgThumbnail,
                        "thumbnailTransition");

                startActivity(intent, options.toBundle());
            }
        });

        mRecyclerView.setAdapter(mAdapter);
//        ((RefreshLayout) rootView.findViewById(R.id.refreshLayout)).start(1000);
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mArticleViewModel.refreshArticlesList();
            }
        });

        mArticleViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                if (mAdapter != null) {
                    if (articles != null && articles.size() > 0 && !articles.get(0).getTitle().isEmpty()) {
                        mAdapter.setArticles(articles);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        ((RefreshLayout) rootView.findViewById(R.id.refreshLayout)).finishRefresh(1000);
                    }
                }

            }
        });


        return rootView;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

}
