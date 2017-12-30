package com.etuloser.padma.rohit.gitsome.model;

import java.io.Serializable;

/**
 * Created by Rohit on 12/29/2017.
 */

public class user implements Serializable {

    String avatar_url;
    String name;
    String login;
    String url;
    String followers;
    String following;
    String location;
    String repos_url;
    String email;
    String public_repos;
    String created_at;
    String documentation_url;
    String message;


    @Override
    public String toString() {
        return "user{" +
                "avatar_url='" + avatar_url+ '\'' +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", url='" + url + '\'' +
                ", followers='" + followers + '\'' +
                ", following='" + following + '\'' +
                ", location='" + location + '\'' +
                ", repos_url='" + repos_url + '\'' +
                ", email='" + email + '\'' +
                ", public_repos='" + public_repos + '\'' +
                ", created_at='" + created_at + '\'' +
                ", documentation_url='" + documentation_url + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentation_url() {
        return documentation_url;
    }

    public void setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
    }


    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(String public_repos) {
        this.public_repos = public_repos;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
