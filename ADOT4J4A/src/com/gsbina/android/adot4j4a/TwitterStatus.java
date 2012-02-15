
package com.gsbina.android.adot4j4a;

import twitter4j.Status;

public class TwitterStatus {

    private Status mStatus;
    private byte[] mImage;

    public TwitterStatus(Status status, byte[] image) {
        mStatus = status;
        mImage = image;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

}
