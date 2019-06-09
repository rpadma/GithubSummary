package com.etuloser.padma.rohit.gitsome.activities.Profile;

import android.util.Log;
import android.util.Pair;

import com.etuloser.padma.rohit.gitsome.model.UserAndRepo;
import com.etuloser.padma.rohit.gitsome.model.UserCommits;
import com.etuloser.padma.rohit.gitsome.model.commitmodel.CommitData;
import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.service.GithubService;
import com.etuloser.padma.rohit.gitsome.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter implements ProfileContract.Presenter {

    ProfileContract.View view;
    private IGithub githubService;
    CompositeDisposable reposDisposable;
    String githubToken = Constants.token;
    HashMap<String, ArrayList<CommitData>> hcommitdata=new HashMap<>();
    HashMap<String,Integer> repocommits=new HashMap<>();
    UserAndRepo uar;


    public ProfilePresenter(ProfileContract.View view, UserAndRepo uar) {
        this.view=view;
        githubService = (IGithub) GithubService.createGithubService(githubToken);
        reposDisposable=new CompositeDisposable();
        this.uar=uar;
    }

    @Override
    public void getcontributors(ArrayList<String> repolist) {

        reposDisposable.add( Observable.fromIterable(repolist)
                .flatMap(new Function<String, ObservableSource<Pair<String,ArrayList<UserCommits>>>>() {
                             @Override
                             public ObservableSource<Pair<String,ArrayList<UserCommits>>> apply(String s) throws Exception {
                                 return Observable.zip(
                                         Observable.just(s),
                                         githubService.getcontributors(uar.getU().getLogin(), s), new BiFunction<String, ArrayList<UserCommits>, Pair<String, ArrayList<UserCommits>>>() {
                                             @Override
                                             public Pair<String, ArrayList<UserCommits>> apply(String s, ArrayList<UserCommits> usercommits) throws Exception {
                                                 return new Pair<String,ArrayList<UserCommits>>(s, usercommits);
                                             }
                                         }

                                 );
                             }
                         }


                ).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Pair<String, ArrayList<UserCommits>>>() {


                    @Override
                    public void onNext(Pair<String, ArrayList<UserCommits>> stringArrayListPair) {
                        for(UserCommits uc:stringArrayListPair.second)
                        {
                            if(uc.getLogin().equals(uar.getU().getLogin()))
                            {
                                repocommits.put(stringArrayListPair.first,uc.getContributions());
                                break;
                            }
                        }

                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {
                        view.bindrepocommit(repocommits);
                    }
                })

        );
    }

    @Override
    public void getrepodata(ArrayList<String> repolist) {

        reposDisposable.add(Observable.fromIterable(repolist)
                .flatMap(new Function<String, ObservableSource<Pair<String,ArrayList<CommitData>>>>() {
                    @Override
                    public ObservableSource<Pair<String,ArrayList<CommitData>>> apply(String s) throws Exception {

                        Log.d("in observable source",s);
                        return Observable.zip(
                                Observable.just(s),
                                githubService.getOcommitdata("rpadma",s),
                                new BiFunction<String, ArrayList<CommitData>, Pair<String, ArrayList<CommitData>>>() {
                                    @Override
                                    public Pair<String, ArrayList<CommitData>> apply(@NonNull String id, @NonNull ArrayList<CommitData> productResponse) throws Exception {
                                        Log.d("in function",id);
                                        return new Pair<String, ArrayList<CommitData>>(id, productResponse);
                                    }
                                });
                    }}).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Pair<String, ArrayList<CommitData>>>(){

                    @Override
                    public void onNext(Pair<String, ArrayList<CommitData>> userArrayListPair) {

                        hcommitdata.put(userArrayListPair.first,userArrayListPair.second);
                        Log.d("demo",userArrayListPair.first.toString() +" "+String.valueOf(userArrayListPair.second.size()));

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
}
