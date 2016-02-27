package example.com.foxnewsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/*
* Assignment: InClass6
* Filename: getThumbnail.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class getThumbnail  extends AsyncTask<String,Void,Bitmap> {

    MainActivity activity;


        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if(bitmap==null) Log.d("Image", "it is null");
            else NewsDetailsActivity.imageViewResult.setImageBitmap(bitmap);
            //if(bitmap!=null)NewsActivity.imageView.setImageBitmap(bitmap);
            //MainActivity.imageViewResult.setImageBitmap(bitmap);
            //activity.setImage(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();
                Bitmap image= BitmapFactory.decodeStream(con.getInputStream());
                if(image==null)Log.d("Image","ImageNull");
                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

}
