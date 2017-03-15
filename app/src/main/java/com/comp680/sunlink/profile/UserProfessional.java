package com.comp680.sunlink.profile;

import java.io.Serializable;

/**
 * Created by bigmatt76 on 12/7/16.
 */

public class UserProfessional implements Serializable {
    private String userID;
    private String ps = " ";
    private String exp = " ";
    private String skills= " ";
    private String projects = " ";


    public UserProfessional() {

    }
    protected void setUserID(String newID) {
        this.userID = newID;
    }

    protected void setPS(String newps) {
        if(newps!="null")
            this.ps=newps;
    }
    protected void setEXP(String newexp) {
        if(newexp!="null")
            this.exp=newexp;
    }
    protected void setSkills(String newskills) {
        if(newskills!="null")
            this.skills=newskills;
    }
    protected void setProjects(String newpr) {
        if(newpr!="null")
            this.projects= newpr;
    }

    protected String getID() {
        return this.userID;
    }
    protected String getPS() {
        return this.ps;
    }
    protected String getEXP(){
        return this.exp;
    }
    protected String getSkills() {
        return this.skills;
    }
    protected String getProjects() {
        return this.projects;
    }
}







