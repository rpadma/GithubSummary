package com.etuloser.padma.rohit.gitsome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.util.constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.etuloser.padma.rohit.gitsome.model.user;
public class MainActivity extends AppCompatActivity {

    Button btnsearch;
    EditText edxusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsearch=(Button)findViewById(R.id.btnsearch);
        edxusername=(EditText)findViewById(R.id.edxusername);

    }


    public void SearchUser(View v){
     String user=edxusername.getText().toString().trim();
     if(!user.isEmpty())
     {
         Retrofit retrofit= new Retrofit.Builder().baseUrl(constants.url)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         IGithub iGithub=retrofit.create(IGithub.class);
         Call<user> callgituser=iGithub.getUser(user);

         callgituser.enqueue(new Callback<user>() {
             @Override
             public void onResponse(Call<user> call, Response<user> response) {


                 Log.d("###trip","demo");
                 Log.d("###trip code",response.message());
                 Log.d("###trip code",String.valueOf(response.code()));
                 Log.d("###trip code",response.message());
                 Log.d("###trip code",response.message());

                 Log.d("###trip",response.body().toString());


             }

             @Override
             public void onFailure(Call<user> call, Throwable t) {

             }
         });
         }
     else
     {
         Toast.makeText(this.getApplicationContext(),"Enter a username",Toast.LENGTH_SHORT).show();
     }


    }


    public void Senduser(user gituser)
    {

        Intent i=new Intent(this,ProfileActivity.class);
        Bundle b=new Bundle();
      //  b.putSerializable("gituser",gituser);
        startActivity(i);
    }
}
