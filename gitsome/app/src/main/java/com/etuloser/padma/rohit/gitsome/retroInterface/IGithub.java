package com.etuloser.padma.rohit.gitsome.retroInterface;

import com.etuloser.padma.rohit.gitsome.model.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rohit on 12/29/2017.
 */

public interface IGithub {

    @GET("users/{user}")
    Call<user> getUser(@Path("user") String user);
}
