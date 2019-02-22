package com.etuloser.padma.rohit.gitsome.Activities.Profile;

import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etuloser.padma.rohit.gitsome.Activities.Main.MainActivity;
import com.etuloser.padma.rohit.gitsome.R;
import com.etuloser.padma.rohit.gitsome.model.User;
import com.etuloser.padma.rohit.gitsome.model.commitmodel.CommitData;
import com.etuloser.padma.rohit.gitsome.model.UserAndRepo;
import com.etuloser.padma.rohit.gitsome.model.UserCommits;
import com.etuloser.padma.rohit.gitsome.model.UserData;
import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.service.GithubService;
import com.etuloser.padma.rohit.gitsome.util.Constants;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View{

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
    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;

    @BindView(R.id.repolangchart)
    PieChart repochar;

    @BindView(R.id.starlangchart)
    PieChart starrepochar;

    @BindView(R.id.projectstarchart)
    PieChart projectstarchar;

    @BindView(R.id.repocommitchart)
    HorizontalBarChart repocommitchar;

    @BindView(R.id.demoLayout)
    LinearLayout demoLayout;

    User u;
    UserAndRepo uar;
    private IGithub githubService;
    CompositeDisposable reposDisposable;
    ArrayList<String> repolist;
    HashMap<String,ArrayList<CommitData>> hcommitdata;
    HashMap<String,Integer> repocommits;
    ArrayList<ArrayList<CommitData>> clist=new ArrayList<>();

    ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);



        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            uar = (UserAndRepo) b.getParcelable("gituser");
            u = uar.getU();
            u.setMessage(" ");
            u.setDocumentation_url(" ");
            Log.d("User in profile", u.toString());
        }

        if (u.getAvatar_url() != null) {
            Picasso.with(this).load(u.getAvatar_url()).fit().into(imgavatar);
        }
        String joinyear = u.getCreated_at();
        txtjoin.setText("Joined " + 4 + " Years ago");
        txtrepo.setText(u.getPublic_repos() + " repos");
        txtname.setText(u.getName());

        repolist=new ArrayList<>();
        for(int i=0;i<uar.getRepo().size();i++)
        {
            repolist.add(uar.getRepo().get(i).getName());
        }

        if (uar.getRepo() != null) {
            bindrepochart(uar.getRepo());
            Log.d("repo size",String.valueOf(repolist.size()));

        } else {
            repochar.setVisibility(View.GONE);
        }

       profilePresenter=new ProfilePresenter(this,uar);
        profilePresenter.getrepodata(repolist);
        profilePresenter.getcontributors(repolist);

    }

