package com.etuloser.padma.rohit.gitsome.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "usercommits_table")
public class UserCommitsEntity {

     @ColumnInfo(name = "contributions")
     int contributions;

     @ColumnInfo(name = "id")
     int id;

     @ColumnInfo(name = "avatarUrl")
     String avatarUrl;

     @ColumnInfo(name = "login")
     String login;
}
