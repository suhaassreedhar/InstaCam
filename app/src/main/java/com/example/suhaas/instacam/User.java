package com.example.suhaas.instacam;

import android.util.Log;

import com.facebook.model.GraphObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Serializable{
    private String mFirstName;
    private String mLastName;
    private Date mBirthday;
    private String mAvatarURL;

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public Date getBirthday() {
        return mBirthday;
    }

    public String getAvatarURL() {
        return mAvatarURL;
    }

    private static User sCurrentUser;

    public static User getCurrentUser() {
        return sCurrentUser;
    }

    public static void setCurrentUser(GraphObject graphObject) {
        if (sCurrentUser == null){
            sCurrentUser = new User(graphObject);
        }
    }

    User(GraphObject graphObject){
        mFirstName = (String) graphObject.getProperty("first_name");
        mLastName = (String) graphObject.getProperty("last_name");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            mBirthday = sdf.parse((String) graphObject.getProperty("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAvatarURL = (String) graphObject.getPropertyAs("picture", GraphObject.class)
                .getPropertyAs("data", GraphObject.class)
                .getProperty("url");
    }
}
