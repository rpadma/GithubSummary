package com.etuloser.padma.rohit.gitsome.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rohitpadma on 1/7/18.
 */

public class UserAndRepo implements Serializable, Parcelable {

    public User u;
    public ArrayList<UserData> repo;

    public UserAndRepo() {
    }


    protected UserAndRepo(Parcel in) {
        u = in.readParcelable(User.class.getClassLoader());
        repo = in.createTypedArrayList(UserData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(u, flags);
        dest.writeTypedList(repo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserAndRepo> CREATOR = new Creator<UserAndRepo>() {
        @Override
        public UserAndRepo createFromParcel(Parcel in) {
            return new UserAndRepo(in);
        }

        @Override
        public UserAndRepo[] newArray(int size) {
            return new UserAndRepo[size];
        }
    };

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public ArrayList<UserData> getRepo() {
        return repo;
    }

    public void setRepo(ArrayList<UserData> repo) {
        this.repo = repo;
    }


}
