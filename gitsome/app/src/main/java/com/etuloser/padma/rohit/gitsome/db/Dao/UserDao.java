package com.etuloser.padma.rohit.gitsome.db.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.etuloser.padma.rohit.gitsome.db.model.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserEntity userEntity);

    @Update
    void updateUser(UserEntity userEntities);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table")
    LiveData<List<UserEntity>> getAllUsers();

}
