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
    interface contextInterface{
        public void setupData(ArrayList<Weather> wData);
    }
    public GetHourlyDataAsyncTask(contextInterface activity){
        this.activity= activity;
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
            if(statusCode == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return WeatherUtil.WeatherPullParser.weatherParser(in);
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
        HourlyDataActivity.progressDialog.show();
    }
    @Override
    protected void onPostExecute(ArrayList<Weather> wData) {
        super.onPostExecute(wData);
        HourlyDataActivity.progressDialog.dismiss();
       if(wData != null){
            Log.d("Demo", wData.toString());
        }
        activity.setupData(wData);
    }
}
