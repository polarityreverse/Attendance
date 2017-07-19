package com.example.ankit.attendance;

/**
 * Created by ANKIT on 13-07-2017.
 */

public class attend {

    private String StudName;
    private String RollNo;
    private boolean isPresent;
    private boolean isAbsent;
    private boolean onLeave;

    attend(String studName, String roll){
        StudName = studName;
        RollNo = roll;
    }

    attend(String studName, String roll, Boolean mIsPresent, Boolean mIsAbsent, Boolean mOnLeave) {
        StudName = studName;
        RollNo = roll;
        isPresent = mIsPresent;
        isAbsent = mIsAbsent;
        onLeave = mOnLeave;

    }
    public boolean isOnLeave() {
        return onLeave;
    }


    public String getStudName() {
        return StudName;
    }

    public String getRollNo() {
        return RollNo;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public boolean isAbsent() {
        return isAbsent;
    }
}
