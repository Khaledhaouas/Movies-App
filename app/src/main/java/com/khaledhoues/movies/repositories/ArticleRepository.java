package com.khaledhoues.movies.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.khaledhoues.movies.DAOs.ArticleDao;
import com.khaledhoues.movies.database.ArticleRoomDatabase;
import com.khaledhoues.movies.entities.Article;
import com.khaledhoues.movies.entities.RSSFeed;
import com.khaledhoues.movies.webservices.RssWebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ArticleRepository {
    private static final String TAG = "ArticleRepository";

    private static final String BASE_URL = "https://movieweb.com/movie-news/";

    private ArticleDao mArticleDao;
    private LiveData<List<Article>> mAllArticles;

    public ArticleRepository(Application application) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getDatabase(application);
        mArticleDao = db.articleDao();
        mAllArticles = mArticleDao.getAllArticles();

        getAllArticlesFromRSS();
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    public void insert(Article article) {
        new insertAsyncTask(mArticleDao).execute(article);
    }

    private class insertAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        insertAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insertAll(List<Article> articles) {
        new insertAllAsyncTask(mArticleDao).execute(articles);
    }

    private class insertAllAsyncTask extends AsyncTask<List<Article>, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        insertAllAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(final List<Article>... params) {

            mArticleDao.deleteAll();
            for (Article article : params[0]) {
                mAsyncTaskDao.insert(article);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAllArticles = mArticleDao.getAllArticles();
        }
    }


    public void getAllArticlesFromRSS() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create()).build();

        RssWebService rssWebService = retrofit.create(RssWebService.class);

        Call<RSSFeed> call = rssWebService.loadRSSFeed();
        call.enqueue(new Callback<RSSFeed>() {
            @Override
            public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
                if (response.isSuccessful()) {
                    RSSFeed rss = response.body();
                    assert rss != null;
                    System.out.println("Channel title: " + rss.getChannelTitle());

                    for (Article article : rss.getArticleList()) {
                        Log.e(TAG, article.toString());
                    }
                    insertAll(rss.getArticleList());
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<RSSFeed> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
