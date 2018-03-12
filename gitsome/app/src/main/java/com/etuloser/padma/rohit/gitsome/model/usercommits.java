package com.etuloser.padma.rohit.gitsome.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by rohitpadma on 3/11/18.
 */

public class usercommits implements Serializable {

    private int contributions;

    private int id;

    private String avatarUrl;

    private String login;

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
