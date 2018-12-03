package com.khaledhoues.movies.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
@Entity(tableName = "article_table")
public class Article {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @Element(name = "title")
    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @Element(name = "description", data = true)
    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @Attribute(name = "url")
    @Path("enclosure")
    @NonNull
    @ColumnInfo(name = "image")
    private String image;

    @Element(name = "pubDate")
    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @Element(name = "link")
    @NonNull
    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "content")
    private String content;

    private String source;

    public Article() {
        author = "";
        content = "";
        source = "";
    }

    @Ignore
    public Article(int id, @NonNull String title, @NonNull String description, @NonNull String image, @NonNull String date, @NonNull String link) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.link = link;
    }

    @Ignore
    public Article(@NonNull String title, @NonNull String description, @NonNull String image, @NonNull String date, @NonNull String link) {
        super();
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.link = link;
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

    @NonNull
    public String getLink() {
        return link;
    }

    public void setLink(@NonNull String link) {
        this.link = link;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", date='" + date + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
