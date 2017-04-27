package com.comp680.sunlink.fragments;

class InterviewInfo {

    private String interviewTitle;
    private String interviewId;
    private String interviewDate;
    private String interviewTime;
    private String interviewLocation ;
    private String interviewInfo;

    public InterviewInfo(String mInterviewId, String mInterviewTitle, String mInterviewDate,
                         String mInterviewInfo, String mInterviewLocation ,String mInterviewTime) {
        this.interviewTitle = mInterviewTitle;
        this.interviewId = mInterviewId;
        this.interviewDate = mInterviewDate;
        this.interviewTime = mInterviewTime;
        this.interviewLocation = mInterviewLocation;
        this.interviewInfo = mInterviewInfo;
    }

    String getInterviewId() {
        return interviewId;
    }
    public void setInterviewId(String mInterviewId) {
        this.interviewId = mInterviewId;
    }

    String getInterviewTitle() {
        return interviewTitle;
    }
    public void setInterviewTitle(String mInterviewTitle) {
        this.interviewTitle = mInterviewTitle;
    }

    String getInterviewDate() {
        return interviewDate;
    }
    public void setInterviewDate(String mInterviewDate) {
        this.interviewDate = mInterviewDate;
    }

    String getInterviewTime() {
        return interviewTime;
    }
    public void setInterviewTime(String mInterviewTime) {
        this.interviewTime = mInterviewTime;
    }

    String getInterviewInfo() {
        return interviewInfo;
    }
    public void setInterviewInfo(String mInterviewInfo) {
        this.interviewInfo = mInterviewInfo;
    }

    String getInterviewLocation() {
        return interviewLocation;
    }
    public void setInterviewLocation(String mInterviewLocation) {
        this.interviewLocation = mInterviewLocation;
    }

}
