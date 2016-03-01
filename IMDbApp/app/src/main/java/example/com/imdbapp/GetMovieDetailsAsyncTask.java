package example.com.imdbapp;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sairam on 2/27/2016.
 */
public class GetMovieDetailsAsyncTask extends AsyncTask<String, Void, Movie> {
private contextInterface activity;
interface contextInterface{
    //public Context getcontext();
    public void setupData(Movie movies);
}

    public GetMovieDetailsAsyncTask(contextInterface activity){
        this.activity= activity;
    }

    @Override
    protected Movie doInBackground(String... params) {
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

                return MoviesUtil.MoviesJSONParser.parseMovieDetails(sb.toString());

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
        MovieDetailsActivity.progressDialog.show();
    }

    @Override
    protected void onPostExecute(Movie movies) {
        super.onPostExecute(movies);
        MovieDetailsActivity.progressDialog.dismiss();
        /*if(movies != null){
            Log.d("Demo", movies.toString());
        }*/
        activity.setupData(movies);
    }
}
