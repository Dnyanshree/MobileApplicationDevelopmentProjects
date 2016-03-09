package example.com.weatherapp;

import android.os.AsyncTask;
import android.util.Log;
import org.xmlpull.v1.XmlPullParserException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ValidationAsyncTask extends AsyncTask<String,Void,Boolean > {
    sendResult instance;


    interface sendResult{
        void result(boolean result);
    }
    public ValidationAsyncTask(sendResult instance){this.instance=instance;}
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        instance.result(aBoolean);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            params[0]=params[0].replace(" ","%20");
            URL url = new URL(params[0]);
            Log.d("URL",url.toString());

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


                if(!(sb==null) && sb.toString().length()>2){
                    Log.d("Validation",sb.toString()+sb.toString().length());
                    return true;
                }
                return false;
            }
            else if(statusCode==HttpURLConnection.HTTP_BAD_REQUEST){
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
