package com.example.suhaas.instacam;

import android.os.Environment;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

public class Photo implements Serializable{
    private static final File sDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private UUID mId;
    private String mCaption;
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    Photo(){
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public File getFile(){
        return new File(sDirectory, mId.toString());
    }
}
