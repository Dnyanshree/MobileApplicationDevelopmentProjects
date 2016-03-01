package example.com.imdbapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchMovieActivity extends AppCompatActivity implements GetMovieListAsyncTask.contextInterface{
    public static ProgressDialog progressDialog;
    LinearLayout linearLayout;
    ArrayList<Movie> moviesList;
    View.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon);

        String movieName = getIntent().getStringExtra("Movie");
        if (isConnectedOnline()) {
            //Toast.makeText(SearchMovieActivity.this, "You are connected to the network!!", Toast.LENGTH_LONG).show();
            progressDialog = new ProgressDialog(SearchMovieActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Movie List");
            //paramList = new HashMap<String, ArrayList<String>>();
             //if(getIntent().getParcelableArrayListExtra("Movies")==null)
                new GetMovieListAsyncTask(this).execute("http://www.omdbapi.com/?type=movie&s=" + movieName);//,SearchMovieActivity.this.getLocalClassName());
            //else {
            //     moviesList=getIntent().getParcelableArrayListExtra("Movies");
              //   setupData(moviesList);
             //}
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            ScrollView scrollView = new ScrollView(this);
            scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(linearLayout);
            relativeLayout.addView(scrollView);

            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SearchMovieActivity.this, v.getTag() + "", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(SearchMovieActivity.this,MovieDetailsActivity.class);
                    intent.putParcelableArrayListExtra("Movies",moviesList);
                    intent.putExtra("imdbID", moviesList.get(v.getId()).getImdbID().toString());
                    intent.putExtra("clickedID",v.getId());
                    startActivity(intent);
                }
            };

        } else {
            Toast.makeText(SearchMovieActivity.this, "Not connected. Check you network!!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
/*
    @Override
    public Context getcontext() {
        return this;
    }

    */

    @Override
    public void setupData(ArrayList<Movie> movies) {
        int i;
        moviesList = movies;
        if(moviesList==null)
        {
            Toast.makeText(SearchMovieActivity.this,"Oops something went wrong!",Toast.LENGTH_LONG).show();
            return;
        }
        for (i = movies.size() - 1; i >= 0; i--) {
            TextView textView = new TextView(this);
            textView.setTag(movies.get(i).getTitle());

            String abc =movies.get(i).getTitle() + "(" + movies.get(i).getYear() + ")";
            //textView.setText(movies.get(i).getTitle());
            textView.setText(abc);
            textView.setTextSize(20);
            textView.setId(i);
            //textView.setTextColor(getResources().getColor(R.color.white));
            //textView.setGravity(Gravity.CENTER);
            textView.setClickable(true);
            textView.setOnClickListener(onClickListener);

            linearLayout.addView(textView);

        }
    }

}
