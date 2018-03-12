package com.etuloser.padma.rohit.gitsome.retroInterface;

import com.etuloser.padma.rohit.gitsome.model.commitmodel.commitdata;
import com.etuloser.padma.rohit.gitsome.model.user;
import com.etuloser.padma.rohit.gitsome.model.usercommits;
import com.etuloser.padma.rohit.gitsome.model.userdata;

import org.eclipse.egit.github.core.Contributor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rohit on 12/29/2017.
 */

public interface IGithub {


    @GET("/users/{user}")
    Observable<user> getOUser(@Path("user") String user);


   @GET("/users/{user}/repos")
    Observable<ArrayList<userdata>> getOUserData(@Path("user") String user);


   @GET("repos/{user}/{reponame}/commits")
    Observable<ArrayList<commitdata>> getOcommitdata(@Path("user") String user,@Path("reponame") String reponame);


    @GET("/repos/{user}/{reponame}/contributors")
    Observable<ArrayList<usercommits>> getcontributors(
            @Path("user") String user, @Path("reponame") String reponame);

   // @GET("users/{user}")
   // user getUser(@Path("user") String user);
}
