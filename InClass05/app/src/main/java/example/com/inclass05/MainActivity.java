package example.com.inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
/*
Assignment - InClass05
FileName - MainActivity.java
Name: Dnyanshree Shengulwar
 */
public class MainActivity extends AppCompatActivity {
    static ProgressDialog progressDialog, progressDialog1;
    static HashMap<String, ArrayList<String>> paramList;
    EditText etSearch;
    int count = 0;
    RequestParams params;
    ArrayList<String> images;
    public static ImageView ivPhoto, ivNext, ivPrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSearch = (EditText) findViewById(R. id.editTextSearch);
        ivPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        ivNext = (ImageView) findViewById(R.id.imageViewNext);
        ivPrev = (ImageView) findViewById(R.id.imageViewPrev);
        ivNext.setClickable(false);

        ivPrev.setClickable(false);
        if (isConnectedOnline()){
            Toast.makeText(MainActivity.this, "You are connected to the network!!", Toast.LENGTH_LONG).show();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Dictionary...");
            paramList = new HashMap<String, ArrayList<String>>();
            new GetDictionaryAsyncTask().execute("http://dev.theappsdr.com/apis/spring_2016/inclass5/URLs.txt");
        }
        else{
            Toast.makeText(MainActivity.this, "Not connected. Check you network!!", Toast.LENGTH_LONG).show();
        }
        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog1 = new ProgressDialog(MainActivity.this);
                progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog1.setCancelable(false);
                progressDialog1.setMessage("Loading Photo...");
                if (paramList != null && paramList.get(etSearch.getText().toString()) != null) {
                    images = paramList.get(etSearch.getText().toString());
                    params = new RequestParams("GET", images.get(count));
                    new GetImageAsyncTask().execute(params);
                    ivNext.setClickable(true);
                    ivPrev.setClickable(true);
                }
            else
            {
                Toast.makeText(MainActivity.this, "No Images Found", Toast.LENGTH_LONG).show();
                ivPhoto.setImageBitmap(null);
                Log.d("Demo", "Something is wrong");
            }
        }
    });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count < images.size()) {
                    count++;
                    params = new RequestParams("GET", images.get(count));
                    new GetImageAsyncTask().execute(params);
                }
                else if(count == images.size() || count > images.size()){
                    count = 0;
                    params = new RequestParams("GET", images.get(count));
                    new GetImageAsyncTask().execute(params);
                    count++;
                }
            }
        });
         ivPrev.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (count > 0) {
                                count--;
                                params = new RequestParams("GET", images.get(count));
                                new GetImageAsyncTask().execute(params);
                            } else {
                                count = images.size() - 1;
                                params = new RequestParams("GET", images.get(count));
                                new GetImageAsyncTask().execute(params);
                                count--;
                            }
                        }
         });

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
