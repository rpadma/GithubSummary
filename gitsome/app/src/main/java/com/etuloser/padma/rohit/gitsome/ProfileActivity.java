package com.etuloser.padma.rohit.gitsome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.etuloser.padma.rohit.gitsome.model.user;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    ImageView imgavatar;
    TextView txtname;
    TextView txtrepo;
    TextView txtjoin;
    TextView txtviewprofile;
user u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgavatar=(ImageView)findViewById(R.id.imgavatar);
        txtjoin=(TextView)findViewById(R.id.txtjoin);
        txtname=(TextView)findViewById(R.id.txtusername);
        txtrepo=(TextView)findViewById(R.id.txtrepo);
        txtviewprofile=(TextView)findViewById(R.id.txtviewprofile);


    if(getIntent().getExtras()!=null)
    {
        Bundle b=getIntent().getExtras();
         u=(user)b.getSerializable("gituser");
         u.setMessage(" ");
         u.setDocumentation_url(" ");
        Log.d("user in profile",u.toString());
    }

        Picasso.with(this).load(u.getAvatar_url()).fit().into(imgavatar);
       String joinyear= u.getCreated_at();
    txtjoin.setText("Joined"+ 4 +"Years ago");
    txtrepo.setText(u.getPublic_repos()+" public repos");
    txtname.setText(u.getLogin()+" ("+u.getName()+") ");


    }
}
