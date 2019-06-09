package com.etuloser.padma.rohit.gitsome.activities.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.etuloser.padma.rohit.gitsome.activities.Profile.ProfileActivity;
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

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.btnsearch)
    Button btnsearch;
    @BindView(R.id.edxusername)
    EditText edxusername;
    User u;
    ArrayList<UserData> userDataArrayList =new ArrayList<>();
    private CompositeDisposable _disposables;
    MainActivityContract.Presenter presenter;


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter=new MainActivityPresenter(this);
        _disposables = new CompositeDisposable();
    }

    @OnClick(R.id.btnsearch)
    public void SearchUser(){
     final String user1=edxusername.getText().toString().trim();
     if(!user1.isEmpty())
     {
         presenter.getUserData(user1);
     }
     else
     {
         Toast.makeText(this.getApplicationContext(),"Enter a username",Toast.LENGTH_SHORT).show();
     }
    }

    @Override
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
