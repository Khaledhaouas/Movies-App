package com.khaledhoues.movies.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.khaledhoues.movies.entities.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    void insert(Article article);

    @Query("DELETE FROM article_table")
    void deleteAll();

    @Query("SELECT * from article_table")
    LiveData<List<Article>> getAllArticles();
}
