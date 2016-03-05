package example.com.inclass07app;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/*
* Assignment: InClass07
* Filename: GetNewsListAsyncTask.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class GetNewsListAsyncTask  extends AsyncTask<String, Void, ArrayList<Story> > {
    private contextInterface activity;
    interface contextInterface{
        //public Context getcontext();
        public void setupData(ArrayList<Story> movies);
    }

    public GetNewsListAsyncTask(contextInterface activity){
        this.activity= activity;
    }

    @Override
    protected ArrayList<Story> doInBackground(String... params) {
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

                return StoryUtil.StoriesJSONParser.parseStory(sb.toString());

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
        TopStoriesActivity.progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Story> stories) {
        super.onPostExecute(stories);
        TopStoriesActivity.progressDialog.dismiss();

        activity.setupData(stories);
    }


}
