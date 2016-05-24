package com.example.kedar.inclass12;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dnyanshree on 4/26/2016.
 */
public class Conversations implements Parcelable{
    private String conversationID;
    private String participant1, participant2;
    private String deletedBy;
    private String isArchived_by_participant1, isArchived_by_participant2;

    public Conversations(String participant1, String participant2, String deletedBy, String isArchived_by_participant1, String isArchived_by_participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.deletedBy = deletedBy;
        this.isArchived_by_participant1 = isArchived_by_participant1;
        this.isArchived_by_participant2 = isArchived_by_participant2;
    }
    public Conversations(){

    }

    public static final Creator<Conversations> CREATOR = new Creator<Conversations>() {
        @Override
        public Conversations createFromParcel(Parcel in) {
            return new Conversations(in);
        }

        @Override
        public Conversations[] newArray(int size) {
            return new Conversations[size];
        }
    };

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String isArchived_by_participant1() {
        return isArchived_by_participant1;
    }

    public void setIsArchived_by_participant1(String isArchived_by_participant1) {
        this.isArchived_by_participant1 = isArchived_by_participant1;
    }

    public String isArchived_by_participant2() {
        return isArchived_by_participant2;
    }

    public void setIsArchived_by_participant2(String isArchived_by_participant2) {
        this.isArchived_by_participant2 = isArchived_by_participant2;
    }

    @Override
    public String toString() {
        return "Conversations{" + conversationID+
                ", participant1='" + participant1 + '\'' +
                ", participant2='" + participant2 + '\'' +
                ", deletedBy='" + deletedBy + '\'' +
                ", isArchived_by_participant1=" + isArchived_by_participant1 +
                ", isArchived_by_participant2=" + isArchived_by_participant2 +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(participant1);
        dest.writeString(participant2);
        dest.writeString(deletedBy);
        dest.writeString(isArchived_by_participant1);
        dest.writeString(isArchived_by_participant2);
    }

    protected Conversations(Parcel in){
        participant1=in.readString();
        participant2=in.readString();
        deletedBy=in.readString();
        isArchived_by_participant1=in.readString();
        isArchived_by_participant2=in.readString();
    }

}
