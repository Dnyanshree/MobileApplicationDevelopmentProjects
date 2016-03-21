package example.com.http_demos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonCheckNetwork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedOnline()){
                    Toast.makeText(MainActivity.this, "You are connected to the network!!", Toast.LENGTH_LONG).show();
                    //new getData().execute("http://rss.cnn.com/rss/cnn_travel.rss");
                    //new getImage().execute("http://danielandhannah.com/wp-content/uploads/2015/07/aa4.jpg");
                    //RequestParams  params = new RequestParams("GET", "http://dev.theappsdr.com/lectures/params.php");
                    RequestParams  params = new RequestParams("POST", "http://dev.theappsdr.com/lectures/params.php");
                    params.addParams("key1", "value1");
                    params.addParams("key2", "value2");
                    params.addParams("key3", "value3");
                    params.addParams("key4", "value4");

                    new getDataWithParams().execute(params);
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected. Check you network!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public class getDataWithParams extends AsyncTask<RequestParams, Void, String>{
        @Override
        protected String doInBackground(RequestParams... params) {
            BufferedReader br = null;

            try {
                HttpURLConnection con = params[0].setupConnection();
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = br.readLine()) != null){
                    sb.append(line + "\n");
                }
                return sb.toString();
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
        protected void onPostExecute(String result) {
            if(result != null){
                Log.d("Demo", result);
            }else{
                Log.d("Demo","No Result Sent");
            }
        }
    }
    public class getData extends AsyncTask<String, Void, String>{
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
                while((line = br.readLine()) != null){
                    sb.append(line + "\n");
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
        protected void onPostExecute(String result) {
            if(result != null){
                Log.d("Demo", result);
            }else{
                Log.d("Demo","No Result Sent");
            }
        }
    }
    //http://rss.cnn.com/rss/cnn_travel.rss
    public class getImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream in = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                in = con.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(in);//to get image
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(in!= null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if(result != null){
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(result);
            }
        }
    }
    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
