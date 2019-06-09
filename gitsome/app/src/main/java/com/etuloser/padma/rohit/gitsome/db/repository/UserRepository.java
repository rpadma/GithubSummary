package com.etuloser.padma.rohit.gitsome.db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.etuloser.padma.rohit.gitsome.db.Dao.UserDao;
import com.etuloser.padma.rohit.gitsome.db.gitsomedatabase.GitSomeDataBase;
import com.etuloser.padma.rohit.gitsome.db.model.UserEntity;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<UserEntity>> users;

    public UserRepository(Application application){
        GitSomeDataBase gitSomeDataBase=GitSomeDataBase.getDatabase(application);
        userDao=gitSomeDataBase.userDao();
        users=userDao.getAllUsers();

    }

    public LiveData<List<UserEntity>> getAllUsers(){
        return users;
    }

    public void insert(UserEntity userEntity){
        new insertAsynctask(userDao).execute(userEntity);
    }

    private static class insertAsynctask extends AsyncTask<UserEntity,Void,Void> {

        private UserDao userAsyncDao;

        insertAsynctask(UserDao userDao){
            userAsyncDao=userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... userEntities) {
            userAsyncDao.insertUser(userEntities[0]);
            return null;
        }
    }
}
