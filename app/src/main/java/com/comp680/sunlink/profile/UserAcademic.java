package com.comp680.sunlink.profile;

import java.io.Serializable;

public class UserAcademic implements Serializable{
    private String userID = " ";
    private String major = " ";
    private String gpa = " ";
    private String graduationYear = " ";
    private String prevEdu = " ";
    private String apType = " ";
    private String degreeLevel = " ";

    UserAcademic() {}

    void setUserID(String newID) {
        this.userID = newID;
    }

    void setMajor(String newMajor) {
        if(!newMajor.isEmpty())
            this.major=newMajor;
    }

    void setGpa(String newGpa) {
        if(!newGpa.isEmpty())
            this.gpa=newGpa;
    }

    void setGraduationYear(String newGraduationYear) {
        if(!newGraduationYear.isEmpty())
            this.graduationYear=newGraduationYear;
    }

    void setPrevEdu(String newPrevEdu) {
        if(!newPrevEdu.isEmpty())
            this.prevEdu= newPrevEdu;
    }

    void setAppType(String newAppType) {
        if(!newAppType.isEmpty())
            this.apType = newAppType;
    }

    void setDegreeLevel(String newDegreeLevel) {
        if(!newDegreeLevel.isEmpty())
            this.degreeLevel = newDegreeLevel;
    }

    String getID() {
        return this.userID;
    }
    String getMajor() {
        return this.major;
    }
    String getGpa(){
        return this.gpa;
    }
    String getGraduationYear() {
        return this.graduationYear;
    }
    String getPrevEdu() {
        return this.prevEdu;
    }
    String getApType() {
        return this.apType;
    }
    String getDegreeLevel() {
        return this.degreeLevel;
    }
}








