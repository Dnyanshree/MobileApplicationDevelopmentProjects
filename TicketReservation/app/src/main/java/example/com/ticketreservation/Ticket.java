package example.com.ticketreservation;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
/*
* Assignment No. HomeWork 02
* File Name: MainActivity.java
* Full Name: Kedar Kulkarni, Dnyanshree Shengulwar, Marissa McLaughlin
* */
public class Ticket implements Parcelable{
    private String name, source,destination, trip, departureDate, departureTime, returnDate, returnTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public static Creator<Ticket> getCREATOR() {
        return CREATOR;
    }

    public Ticket(String name, String source, String destination, String trip, String departureDate, String departureTime, String returnDate, String returnTime) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.trip = trip;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
    }
    @Override
      public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(source);
        dest.writeString(destination);
        dest.writeString(trip);
        dest.writeString(departureDate);
        dest.writeString(departureTime);
        dest.writeString(returnDate);
        dest.writeString(returnTime);
    }

    public static final Parcelable.Creator<Ticket> CREATOR
            = new Parcelable.Creator<Ticket>() {
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    private Ticket(Parcel in) {
        this.name = in.readString();
        this.source = in.readString();
        this.destination = in.readString();
        this.trip = in.readString();
        this.departureDate = in.readString();
        this.departureTime = in.readString();
        this.returnDate = in.readString();
        this.returnTime = in.readString();
    }
    @Override
    public String toString() {
        return "example.com.ticketreservation.Ticket{" +
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", trip='" + trip + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", returnTime='" + returnTime + '\'' +
                '}';
    }


}
