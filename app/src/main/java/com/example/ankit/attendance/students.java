package com.example.ankit.attendance;

/**
 * Created by ANKIT on 12-07-2017.
 */

public class students {

    public String mStudName;
    public String mStudRoll;
    public String mStudEmail;
    public String mStudPhone;

    public students(String mStudName, String mStudRoll, String mStudEmail, String mStudPhone) {
        this.mStudName = mStudName;
        this.mStudRoll = mStudRoll;
        this.mStudEmail = mStudEmail;
        this.mStudPhone = mStudPhone;
    }

    public String getmStudName() {
        return mStudName;
    }

    public String getmStudRoll() {
        return mStudRoll;
    }

    public String getmStudEmail() {
        return mStudEmail;
    }

    public String getmStudPhone() {
        return mStudPhone;
    }
}
