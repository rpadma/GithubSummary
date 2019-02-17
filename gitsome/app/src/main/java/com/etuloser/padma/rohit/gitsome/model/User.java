package com.etuloser.padma.rohit.gitsome.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Rohit on 12/29/2017.
 */

public class User implements Serializable, Parcelable {

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


    protected User(Parcel in) {
        avatar_url = in.readString();
        name = in.readString();
        login = in.readString();
        url = in.readString();
        followers = in.readString();
        following = in.readString();
        location = in.readString();
        repos_url = in.readString();
        email = in.readString();
        public_repos = in.readString();
        created_at = in.readString();
        documentation_url = in.readString();
        message = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
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

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar_url);
        dest.writeString(name);
        dest.writeString(login);
        dest.writeString(url);
        dest.writeString(followers);
        dest.writeString(following);
        dest.writeString(location);
        dest.writeString(repos_url);
        dest.writeString(email);
        dest.writeString(public_repos);
        dest.writeString(created_at);
        dest.writeString(documentation_url);
        dest.writeString(message);
    }
}
