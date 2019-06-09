package com.etuloser.padma.rohit.gitsome.activities.Main;

import android.util.Log;

import com.etuloser.padma.rohit.gitsome.model.User;
import com.etuloser.padma.rohit.gitsome.model.UserAndRepo;
import com.etuloser.padma.rohit.gitsome.model.UserData;
import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.service.GithubService;
import com.etuloser.padma.rohit.gitsome.util.Constants;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    private IGithub githubService;
    Observable<UserAndRepo> combined;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        String githubToken = Constants.token;
        githubService = (IGithub) GithubService.createGithubService(githubToken);
    }

    @Override
    public void getUserData(String username) {

        Observable<User> userObservable = githubService.getOUser(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ArrayList<UserData>> repoObservable = githubService.getOUserData(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        combined = Observable.zip(userObservable, repoObservable, new BiFunction<User, ArrayList<UserData>, UserAndRepo>() {
            @Override
            public UserAndRepo apply(User u, ArrayList<UserData> userdata) throws Exception {

                Log.d("User",u.toString());
                Log.d("userdata",userdata.toString());
                UserAndRepo urobject=new UserAndRepo();
                urobject.setU(u);
                urobject.setRepo(userdata);
                return urobject;

            }
        });

        combined.subscribe(new Observer<UserAndRepo>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(UserAndRepo userandrepo) {
                                   view.Senduser(userandrepo);
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
        );
    }
}
