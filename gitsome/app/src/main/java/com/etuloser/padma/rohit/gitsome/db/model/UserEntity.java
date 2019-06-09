package com.etuloser.padma.rohit.gitsome.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user_table")
public class UserEntity {

    @ColumnInfo(name = "avatar_url")
    public String avatar_url;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "login")
    public String login;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "followers")
    public String followers;

    @ColumnInfo(name = "following")
    public String following;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "repos_url")
    public String repos_url;

    @NonNull
    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "public_repos")
    public String public_repos;

    @ColumnInfo(name = "created_at")
    public String created_at;

    @ColumnInfo(name = "documentation_url")
    public String documentation_url;

    @ColumnInfo(name = "message")
    public String message;

}