@Override
  public void bindrepocommit(HashMap<String,Integer> repocommits)
  {
      ArrayList<String> labels = new ArrayList<String>();
    //  ArrayList<PieEntry> entries1 = new ArrayList<>();
      ArrayList<BarEntry> entries1 = new ArrayList<>();

     int i=0;
      for (String temp:repocommits.keySet())
      {

          labels.add(temp);
          //entries1.add(new PieEntry(repocommits.get(temp),temp));
          entries1.add(new BarEntry(i,repocommits.get(temp)));
          i++;
          Log.d("checkrepo",temp);

      }

      String [] la=new String[labels.size()];
      la=labels.toArray(la);
      BarDataSet dataSet=new BarDataSet(entries1,"Commits count");
      dataSet.setStackLabels(la);

      repocommitchar.setData(new BarData(dataSet));
      dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
      Description d=new Description();
      d.setText("Commits per repo");
      repocommitchar.setDescription(d);

      repocommitchar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(la));
      repocommitchar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
      repocommitchar.getXAxis().setGranularity(1f);
      repocommitchar.getXAxis().setGranularityEnabled(true);
      repocommitchar.getXAxis().setLabelCount(la.length);
      repocommitchar.setTouchEnabled(false);
      repocommitchar.notifyDataSetChanged();
      repocommitchar.invalidate();

   //   repocommitchar.setExtraOffsets(30,10,0,10);


   /*  PieDataSet dataset1 = new PieDataSet(entries1,"Commits Count");

      PieData pd=new PieData(dataset1);
      repocommitchar.setData(pd);

      dataset1.setColors(ColorTemplate.MATERIAL_COLORS);
      Description d=new Description();
      d.setText("Commits per repo");
      repocommitchar.setDescription(d);*/

  }

    @Override
    public void bindrepochart(ArrayList<UserData> ud)
    {


        HashMap<String,Integer> projectcount=new HashMap<>();
        HashMap<String,Integer> starcount=new HashMap<>();
        HashMap<String, Integer> projectstarcount=new HashMap<>();

        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for  (UserData u:ud)
        {
            if(!projectcount.containsKey(u.getLanguage())) {
                projectcount.put(u.getLanguage(), 1);

            }
            else
            {
                projectcount.put(u.getLanguage(),projectcount.get(u.getLanguage())+1);
            }

            if(u.getStargazers_count()>0)
            {
                if(starcount.containsKey(u.getLanguage())) {
                    starcount.put(u.getLanguage(), (starcount.get(u.getLanguage())+ u.getStargazers_count()));

                }
                else
                {
                    starcount.put(u.getLanguage(),u.getStargazers_count());
                }
            }

            if(u.getStargazers_count()>0)
            {
                projectstarcount.put(u.getName(),u.getStargazers_count());
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

        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataset1.setValueTextColor(Color.BLACK);
        Description d=new Description();
        d.setText("Repos per Language");
        repochar.setDescription(d);
        repochar.getLegend().setTextColor(Color.BLACK);
        repochar.setEntryLabelColor(Color.BLACK);



        if(starcount.size()>0) {
            bindstarrepo(starcount);
        }
        else{
            starrepochar.setVisibility(View.GONE);
        }
        if(projectstarcount.size()>0) {
            bindprojectstar(projectstarcount);
        }
        else
        {
            projectstarchar.setVisibility(View.GONE);
        }
    }


    @Override
    public void bindstarrepo(HashMap<String,Integer> starcount)
    {

        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for (String temp:starcount.keySet())
        {

            labels.add(temp);
            entries1.add(new PieEntry(starcount.get(temp),temp));

        }


        PieDataSet dataset1 = new PieDataSet(entries1," ");

        PieData pd=new PieData(dataset1);


        dataset1.setColors(ColorTemplate.MATERIAL_COLORS);
        dataset1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataset1.setValueTextColor(Color.BLACK);
        pd.setValueTextColor(Color.BLACK);
        dataset1.setValueLineColor(getResources().getColor(R.color.colorPrimaryDark));
        Description d=new Description();
        d.setText("Stars per Language");
        d.setTextColor(Color.RED);

        starrepochar.setDescription(d);
        starrepochar.setData(pd);
        starrepochar.getLegend().setTextColor(Color.BLACK);
        starrepochar.setEntryLabelColor(Color.BLACK);



    }


    @Override
    public void bindprojectstar(HashMap<String,Integer> projectstarcount)
    {

        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for (String temp:projectstarcount.keySet())
        {

            labels.add(temp);
            entries1.add(new PieEntry(projectstarcount.get(temp),temp));

        }


        PieDataSet dataset1 = new PieDataSet(entries1,"Star per Repo Count");

        PieData pd=new PieData(dataset1);
        projectstarchar.setData(pd);

        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataset1.setValueTextColor(Color.BLACK);
        Description d=new Description();
        d.setText("Stars per Repo");
        projectstarchar.setDescription(d);
        projectstarchar.setEntryLabelColor(Color.BLACK);
        projectstarchar.setPadding(50,0,50,0);
    }

    @Override
    public void notifyChart() {
        repocommitchar.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reposDisposable!=null) {
            reposDisposable.dispose();
        }
        }

    @OnClick(R.id.printPdf)
    public void printPdf(){

        PrintAttributes printAttrs = new PrintAttributes.Builder().
                setColorMode(PrintAttributes.COLOR_MODE_COLOR).
                setMediaSize(PrintAttributes.MediaSize.NA_LETTER).
                setResolution(new PrintAttributes.Resolution("zooey", PRINT_SERVICE, 300, 300)).
                setMinMargins(PrintAttributes.Margins.NO_MARGINS).
                build();
        PdfDocument document = new PrintedPdfDocument(this, printAttrs);
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(parentLayout.getWidth(), parentLayout.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        View content = findViewById(R.id.parentLayout);
        content.draw(page.getCanvas());
        document.finishPage(page);

        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(repochar.getWidth(), repochar.getHeight(), 2).create();
        PdfDocument.Page page1 = document.startPage(pageInfo1);
        View content1 = findViewById(R.id.repolangchart);
        content1.draw(page1.getCanvas());
        document.finishPage(page1);

        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(starrepochar.getWidth(), starrepochar.getHeight(), 3).create();
        PdfDocument.Page page2 = document.startPage(pageInfo2);
        View content2 = findViewById(R.id.starlangchart);
        content2.draw(page2.getCanvas());
        document.finishPage(page2);


        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(starrepochar.getWidth(), starrepochar.getHeight(), 4).create();
        PdfDocument.Page page3 = document.startPage(pageInfo3);
        View content3 = findViewById(R.id.projectstarchart);
        content3.draw(page3.getCanvas());
        document.finishPage(page3);


        PdfDocument.PageInfo pageInfo4 = new PdfDocument.PageInfo.Builder(demoLayout.getWidth(), demoLayout.getHeight(), 5).create();
        PdfDocument.Page page4 = document.startPage(pageInfo4);
        View content5 = findViewById(R.id.repocommitchart);
        content5.draw(page4.getCanvas());
        document.finishPage(page4);



        // Here you could add more pages in a longer doc app, but you'd have
        // to handle page-breaking yourself in e.g., write your own word processor...
        // Now write the PDF document to a file; it actually needs to be a file
        // since the Share mechanism can't accept a byte[]. though it can
        // accept a String/CharSequence. Meh.
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

            File f = new File(Environment.getExternalStorageDirectory().getPath() + "/pruebaAppModerator"+sdf.format(Calendar.getInstance().getTime())+".pdf");
            FileOutputStream fos = new FileOutputStream(f);
            document.writeTo(fos);
            document.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException("Error generating file", e);
        }
    }
}
