package com.khaledhoues.movies.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.khaledhoues.movies.R;
import com.khaledhoues.movies.entities.Article;
import com.khaledhoues.movies.utils.SharedInformation;
import com.khaledhoues.movies.viewmodels.ArticlesViewModel;

public class ArticleDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ArticleDetailsActivity";

    private ArticlesViewModel mArticleViewModel;
    private Article mArticle;

    private ImageView mImgArticleThumbnail;
    private TextView mTxtArticleTitle;
    private TextView mTxtArticleDate;
    private TextView mTxtArticleAuthor;
    private TextView mTxtArticleContent;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_details_fragment);
        supportPostponeEnterTransition();

        mArticleViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        Bundle extras = getIntent().getExtras();
        mArticle = SharedInformation.getSharedArticle();

        mImgArticleThumbnail = (ImageView) findViewById(R.id.img_article_thumbnail);
        mTxtArticleTitle = (TextView) findViewById(R.id.txt_article_title);
        mTxtArticleDate = (TextView) findViewById(R.id.txt_article_date);
        mTxtArticleAuthor = (TextView) findViewById(R.id.txt_article_author);
        mTxtArticleContent = (TextView) findViewById(R.id.txt_card_content);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mTxtArticleTitle.setText(mArticle.getTitle());
        mTxtArticleDate.setText(mArticle.getDate());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = extras.getString("TransitionName");
            mImgArticleThumbnail.setTransitionName(imageTransitionName);
        }

        Glide.with(this)
                .load(mArticle.getImage())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        ((LinearLayout) findViewById(R.id.animated_layout)).setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        ((LinearLayout) findViewById(R.id.animated_layout)).setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(mImgArticleThumbnail);
        if (mArticle.getAuthor().isEmpty() || mArticle.getContent().isEmpty()) {
            mArticleViewModel.getArticle(mArticle.getId()).observe(this, new Observer<Article>() {
                @Override
                public void onChanged(@Nullable Article article) {
                    if (article != null) {
                        Log.e(TAG, "onChanged: ARTICLE " + article.toString());
                        if (!article.getAuthor().isEmpty()) {
                            mTxtArticleAuthor.setText(article.getAuthor());
                            mTxtArticleContent.setText(article.getContent());
                            mProgressBar.setVisibility(View.GONE);
                        }

                    }
                }
            });
        } else {
            mTxtArticleAuthor.setText(mArticle.getAuthor());
            mTxtArticleContent.setText(mArticle.getContent());
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Animation startFadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        startFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((CardView) findViewById(R.id.animated_card)).setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ((CardView) findViewById(R.id.animated_card)).startAnimation(startFadeOutAnimation);


        super.onBackPressed();
    }
}
