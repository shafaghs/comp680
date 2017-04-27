package com.comp680.sunlink.fragments;

class EventInfo {
    private String eventTitle,eventId,eventDate,eventLocation,eventInfo;

    EventInfo(String mEventId, String mEventTitle, String mEventDate, String mEventInfo, String mEventLocation) {
        this.eventTitle = mEventTitle;
        this.eventId = mEventId;
        this.eventDate = mEventDate;
        this.eventLocation = mEventLocation;
        this.eventInfo = mEventInfo;
    }

    String geteventId() {
        return eventId;
    }
    public void seteventId(String meventId) {
        this.eventId = meventId;
    }

    String geteventTitle() {
        return eventTitle;
    }
    public void seteventTitle(String meventTitle) {
        this.eventTitle = meventTitle;
    }

    String geteventDate() {
        return eventDate;
    }
    public void seteventDate(String meventDate) {
        this.eventDate = meventDate;
    }

    String geteventInfo() {
        return eventInfo;
    }
    public void seteventInfo(String meventInfo) {
        this.eventInfo = meventInfo;
    }


    String geteventLocation() {
        return eventLocation;
    }
    public void seteventLocation(String meventLocation) {
        this.eventLocation = meventLocation;
    }


}
