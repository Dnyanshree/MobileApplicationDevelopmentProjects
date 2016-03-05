package example.com.inclass07app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
* Assignment: InClass07
* Filename: getImage.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */

public class getImage  extends AsyncTask<String,Void,Bitmap> {
    private contextInterface activity;
    interface contextInterface{

        public void setupThumbnail(Bitmap bitmap);
    }

    public getImage(contextInterface activity){
        this.activity= activity;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        try {

            URL url= new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Bitmap image= BitmapFactory.decodeStream(con.getInputStream());
            if(image==null)Log.d("Debug","ImageNull");
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {

        activity.setupThumbnail(bitmap);
    }
}
