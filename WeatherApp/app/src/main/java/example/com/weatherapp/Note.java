package example.com.weatherapp;

/**
 * Created by Dnyanshree on 3/19/2016.
 */
public class Note {
    private long citykey;
    private String date, note;

    public Note(String date, String note) {
        this.date = date;
        this.note = note;
    }

    public Note() {
    }

    public long getCitykey() {
        return citykey;
    }

    public void setCitykey(long citykey) {
        this.citykey = citykey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Note{" +
                "citykey=" + citykey +
                ", date='" + date + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
