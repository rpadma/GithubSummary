package com.etuloser.padma.rohit.gitsome.retroInterface;

import com.etuloser.padma.rohit.gitsome.model.User;
import com.etuloser.padma.rohit.gitsome.model.commitmodel.CommitData;
import com.etuloser.padma.rohit.gitsome.model.UserCommits;
import com.etuloser.padma.rohit.gitsome.model.UserData;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rohit on 12/29/2017.
 */

public interface IGithub {


    @GET("/users/{User}")
    Observable<User> getOUser(@Path("User") String user);


   @GET("/users/{User}/repos")
    Observable<ArrayList<UserData>> getOUserData(@Path("User") String user);


   @GET("repos/{User}/{reponame}/commits")
    Observable<ArrayList<CommitData>> getOcommitdata(@Path("User") String user, @Path("reponame") String reponame);


    @GET("/repos/{User}/{reponame}/contributors")
    Observable<ArrayList<UserCommits>> getcontributors(
            @Path("User") String user, @Path("reponame") String reponame);

   // @GET("users/{User}")
   // User getUser(@Path("User") String User);
}
