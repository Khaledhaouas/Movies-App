package com.khaledhoues.movies.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.khaledhoues.movies.R;
import com.khaledhoues.movies.adapters.CustomRecyclerViewAdapter;
import com.khaledhoues.movies.entities.Article;
import com.khaledhoues.movies.utils.SharedInformation;
import com.khaledhoues.movies.viewmodels.ArticlesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArticlesViewModel mArticleViewModel;

    private RecyclerView mRecyclerView;
    private CustomRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mArticleViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);


        mLayoutManager = new LinearLayoutManager(this);
        setRecyclerViewLayoutManager();


        mAdapter = new CustomRecyclerViewAdapter(new ArrayList<Article>(), new CustomRecyclerViewAdapter.ArticleItemClickListener() {
            @Override
            public void onArticleItemClick(int adapterPosition, Article article, ImageView imgThumbnail) {
                Intent intent = new Intent(MainActivity.this, ArticleDetailsActivity.class);
//                intent.putExtra("Article",  article.getImage());
                SharedInformation.setSharedArticle(article);
                intent.putExtra("TransitionName", "thumbnailTransition");

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        imgThumbnail,
                        "thumbnailTransition");

                startActivity(intent, options.toBundle());
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mArticleViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                mAdapter.setArticles(articles);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mArticleViewModel.refreshArticlesList();
            }
        });


    }


    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }
}
