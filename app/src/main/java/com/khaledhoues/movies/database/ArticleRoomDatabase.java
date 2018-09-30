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
//                    new PopulateDbAsync(INSTANCE).execute();
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

//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final ArticleDao mDao;
//
//        PopulateDbAsync(ArticleRoomDatabase db) {
//            mDao = db.articleDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            mDao.deleteAll();
//            Article article1 = new Article("Gal Gadot Joins Kenneth Branagh in Agatha Christie's Death on the Nile",
//                    "Wonder Woman leading lady Gal Gadot will next be starring in the Agatha Christie book adaptation Death on the Nile from 20th Century Fox.",
//                    "https://cdn3.movieweb.com/i/article/geSEHPoOgBRO1bZLSQtOyLOjxz8q2U/1200:100/Death-On-The-Nile-Cast-Gal-Gadot.jpg",
//                    "Fri, 28 Sep 2018 17:33:44 PDT",
//                    "");
//            mDao.insert(article1);
//
//            Article article2 = new Article("Fred Savage to Reprise Princess Bride Role in Deadpool 2 PG-13 Rerelease",
//                    "It is believed that Deadpool 2 is getting new bookend scenes that feature Ryan Reynolds reading his story to Fred Savage right before bedtime.",
//                    "https://cdn3.movieweb.com/i/article/DpPWQITpW5vXFCpzuONPWOXsRDpZLP/1200:100/Deadpool-2-Pg-13-Rerelease-Fred-Savage-Princess.jpg",
//                    "Fri, 28 Sep 2018 18:04:35 PDT",
//                    "");
//            mDao.insert(article2);
//            return null;
//        }
//    }
}
