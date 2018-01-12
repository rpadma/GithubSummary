package com.etuloser.padma.rohit.gitsome.model;

import java.io.Serializable;

/**
 * Created by rohitpadma on 1/7/18.
 */

public class userdata implements Serializable{


    String name;
    String created_at;
    String language;
    int stargazers_count;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }


}
