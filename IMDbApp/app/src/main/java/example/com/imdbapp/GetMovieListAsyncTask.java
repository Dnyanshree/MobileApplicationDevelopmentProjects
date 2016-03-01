package example.com.imdbapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetMovieListAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {
    private contextInterface activity;
     interface contextInterface{
         //public Context getcontext();
         public void setupData(ArrayList<Movie> movies);
    }

    public GetMovieListAsyncTask(contextInterface activity){
        this.activity= activity;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    sb.append(line);
                    line = reader.readLine();
                }

                return MoviesUtil.MoviesJSONParser.parseMovie(sb.toString());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        SearchMovieActivity.progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        SearchMovieActivity.progressDialog.dismiss();
        /*if(movies != null){
            Log.d("Demo", movies.toString());
        }*/
       activity.setupData(movies);
    }
}
