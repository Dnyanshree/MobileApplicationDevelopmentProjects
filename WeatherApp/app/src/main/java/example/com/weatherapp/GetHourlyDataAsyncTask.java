package example.com.weatherapp;


import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetHourlyDataAsyncTask extends AsyncTask<String, Void, ArrayList<Weather>> {
    private contextInterface activity;
    String activityName;
    interface contextInterface{
        public void setupData(ArrayList<Weather> wData);
    }
    public GetHourlyDataAsyncTask(contextInterface activity,String activityName){
        this.activity= activity;
        this.activityName=activityName;
    }
    @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        try {
            params[0]=params[0].replace(" ","_");
            URL url = new URL(params[0]);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();

            if(statusCode == HttpURLConnection.HTTP_OK && params[0].contains("hourly")){
                InputStream in = con.getInputStream();
                return WeatherUtil.WeatherPullParser.weatherParser(in);
            }
            else if(statusCode == HttpURLConnection.HTTP_OK && params[0].contains("forecast10day")){
                InputStream in = con.getInputStream();
                return WeatherUtil.WeatherForecastPullParser.weatherParser(in);
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
    protected void onPreExecute() {
        super.onPreExecute();

       try{
           if(activityName.equals("hourly"))
                HourlyDataActivity.progressDialog.show();
            else
               ForecastActivity.progressDialog.show();
       }
       catch (Exception e){e.printStackTrace();}
    }
    @Override
    protected void onPostExecute(ArrayList<Weather> wData) {
        super.onPostExecute(wData);
        try{ if(activityName.equals("hourly"))
            HourlyDataActivity.progressDialog.dismiss();
        else
            ForecastActivity.progressDialog.dismiss();}
        catch (Exception e){e.printStackTrace();}
        activity.setupData(wData);
    }
}
