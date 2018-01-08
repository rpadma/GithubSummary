package com.etuloser.padma.rohit.gitsome.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rohitpadma on 1/7/18.
 */

public class userandrepo implements Serializable{

    public user u;
    public ArrayList<userdata> repo;

    public user getU() {
        return u;
    }

    public void setU(user u) {
        this.u = u;
    }

    public ArrayList<userdata> getRepo() {
        return repo;
    }

    public void setRepo(ArrayList<userdata> repo) {
        this.repo = repo;
    }
}
