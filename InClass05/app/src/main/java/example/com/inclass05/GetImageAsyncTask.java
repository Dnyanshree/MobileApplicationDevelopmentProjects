package example.com.inclass05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
/*
Assignment - InClass05
Name: Dnyanshree Shengulwar
FileName - GetImageAsyncTask.java
 */
/**
 * Created by Dnyanshree on 2/15/2016.
 */
public class GetImageAsyncTask extends AsyncTask<RequestParams,Void,Bitmap> {


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        MainActivity.progressDialog1.dismiss();
        MainActivity.ivPhoto.setImageBitmap(bitmap);
    }

    @Override
    protected Bitmap doInBackground(RequestParams... params) {
        try {
            HttpURLConnection con= params[0].setupConnection();
            Bitmap image= BitmapFactory.decodeStream(con.getInputStream());

            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.progressDialog1.show();
    }


}