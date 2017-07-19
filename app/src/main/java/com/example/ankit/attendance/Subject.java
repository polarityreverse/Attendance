package com.example.ankit.attendance;

/**
 * Created by ANKIT on 09-07-2017.
 */

public class Subject {

    public final String mSubject;
    public final String mTime;

    Subject(String subject, String time) {
        mSubject = subject;
        mTime = time;
    }
    String getSubject() {
        return mSubject;
    }
    String getTime() {
        return mTime;
    }
}