package example.com.inclass05;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
/*
Assignment - InClass05
Name: Dnyanshree Shengulwar
FileName - GetDictionaryAsyncTask.java
 */
/**
 * Created by Dnyanshree on 2/15/2016.
 */
public class GetDictionaryAsyncTask extends AsyncTask<String, Void, String> {
    MainActivity activity;

        @Override
        protected String doInBackground(String... params) {
            BufferedReader br = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                //Bitmap image = BitmapFactory.decodeStream(con.getInputStream());//to get image
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                String[] keyValuesList, keyValue;
                while((line = br.readLine()) != null){
                    sb.append(line + "\n");

                    keyValuesList = line.split(";");
                    for (String kvl: keyValuesList) {
                        keyValue =kvl.split(",");
                        if(MainActivity.paramList.get(keyValue[0]) != null){
                            ArrayList<String> values = MainActivity.paramList.get(keyValue[0]);
                            values.add(keyValue[1]);
                            MainActivity.paramList.put(keyValue[0], values);
                        }
                        else{
                            ArrayList<String> values = new ArrayList<String>();
                            values.add(keyValue[1]);
                            MainActivity.paramList.put(keyValue[0], values);
                        }
                    }
                }
            return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(br != null){
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.progressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            MainActivity.progressDialog.dismiss();
            if(result != null){
                Log.d("Demo", result);
            }else{
                Log.d("Demo","No Result Sent");
            }
        }

}
