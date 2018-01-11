package com.etuloser.padma.rohit.gitsome;

import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.etuloser.padma.rohit.gitsome.model.user;
import com.etuloser.padma.rohit.gitsome.model.userandrepo;
import com.etuloser.padma.rohit.gitsome.model.userdata;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.imgavatar)
    ImageView imgavatar;
    @BindView(R.id.txtusername)
    TextView txtname;
    @BindView(R.id.txtrepo)
    TextView txtrepo;
    @BindView(R.id.txtjoin)
    TextView txtjoin;
    @BindView(R.id.txtviewprofile)
    TextView txtviewprofile;

    @BindView(R.id.repolangchart)
    PieChart repochar;


    user u;
    userandrepo uar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);




    if(getIntent().getExtras()!=null)
    {
        Bundle b=getIntent().getExtras();
         uar=(userandrepo)b.getSerializable("gituser");
         u=uar.getU();
         u.setMessage(" ");
         u.setDocumentation_url(" ");
        Log.d("user in profile",u.toString());
    }

    if(u.getAvatar_url()!=null)
        {
            Picasso.with(this).load(u.getAvatar_url()).fit().into(imgavatar);
        }
        String joinyear= u.getCreated_at();
    txtjoin.setText("Joined"+ 4 +" Years ago");
    txtrepo.setText(u.getPublic_repos()+" repos");
    txtname.setText(u.getName());

    bindrepochart(uar.getRepo());


    }

    public void bindrepochart(ArrayList<userdata> ud)
    {


        HashMap<String,Integer> projectcount=new HashMap<>();
        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for  (userdata u:ud)
        {
            if(!projectcount.containsKey(u.getLanguage())) {
                projectcount.put(u.getLanguage(), 1);

            }
            else
            {
                projectcount.put(u.getLanguage(),projectcount.get(u.getLanguage())+1);
            }

        }

        for (String temp:projectcount.keySet())
        {

            labels.add(temp);
            entries1.add(new PieEntry(projectcount.get(temp),temp));

        }


        PieDataSet dataset1 = new PieDataSet(entries1,"Count");

        PieData pd=new PieData(dataset1);
        repochar.setData(pd);

        dataset1.setColors(ColorTemplate.MATERIAL_COLORS);
        Description d=new Description();
        d.setText("Repos per Language");
        repochar.setDescription(d);



    }



}
