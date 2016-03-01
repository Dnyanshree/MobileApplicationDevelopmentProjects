package example.com.imdbapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dnyanshree on 2/26/2016.
 */
public class Movie implements Parcelable{
    String title, year, imdbID, poster, released, genre, director, actors, plot, imdbRating;

    protected Movie(Parcel in) {
        title = in.readString();
        year = in.readString();
        imdbID = in.readString();
        poster = in.readString();
        released = in.readString();
        genre = in.readString();
        director = in.readString();
        actors = in.readString();
        plot = in.readString();
        imdbRating = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

   /* public static Movie createMovie(JSONObject js) throws JSONException {
        Movie movie = new Movie();
        //title, year, imdbID, poster, released,genre, director, actors, plot and imdbRating.
        movie.setTitle(js.getString("Title"));
        movie.setYear(js.getString("Year"));
        movie.setImdbID(js.getString("imdbID"));
        movie.setPoster(js.getString("Poster"));
        movie.setReleased(null);
        movie.setGenre(null);
        movie.setDirector(null);
        movie.setActors(null);
        movie.setPlot(null);
        movie.setImdbRating(null);
        return movie;
    }


    public static Movie createMovieDetails(JSONObject js) throws JSONException {
        Movie movie = new Movie();
        //title, year, imdbID, poster, released,genre, director, actors, plot and imdbRating.
        movie.setTitle(js.getString("Title"));
        movie.setYear(js.getString("Year"));
        movie.setImdbID(js.getString("imdbID"));
        movie.setPoster(js.getString("Poster"));
        movie.setReleased(js.getString("Released"));
        movie.setGenre(js.getString("Genre"));
        movie.setDirector(js.getString("Director"));
        movie.setActors(js.getString("Actors"));
        movie.setPlot(js.getString("Plot"));
        movie.setImdbRating(js.getString("imdbRating"));
        return movie;
    }*/
    public String getTitle() {
        return title;
    }

    public Movie(String title, String year, String imdbID, String poster, String released, String genre, String director, String actors, String plot, String imdbRating) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.poster = poster;
        this.released = released;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.imdbRating = imdbRating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", poster='" + poster + '\'' +
                ", released='" + released + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(imdbID);
        dest.writeString(poster);
        dest.writeString(released);
        dest.writeString(genre);
        dest.writeString(director);
        dest.writeString(actors);
        dest.writeString(plot);
        dest.writeString(imdbRating);
    }
}
