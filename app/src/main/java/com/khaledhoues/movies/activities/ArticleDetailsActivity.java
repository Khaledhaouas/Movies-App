package com.khaledhoues.movies.activities;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.khaledhoues.movies.R;
import com.khaledhoues.movies.entities.Article;
import com.khaledhoues.movies.utils.SharedInformation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class ArticleDetailsActivity extends AppCompatActivity {

    private Article mArticle;

    ImageView mImgArticleThumbnail;
    TextView mTxtArticleTitle;
    TextView mTxtArticleDate;
    TextView mTxtArticleAuthor;
    TextView mTxtArticleContent;

    private String author;
    private String content="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_details_fragment);
        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        mArticle = SharedInformation.getSharedArticle();

        mImgArticleThumbnail = (ImageView) findViewById(R.id.img_article_thumbnail);
        mTxtArticleTitle = (TextView) findViewById(R.id.txt_article_title);
        mTxtArticleDate = (TextView) findViewById(R.id.txt_article_date);
        mTxtArticleAuthor = (TextView) findViewById(R.id.txt_article_author);
        mTxtArticleContent = (TextView) findViewById(R.id.txt_card_content);

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
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(mImgArticleThumbnail);

        Content c = new Content();
        c.execute();

    }


    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect(mArticle.getLink()).get();

                author = document.getElementsByClass("article-header-author").first().text();
                Log.e("Author", "doInBackground: " + author);

                Elements elements = document.getElementsByClass("article-body").get(0).children();

                for (Element e : elements) {
                    if (e.hasText()) {
                        Log.e("Content", e.text());
                        content += e.text() + "\n\n";
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mTxtArticleAuthor.setText(author);
            mTxtArticleContent.setText(content);
        }
    }
}
