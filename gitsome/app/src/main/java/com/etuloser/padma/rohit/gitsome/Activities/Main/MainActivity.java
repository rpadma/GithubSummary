package com.etuloser.padma.rohit.gitsome.Activities.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.etuloser.padma.rohit.gitsome.Activities.Profile.ProfileActivity;
import com.etuloser.padma.rohit.gitsome.R;
import com.etuloser.padma.rohit.gitsome.model.User;
import com.etuloser.padma.rohit.gitsome.model.UserAndRepo;
import com.etuloser.padma.rohit.gitsome.model.UserData;
import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.service.GithubService;
import com.etuloser.padma.rohit.gitsome.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnsearch)
    Button btnsearch;
    @BindView(R.id.edxusername)
    EditText edxusername;
    User u;
    ArrayList<UserData> userDataArrayList =new ArrayList<>();
    private IGithub githubService;
   private CompositeDisposable _disposables;
    Observable<UserAndRepo> combined;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String githubToken = Constants.token;
        githubService = (IGithub) GithubService.createGithubService(githubToken);
        _disposables = new CompositeDisposable();
    }

    @OnClick(R.id.btnsearch)
    public void SearchUser(){
     final String user1=edxusername.getText().toString().trim();
     if(!user1.isEmpty())
     {

         Observable<User> userObservable = githubService.getOUser(user1)
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread());

         Observable<ArrayList<UserData>> repoObservable = githubService.getOUserData(user1)
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread());
         combined = Observable.zip(userObservable, repoObservable, new BiFunction<User, ArrayList<UserData>, UserAndRepo>() {
             @Override
             public UserAndRepo apply(User u, ArrayList<UserData> userdata) throws Exception {

                 Log.d("User",u.toString());
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


                                    Senduser(userandrepo);
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
     else
     {
         Toast.makeText(this.getApplicationContext(),"Enter a username",Toast.LENGTH_SHORT).show();
     }
    }

    public void Senduser(UserAndRepo gituser)
    {
        _disposables.dispose();
        Intent i=new Intent(this, ProfileActivity.class);
        Bundle b=new Bundle();
        b.putParcelable("gituser",gituser);
        i.putExtras(b);
        startActivity(i);
    }

      @Override
      public void onDestroy() {
        super.onDestroy();

        _disposables.dispose();
      }

}
