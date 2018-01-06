package com.etuloser.padma.rohit.gitsome;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.service.GithubService;
import com.etuloser.padma.rohit.gitsome.util.constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.etuloser.padma.rohit.gitsome.model.user;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnsearch)
    Button btnsearch;
    @BindView(R.id.edxusername)
    EditText edxusername;

  private IGithub githubService;
  private CompositeDisposable _disposables;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String githubToken = getResources().getString(R.string.githubToken);
         githubService = (IGithub) GithubService.createGithubService(githubToken);

            _disposables = new CompositeDisposable();


    }
        public void SearchUser(View v){
     final String user1=edxusername.getText().toString().trim();
     if(!user1.isEmpty())
     {

             _disposables.add(
                     githubService.getOUser(user1)
                             .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribeWith(
                         new DisposableObserver<user>() {

                           @Override
                           public void onComplete() {
                                Log.d("user","completed");
                           }

                             @Override
                             public void onNext(user user) {

                               Log.d("user",user.toString());
                               Senduser(user);

                             }

                             @Override
                           public void onError(Throwable e) {

                           }














                         }));











/*
         Retrofit retrofit= new Retrofit.Builder().baseUrl(constants.url)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         IGithub iGithub=retrofit.create(IGithub.class);
         Call<user> callgituser=iGithub.getUser(user);

         callgituser.enqueue(new Callback<user>() {
             @Override
             public void onResponse(Call<user> call, Response<user> response) {

                 if(response.code()==200)
                 {


                     user u=new user();
                     u.setAvatar_url(response.body().getAvatar_url());
                     u.setRepos_url(response.body().getRepos_url());
                     u.setCreated_at(response.body().getCreated_at());
                     u.setEmail(response.body().getEmail());
                     u.setFollowers(response.body().getFollowers());
                     u.setFollowing(response.body().getFollowing());
                     u.setLocation(response.body().getLocation());
                     u.setLogin(response.body().getLogin());
                     u.setUrl(response.body().getUrl());
                     u.setPublic_repos(response.body().getPublic_repos());
                     u.setName(response.body().getName());

                     Senduser(u);
                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(),"No User exist",Toast.LENGTH_SHORT).show();
                 }



             }

             @Override
             public void onFailure(Call<user> call, Throwable t) {

             }
         });

         */
         }
     else
     {
         Toast.makeText(this.getApplicationContext(),"Enter a username",Toast.LENGTH_SHORT).show();
     }


    }


    public void Senduser(user gituser)
    {

        _disposables.dispose();
        Intent i=new Intent(this,ProfileActivity.class);
        Bundle b=new Bundle();
        b.putSerializable("gituser",gituser);
        i.putExtras(b);
        startActivity(i);
    }

      @Override
      public void onDestroy() {
        super.onDestroy();
        _disposables.dispose();
      }

}
