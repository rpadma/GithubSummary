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

import com.etuloser.padma.rohit.gitsome.model.commitmodel.commitdata;
import com.etuloser.padma.rohit.gitsome.model.userandrepo;
import com.etuloser.padma.rohit.gitsome.model.userdata;
import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.service.GithubService;
import com.etuloser.padma.rohit.gitsome.util.constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.etuloser.padma.rohit.gitsome.model.user;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.reactivestreams.Subscriber;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnsearch)
    Button btnsearch;
    @BindView(R.id.edxusername)
    EditText edxusername;


    user u;
    ArrayList<userdata> userdataArrayList=new ArrayList<>();
  private IGithub githubService;
  private CompositeDisposable _disposables;
    Observable<userandrepo> combined;

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

         Observable<user> userObservable = githubService.getOUser(user1)
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread());

         Observable<ArrayList<userdata>> repoObservable = githubService.getOUserData(user1)
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread());



         combined = Observable.zip(userObservable, repoObservable, new BiFunction<user, ArrayList<userdata>, userandrepo>() {
             @Override
             public userandrepo apply(user u, ArrayList<userdata> userdata) throws Exception {


                 Log.d("user",u.toString());
                 userandrepo urobject=new userandrepo();
                 urobject.setU(u);
                 urobject.setRepo(userdata);
                 return urobject;

             }
         });

         combined.subscribe(new Observer<userandrepo>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(userandrepo userandrepo) {


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




    public void Senduser(userandrepo gituser)
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
