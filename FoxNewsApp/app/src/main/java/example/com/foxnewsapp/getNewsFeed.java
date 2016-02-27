package example.com.foxnewsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class getNewsFeed extends AsyncTask<String,Void,ArrayList<News>> {
    contextInterface activity;
    ProgressDialog progressDialog;



    static public interface contextInterface{
        public Context getcontext();
        public void setupData(ArrayList<News> news);
    }

    public getNewsFeed(contextInterface activity){
        this.activity= activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =new ProgressDialog(activity.getcontext());
        progressDialog.setMessage("Loading News");
        progressDialog.show();
    }

    protected ArrayList<News> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return NewsUtils.NewsPullParser.newsParser(in);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        progressDialog.dismiss();
        if(news != null){
            Log.d("Demo", news.toString());
        }
        activity.setupData(news);

    }
}
