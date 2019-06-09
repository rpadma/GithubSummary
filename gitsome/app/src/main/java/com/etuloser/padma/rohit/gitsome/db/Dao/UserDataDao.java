package com.etuloser.padma.rohit.gitsome.db.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.etuloser.padma.rohit.gitsome.db.model.UserDataEntity;
import com.etuloser.padma.rohit.gitsome.db.model.UserEntity;
import com.etuloser.padma.rohit.gitsome.model.UserData;

import java.util.List;

@Dao
public interface UserDataDao {

    @Insert
    void insertUserData(UserDataEntity... userEntity);

    @Update
    void updateUserData(UserDataEntity... userEntity);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM userdata_table WHERE login=:login")
    List<UserData> findUserDataByUser(String login);
}
