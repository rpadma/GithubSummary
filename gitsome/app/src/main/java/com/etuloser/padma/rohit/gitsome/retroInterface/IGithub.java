package com.etuloser.padma.rohit.gitsome.retroInterface;

import com.etuloser.padma.rohit.gitsome.model.user;
import com.etuloser.padma.rohit.gitsome.model.userdata;

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

   // @GET("users/{user}")
   // user getUser(@Path("user") String user);
}
