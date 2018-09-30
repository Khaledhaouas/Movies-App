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

}
