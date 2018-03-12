package com.etuloser.padma.rohit.gitsome;

import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etuloser.padma.rohit.gitsome.model.commitmodel.commitdata;
import com.etuloser.padma.rohit.gitsome.model.user;
import com.etuloser.padma.rohit.gitsome.model.userandrepo;
import com.etuloser.padma.rohit.gitsome.model.usercommits;
import com.etuloser.padma.rohit.gitsome.model.userdata;
import com.etuloser.padma.rohit.gitsome.retroInterface.IGithub;
import com.etuloser.padma.rohit.gitsome.service.GithubService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

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

    @BindView(R.id.starlangchart)
    PieChart starrepochar;

    @BindView(R.id.projectstarchart)
    PieChart projectstarchar;

    @BindView(R.id.repocommitchart)
     PieChart repocommitchar;

    user u;
    userandrepo uar;
    private IGithub githubService;
    CompositeDisposable reposDisposable;
    ArrayList<String> repolist;
    HashMap<String,ArrayList<commitdata>> hcommitdata;
    HashMap<String,Integer> repocommits;
    ArrayList<ArrayList<commitdata>> clist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        String githubToken = getResources().getString(R.string.githubToken);
        githubService = (IGithub) GithubService.createGithubService(githubToken);
        reposDisposable=new CompositeDisposable();
        hcommitdata=new HashMap<>();
        repocommits=new HashMap<>();

        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            uar = (userandrepo) b.getSerializable("gituser");
            u = uar.getU();
            u.setMessage(" ");
            u.setDocumentation_url(" ");
            Log.d("user in profile", u.toString());
        }

        if (u.getAvatar_url() != null) {
            Picasso.with(this).load(u.getAvatar_url()).fit().into(imgavatar);
        }
        String joinyear = u.getCreated_at();
        txtjoin.setText("Joined" + 4 + " Years ago");
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


        getrepodata(repolist);

        getcontributors(repolist);




    }


    public void getcontributors(ArrayList<String> repolist)
    {
                       reposDisposable.add( Observable.fromIterable(repolist)
                                .flatMap(new Function<String, ObservableSource<Pair<String,ArrayList<usercommits>>>>() {
                                                                   @Override
                                                                   public ObservableSource<Pair<String,ArrayList<usercommits>>> apply(String s) throws Exception {

                                                                       return Observable.zip(
                                                                               Observable.just(s),
                                                                               githubService.getcontributors(u.getLogin(), s), new BiFunction<String, ArrayList<usercommits>, Pair<String, ArrayList<usercommits>>>() {
                                                                                   @Override
                                                                                   public Pair<String, ArrayList<usercommits>> apply(String s, ArrayList<usercommits> usercommits) throws Exception {
                                                                                       return new Pair<String,ArrayList<usercommits>>(s, usercommits);
                                                                                   }
                                                                               }

                                                                       );
                                                                   }
                                                               }


                           ).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
         .subscribeWith(new DisposableObserver<Pair<String, ArrayList<usercommits>>>() {


             @Override
             public void onNext(Pair<String, ArrayList<usercommits>> stringArrayListPair) {


                 for(usercommits uc:stringArrayListPair.second)
                 {
                     if(uc.getLogin().equals(u.getLogin()))
                     {
                         repocommits.put(stringArrayListPair.first,uc.getContributions());
                         break;
                     }

                 }
             }


             @Override
             public void onError(Throwable e) {

             }


             @Override
             public void onComplete() {

                 bindrepocommit(repocommits);

             }
         })

 );


    }



    public void getrepodata(ArrayList<String> repolist)
    {




        reposDisposable.add(Observable.fromIterable(repolist)
                .flatMap(new Function<String, ObservableSource<Pair<String,ArrayList<commitdata>>>>() {
                    @Override
                    public ObservableSource<Pair<String,ArrayList<commitdata>>> apply(String s) throws Exception {

                        Log.d("in observable source",s);
                        return Observable.zip(
                                Observable.just(s),
                                githubService.getOcommitdata("rpadma",s),
                                new BiFunction<String, ArrayList<commitdata>, Pair<String, ArrayList<commitdata>>>() {
                                    @Override
                                    public Pair<String, ArrayList<commitdata>> apply(@NonNull String id, @NonNull ArrayList<commitdata> productResponse) throws Exception {
                                        Log.d("in function",id);
                                        return new Pair<String, ArrayList<commitdata>>(id, productResponse);
                                    }
                                });
                    }}).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Pair<String, ArrayList<commitdata>>>(){


                            /**
                             * Provides the Observer with a new item to observe.
                             * <p>
                             * The {@link Observable} may call this method 0 or more times.
                             * <p>
                             * The {@code Observable} will not call this method again after it calls either {@link #onComplete} or
                             * {@link #onError}.
                             *
                             * @param userArrayListPair the item emitted by the Observable
                             */
                            @Override
                            public void onNext(Pair<String, ArrayList<commitdata>> userArrayListPair) {

                                hcommitdata.put(userArrayListPair.first,userArrayListPair.second);
                                Log.d("demo",userArrayListPair.first.toString() +" "+String.valueOf(userArrayListPair.second.size()));

                            }

                            /**
                             * Notifies the Observer that the {@link Observable} has experienced an error condition.
                             * <p>
                             * If the {@link Observable} calls this method, it will not thereafter call {@link #onNext} or
                             * {@link #onComplete}.
                             *
                             * @param e the exception encountered by the Observable
                             */
                            @Override
                            public void onError(Throwable e) {

                            }

                            /**
                             * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
                             * <p>
                             * The {@link Observable} will not call this method if it calls {@link #onError}.
                             */
                            @Override
                            public void onComplete() {

                            }
                        }));



    }







  public void bindrepocommit(HashMap<String,Integer> repocommits)
  {
      ArrayList<String> labels = new ArrayList<String>();
      ArrayList<PieEntry> entries1 = new ArrayList<>();

      for (String temp:repocommits.keySet())
      {

          labels.add(temp);
          entries1.add(new PieEntry(repocommits.get(temp),temp));

      }

      PieDataSet dataset1 = new PieDataSet(entries1,"Commits Count");

      PieData pd=new PieData(dataset1);
      repocommitchar.setData(pd);

      dataset1.setColors(ColorTemplate.MATERIAL_COLORS);
      Description d=new Description();
      d.setText("Commits per repo");
      repocommitchar.setDescription(d);







  }




    public void bindrepochart(ArrayList<userdata> ud)
    {


        HashMap<String,Integer> projectcount=new HashMap<>();
        HashMap<String,Integer> starcount=new HashMap<>();
        HashMap<String, Integer> projectstarcount=new HashMap<>();

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

        dataset1.setColors(ColorTemplate.MATERIAL_COLORS);
        Description d=new Description();
        d.setText("Repos per Language");
        repochar.setDescription(d);


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


    public void bindstarrepo(HashMap<String,Integer> starcount)
    {

        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for (String temp:starcount.keySet())
        {

            labels.add(temp);
            entries1.add(new PieEntry(starcount.get(temp),temp));

        }


        PieDataSet dataset1 = new PieDataSet(entries1,"Star Count");

        PieData pd=new PieData(dataset1);
        starrepochar.setData(pd);

        dataset1.setColors(ColorTemplate.MATERIAL_COLORS);
        Description d=new Description();
        d.setText("Stars per Language");
        starrepochar.setDescription(d);


    }


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
        Description d=new Description();
        d.setText("Stars per Repo");
        projectstarchar.setDescription(d);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reposDisposable!=null) {
            reposDisposable.dispose();
        }
        }
}
