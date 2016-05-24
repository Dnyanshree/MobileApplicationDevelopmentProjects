package com.example.kedar.inclass12;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kedar on 4/24/2016.
 */
public class Messages  implements Parcelable{
    private String timestamp;
    private String message_read;
    private String message_text,receiver,sender;
    private String key;

    public static final Creator<Messages> CREATOR = new Creator<Messages>() {
        @Override
        public Messages createFromParcel(Parcel in) {
            return new Messages(in);
        }

        @Override
        public Messages[] newArray(int size) {
            return new Messages[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Messages() {
    }

    @Override
    public String toString() {
        return "Messages{" +
                "timestamp='" + timestamp + '\'' +
                ", message_read=" + message_read +
                ", message_text='" + message_text + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }

    public String getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String isMessage_read() {
        return message_read;
    }

    public void setMessage_read(String message_read) {
        this.message_read = message_read;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Messages(String timestamp, String message_read, String message_text, String receiver, String sender) {

        this.timestamp = timestamp;
        this.message_read = message_read;
        this.message_text = message_text;
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timestamp);
        dest.writeString(message_read);
        dest.writeString(message_text);
        dest.writeString(receiver);
        dest.writeString(sender);
    }


    protected Messages(Parcel in){
        timestamp=in.readString();
        message_read=in.readString();
        message_text=in.readString();
        receiver=in.readString();
        sender=in.readString();
    }
}
