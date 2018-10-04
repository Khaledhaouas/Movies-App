package com.khaledhoues.movies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.khaledhoues.movies.entities.Article;
import com.khaledhoues.movies.repositories.ArticleRepository;

import java.util.List;

public class ArticlesViewModel extends AndroidViewModel {
    private static final String TAG = "ArticlesViewModel";
    private ArticleRepository mRepository;
    private LiveData<List<Article>> mAllArticles;

    public ArticlesViewModel(Application application) {
        super(application);
        mRepository = new ArticleRepository(application);
    }

    public LiveData<List<Article>> getAllArticles() {
        mAllArticles = mRepository.getAllArticles();
        return mAllArticles;
    }

    public void refreshArticlesList() {
        mRepository.getAllArticlesFromRSS();
    }

    public void insert(Article word) {
        mRepository.insert(word);
    }

    public LiveData<Article> getArticle(int id) {
        return mRepository.getArticle(id);
    }

}
