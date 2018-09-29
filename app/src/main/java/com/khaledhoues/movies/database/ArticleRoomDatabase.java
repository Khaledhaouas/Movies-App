package com.khaledhoues.movies.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.khaledhoues.movies.DAOs.ArticleDao;
import com.khaledhoues.movies.entities.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class ArticleRoomDatabase extends RoomDatabase {
    private static ArticleRoomDatabase INSTANCE;
    private static Callback sRoomDatabaseCallback =
            new Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    public static ArticleRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleRoomDatabase.class, "article_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ArticleDao articleDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ArticleDao mDao;

        PopulateDbAsync(ArticleRoomDatabase db) {
            mDao = db.articleDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Article article1 = new Article("Gal Gadot Joins Kenneth Branagh in Agatha Christie's Death on the Nile",
                    "Wonder Woman leading lady Gal Gadot will next be starring in the Agatha Christie book adaptation Death on the Nile from 20th Century Fox.",
                    "https://cdn3.movieweb.com/i/article/geSEHPoOgBRO1bZLSQtOyLOjxz8q2U/1200:100/Death-On-The-Nile-Cast-Gal-Gadot.jpg",
                    "Fri, 28 Sep 2018 17:33:44 PDT");
            mDao.insert(article1);

            Article article2 = new Article("Fred Savage to Reprise Princess Bride Role in Deadpool 2 PG-13 Rerelease",
                    "It is believed that Deadpool 2 is getting new bookend scenes that feature Ryan Reynolds reading his story to Fred Savage right before bedtime.",
                    "https://cdn3.movieweb.com/i/article/DpPWQITpW5vXFCpzuONPWOXsRDpZLP/1200:100/Deadpool-2-Pg-13-Rerelease-Fred-Savage-Princess.jpg",
                    "Fri, 28 Sep 2018 18:04:35 PDT");
            mDao.insert(article2);

            Article article3 = new Article("Holmes & Watson Trailer Has Will Ferrell and John C. Reilly on the Case",
                    "Sony has released the first trailer for their comedic take on Sherlock Holmes, Holmes and Watson, which arrives in theaters this Christmas.",
                    "https://cdn3.movieweb.com/i/article/h7yg9UuSJ249CRftjqkSYggiDdOa7q/1200:100/Holmes-And-Watson-Trailer-2018-Will-Ferrell-John.jpg",
                    "Fri, 28 Sep 2018 16:49:35 PDT");
            mDao.insert(article3);

            Article article4 = new Article("Under the Silver Lake Review: A Stylish and Bizarre Mystery",
                    "Andrew Garfield turns in a great performance in David Robert Mitchell's follow-up to It Follows with Under the Silver Lake.",
                    "https://cdn3.movieweb.com/i/article/VcNtRjg4gIhaR7zlAQIjOG3h4MmF8g/1200:100/Under-The-Silver-Lake-Review.jpg",
                    "Fri, 28 Sep 2018 13:15:57 PDT");
            mDao.insert(article4);


            mDao.insert(article1);
            mDao.insert(article2);
            mDao.insert(article3);
            mDao.insert(article4);

            mDao.insert(article1);
            mDao.insert(article2);
            mDao.insert(article3);
            mDao.insert(article4);

            mDao.insert(article1);
            mDao.insert(article2);
            mDao.insert(article3);
            mDao.insert(article4);
            return null;
        }
    }
}
