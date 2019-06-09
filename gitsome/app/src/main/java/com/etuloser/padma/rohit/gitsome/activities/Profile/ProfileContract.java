package com.etuloser.padma.rohit.gitsome.activities.Profile;

import com.etuloser.padma.rohit.gitsome.model.UserData;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileContract {
    public interface Presenter{
        void getcontributors(ArrayList<String> repolist);
        void getrepodata(ArrayList<String> repolist);
    }
    public interface View{
        void bindrepocommit(HashMap<String,Integer> repocommits);
        void bindrepochart(ArrayList<UserData> ud);
        void bindstarrepo(HashMap<String,Integer> starcount);
        void bindprojectstar(HashMap<String,Integer> projectstarcount);
        void notifyChart();
    }
}
