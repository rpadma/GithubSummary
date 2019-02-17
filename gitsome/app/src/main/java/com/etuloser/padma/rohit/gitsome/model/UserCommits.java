package com.etuloser.padma.rohit.gitsome.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by rohitpadma on 3/11/18.
 */

public class UserCommits implements Serializable, Parcelable {

    private int contributions;

    private int id;

    private String avatarUrl;

    private String login;

    protected UserCommits(Parcel in) {
        contributions = in.readInt();
        id = in.readInt();
        avatarUrl = in.readString();
        login = in.readString();
    }

    public static final Creator<UserCommits> CREATOR = new Creator<UserCommits>() {
        @Override
        public UserCommits createFromParcel(Parcel in) {
            return new UserCommits(in);
        }

        @Override
        public UserCommits[] newArray(int size) {
            return new UserCommits[size];
        }
    };

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contributions);
        dest.writeInt(id);
        dest.writeString(avatarUrl);
        dest.writeString(login);
    }
}
