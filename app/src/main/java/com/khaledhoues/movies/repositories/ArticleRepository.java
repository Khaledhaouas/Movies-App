package com.khaledhoues.movies.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.khaledhoues.movies.DAOs.ArticleDao;
import com.khaledhoues.movies.database.ArticleRoomDatabase;
import com.khaledhoues.movies.entities.Article;

import java.util.List;

public class ArticleRepository {

    private ArticleDao mArticleDao;
    private LiveData<List<Article>> mAllArticles;

    public ArticleRepository(Application application) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getDatabase(application);
        mArticleDao = db.articleDao();
        mAllArticles = mArticleDao.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    public void insert(Article article) {
        new insertAsyncTask(mArticleDao).execute(article);
    }

    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {

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

}
