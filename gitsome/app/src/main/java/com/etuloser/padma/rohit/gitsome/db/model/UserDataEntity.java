package com.etuloser.padma.rohit.gitsome.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "userdata_table",foreignKeys = @ForeignKey(entity = UserEntity.class,
        parentColumns = "login",
        childColumns = "login"))
public class UserDataEntity {

    @PrimaryKey
    public int id;

    @ColumnInfo(name="login")
    public String login;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "created_at")
    public String created_at;

    @ColumnInfo(name = "language")
    public String language;

    @ColumnInfo(name = "stargazers_count")
    public int stargazers_count;

}
