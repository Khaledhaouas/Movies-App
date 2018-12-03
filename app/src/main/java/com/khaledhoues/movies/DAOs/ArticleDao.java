package com.khaledhoues.movies.DAOs;

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

    @Query("SELECT * FROM article_table")
    List<Article> getAllArticles();

    @Query("UPDATE article_table SET author = :author, content = :content WHERE id = :id")
    void updateArticle(int id, String author, String content);

    @Query("SELECT * FROM article_table WHERE id = :id")
    Article getArticle(int id);
}
