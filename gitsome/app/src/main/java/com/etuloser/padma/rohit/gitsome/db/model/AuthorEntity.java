package com.etuloser.padma.rohit.gitsome.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "author_table")
public class AuthorEntity {

     @ColumnInfo(name = "received_events_url")
     String received_events_url;

     @ColumnInfo(name = "organizations_url")
     String organizations_url;

     @ColumnInfo(name = "avatar_url")
     String avatar_url;

     @ColumnInfo(name = "gravatar_id")
     String gravatar_id;

     @ColumnInfo(name = "gists_url")
     String gists_url;

     @ColumnInfo(name = "starred_url")
     String starred_url;

     @ColumnInfo(name = "site_admin")
     String site_admin;

     @ColumnInfo(name = "type")
     String type;

     @ColumnInfo(name = "url")
     String url;

     @ColumnInfo(name = "id")
     String id;

     @ColumnInfo(name = "html_url")
     String html_url;

     @ColumnInfo(name = "following_url")
     String following_url;

     @ColumnInfo(name = "events_url")
     String events_url;

     @ColumnInfo(name = "login")
     String login;

     @ColumnInfo(name = "subscriptions_url")
     String subscriptions_url;

     @ColumnInfo(name = "repos_url")
     String repos_url;

     @ColumnInfo(name = "followers_url")
     String followers_url;
}
