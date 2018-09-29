package com.khaledhoues.movies.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "article_table")
public class Article {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "image")
    private String image;

    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    public Article() {
    }

    public Article(int id, @NonNull String title, @NonNull String description, @NonNull String image, @NonNull String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
    }

    public Article(@NonNull String title, @NonNull String description, @NonNull String image, @NonNull String date) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
