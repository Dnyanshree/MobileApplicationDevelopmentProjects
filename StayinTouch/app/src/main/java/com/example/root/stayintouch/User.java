package com.example.root.stayintouch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 4/16/16.
 */
public class User implements Parcelable {
    private String email,password,name,phone,profilePic,hasUnreadMsg="false";

    public String getHasUnreadMsg() {
        return hasUnreadMsg;
    }

    public void setHasUnreadMsg(String hasUnreadMsg) {
        this.hasUnreadMsg = hasUnreadMsg;
    }

    public User() {
    }
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }

    public User(String email, String password, String name, String phone, String profilePic) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.profilePic = profilePic;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //email,password,name,phone,profilePic;

        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(profilePic);

    }

    protected User(Parcel in) {

        email = in.readString();
        password = in.readString();
        name = in.readString();
        phone = in.readString();
        profilePic = in.readString();

    }
}
