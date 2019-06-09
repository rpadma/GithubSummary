package com.etuloser.padma.rohit.gitsome.activities.Main;

import com.etuloser.padma.rohit.gitsome.model.UserAndRepo;

public interface MainActivityContract {

    public interface Presenter{

        void getUserData(String username);
    }
    public interface View{

        void Senduser(UserAndRepo gituser);
    }
}
