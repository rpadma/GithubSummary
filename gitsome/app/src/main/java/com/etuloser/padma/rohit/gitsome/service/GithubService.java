package com.etuloser.padma.rohit.gitsome.service;

import android.text.TextUtils;

import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;

/**
 * Created by Rohit on 1/4/2018.
 */


public class GithubService {

    private GithubService() {}

    public static IGithub createGithubService(final String githubToken) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.github.com");

        if (!TextUtils.isEmpty(githubToken)) {

            OkHttpClient client =
                    new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            Request request = chain.request();
                            Request newReq =
                                    request
                                            .newBuilder()
                                            .addHeader("Authorization", format("token %s", githubToken))
                                            .build();
                            return chain.proceed(newReq);
                        }
                    }).build();

            builder.client(client);
        }

        return builder.build().create(IGithub.class);
    }
}
