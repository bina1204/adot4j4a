
package com.gsbina.android.adot4j4a;

import twitter4j.User;

public class TwitterUser {

    public static final String YUSUKEY = "yusukey";

    private User mUser;
    private byte[] mImage;

    public TwitterUser(User user, byte[] image) {
        mUser = user;
        mImage = image;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

}
