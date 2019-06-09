package com.etuloser.padma.rohit.gitsome.db.gitsomedatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.etuloser.padma.rohit.gitsome.db.Dao.UserDao;
import com.etuloser.padma.rohit.gitsome.db.Dao.UserDataDao;
import com.etuloser.padma.rohit.gitsome.db.model.UserDataEntity;
import com.etuloser.padma.rohit.gitsome.db.model.UserEntity;
import com.etuloser.padma.rohit.gitsome.model.UserData;

@Database(entities = {UserEntity.class , UserDataEntity.class},version = 1)
public abstract class GitSomeDataBase extends RoomDatabase {
    public abstract UserDataDao userDataDao();
    public abstract UserDao userDao();

    private static volatile GitSomeDataBase instance;

    public static GitSomeDataBase getDatabase(final Context context){
        if(instance==null){
            synchronized (GitSomeDataBase.class){
                if(instance==null){
                    instance= Room.databaseBuilder(context.getApplicationContext(),GitSomeDataBase.class,"gitsome_database")
                            .addCallback(roomDatabaseCallback).build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomDatabaseCallback=new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}

