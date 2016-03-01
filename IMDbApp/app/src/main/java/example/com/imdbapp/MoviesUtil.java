package example.com.imdbapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Dnyanshree on 2/26/2016.
 */
public class MoviesUtil {
    static public class MoviesJSONParser{

        static  ArrayList<Movie> parseMovie(String in) throws JSONException {
/*
{"Search":[{"Title":"Batman Begins","Year":"2005","imdbID":"tt0372784","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg"},{"Title":"Batman","Year":"1989","imdbID":"tt0096895","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg"},{"Title":"Batman Returns","Year":"1992","imdbID":"tt0103776","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BODM2OTc0Njg2OF5BMl5BanBnXkFtZTgwMDA4NjQxMTE@._V1_SX300.jpg"},{"Title":"Batman Forever","Year":"1995","imdbID":"tt0112462","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMjAwOTEyNjg0M15BMl5BanBnXkFtZTYwODQyMTI5._V1_SX300.jpg"},{"Title":"Batman & Robin","Year":"1997","imdbID":"tt0118688","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMjE3NzcyNzM4MF5BMl5BanBnXkFtZTYwNDA3Mzk4._V1_SX300.jpg"},{"Title":"Batman: Under the Red Hood","Year":"2010","imdbID":"tt1569923","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMTMwNDEyMjExOF5BMl5BanBnXkFtZTcwMzU4MDU0Mw@@._V1_SX300.jpg"},{"Title":"Batman: The Dark Knight Returns, Part 1","Year":"2012","imdbID":"tt2313197","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg"},{"Title":"Batman: Mask of the Phantasm","Year":"1993","imdbID":"tt0106364","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMTMzODU0NTYxN15BMl5BanBnXkFtZTcwNDUxNzUyMQ@@._V1_SX300.jpg"},{"Title":"Batman: The Dark Knight Returns, Part 2","Year":"2013","imdbID":"tt2166834","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMjMzMjkxOTc3MV5BMl5BanBnXkFtZTcwNTE0ODAwOQ@@._V1_SX300.jpg"},{"Title":"Batman: The Movie","Year":"1966","imdbID":"tt0060153","Type":"movie","Poster":"http://ia.media-imdb.com/images/M/MV5BMTkzODAyMjg2Ml5BMl5BanBnXkFtZTgwMzI4NzM1MjE@._V1_SX300.jpg"}],"totalResults":"226","Response":"True"}
 */


            ArrayList<Movie> moviesList = new ArrayList<Movie>();
            JSONObject root = new JSONObject(in);
            JSONArray moviesJSONArray = root.getJSONArray("Search");
           // JSONArray jsonArr = new JSONArray(jsonArrStr);
            JSONArray sortedJsonArray = new JSONArray();

            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < moviesJSONArray.length(); i++) {
                jsonValues.add(moviesJSONArray.getJSONObject(i));
            }
            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                //You can change "Name" with "ID" if you want to sort by ID
                private static final String KEY_NAME = "Year";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = new String();
                    String valB = new String();

                    try {
                        valA = (String) a.getString(KEY_NAME);
                        valB = (String) b.getString(KEY_NAME);
                    } catch (JSONException e) {
                        //do something
                    }
                    return valA.compareTo(valB);
                    //if you want to change the sort order, simply use the following:
                    //return -valA.compareTo(valB);
                }
            });

            for (int i = 0; i < moviesJSONArray.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }
            for(int i=0; i<sortedJsonArray.length(); i++){
                JSONObject movieJSONObject = sortedJsonArray.getJSONObject(i);
                //JSONArray movieArray= movieJSONObject.getJSONArray("Search");
                Movie movie = new Movie(movieJSONObject.getString("Title"),movieJSONObject.getString("Year"),
                        movieJSONObject.getString("imdbID"),movieJSONObject.getString("Poster"),
                        null,null,null,null,null,null);

                moviesList.add(movie);
            }
            return moviesList;

        }
        static Movie parseMovieDetails(String in) throws JSONException{
            JSONObject root = new JSONObject(in);

            Movie movie= new Movie(root.getString("Title"),root.getString("Year"),root.getString("imdbID"),root.getString("Poster"),
                    root.getString("Released"),root.getString("Genre"),root.getString("Director"),root.getString("Actors"),
                    root.getString("Plot"),root.getString("imdbRating"));
            Log.d("Parcelable",movie.toString());
            return movie;
        }

    }
}
