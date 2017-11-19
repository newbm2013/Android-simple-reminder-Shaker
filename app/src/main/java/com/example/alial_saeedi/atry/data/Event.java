package com.example.alial_saeedi.atry.data;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject implements Parcelable {

    private String eventTitle;
    private String eventDetail;
    private String eventDate;
    private String eventTime;
    private String userId;
    @PrimaryKey
    private String eventID;

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }


    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }


    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventID() {
        return eventID;
    }


    public Event() {
    }

    public Event(String eventTitle, String eventDetail, String userId, String itemID , String eventTime ,String eventDate) {
        this.eventTitle = eventTitle;
        this.eventDetail = eventDetail;
        this.userId = userId;
        this.eventID = itemID;
        this.eventTime = eventTime;
        this.eventDate = eventDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(eventID);
        out.writeString(eventTitle);
        out.writeString(eventDetail);
        out.writeString(userId);
        out.writeString(eventDate);
        out.writeString(eventTime);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Event(Parcel in) {
        eventID = in.readString();
        eventTitle = in.readString();
        eventDetail = in.readString();
        userId = in.readString();
        eventDate = in.readString();
        eventTime = in.readString();
    }
}
