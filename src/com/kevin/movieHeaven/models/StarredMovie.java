package com.kevin.movieHeaven.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "starred_movies")
public class StarredMovie extends Model {

    @Column(name = "name")
    public String name;

    @Column(name = "url")
    public String url;

    public StarredMovie() {
        super();
    }

    public StarredMovie(String name, String url) {
        super();
        this.name = name;
        this.url = url;
    }
}
