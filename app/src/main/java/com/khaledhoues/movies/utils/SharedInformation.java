package com.khaledhoues.movies.utils;

import com.khaledhoues.movies.entities.Article;

public class SharedInformation {
    private static Article sharedArticle = new Article();

    public static Article getSharedArticle() {
        return sharedArticle;
    }

    public static void setSharedArticle(Article sharedArticle) {
        SharedInformation.sharedArticle = sharedArticle;
    }
}
