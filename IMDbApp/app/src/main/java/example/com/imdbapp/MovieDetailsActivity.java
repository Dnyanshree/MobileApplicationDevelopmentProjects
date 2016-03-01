package example.com.imdbapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity implements GetMovieDetailsAsyncTask.contextInterface {
    public static ProgressDialog progressDialog;
    ArrayList<Movie> Movies;
    int clickedItemIndex;
    TextView tvTitle,tvRelease,tvGenre,tvDirector, tvActors,tvPlot;
    static ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        imageViewResult= (ImageView) findViewById(R.id.imageViewResult);
        tvTitle=(TextView) findViewById(R.id.textViewTitle);
        tvRelease=(TextView) findViewById(R.id.textViewReleaseDate);
        tvGenre= (TextView) findViewById(R.id.textViewGenre);
        tvActors=(TextView) findViewById(R.id.textViewActors);
        tvDirector=(TextView) findViewById(R.id.textViewDirector);
        tvPlot= (TextView) findViewById(R.id.textViewPlot);

        Movies= getIntent().getParcelableArrayListExtra("Movies");
        progressDialog = new ProgressDialog(MovieDetailsActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Movie");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon);

        final String imdbID = getIntent().getStringExtra("imdbID");
        Log.d("Demo Movie",getIntent().getIntExtra("clickedID",0)+"");
        clickedItemIndex= getIntent().getIntExtra("clickedID",0);
        new GetMovieDetailsAsyncTask(this).execute("http://www.omdbapi.com/?i="+imdbID);
        findViewById(R.id.imageViewResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this, MovieWebViewActivity.class);
                intent.putExtra("imdbID", Movies.get(clickedItemIndex).getImdbID());
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.imageViewArrowRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickedItemIndex < Movies.size()-1)
                    clickedItemIndex++;
                else
                    clickedItemIndex=0;

                new GetMovieDetailsAsyncTask(MovieDetailsActivity.this).
                        execute("http://www.omdbapi.com/?i=" + Movies.get(clickedItemIndex).getImdbID());
            }
        });

        findViewById(R.id.imageViewArrowLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickedItemIndex > 0)
                    clickedItemIndex--;
                else
                    clickedItemIndex= Movies.size()-1;

                new GetMovieDetailsAsyncTask(MovieDetailsActivity.this).
                        execute("http://www.omdbapi.com/?i=" + Movies.get(clickedItemIndex).getImdbID());
            }
        });
        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setupData(Movie movies) {
        Movies.remove(clickedItemIndex);
        Movies.add(clickedItemIndex, movies);

        tvTitle.setText(Movies.get(clickedItemIndex).getTitle() + "(" + Movies.get(clickedItemIndex).getYear() + ")");

        String dateStr =  Movies.get(clickedItemIndex).getReleased();
        String formatedDate="";
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        DateFormat writeFormat= new SimpleDateFormat("MMM dd yyyy");
        Date date=null;
        try {
            date = (Date) formatter.parse(dateStr);
            formatedDate=writeFormat.format(date);

        }
        catch (ParseException e){
            e.printStackTrace();
        }


        tvRelease.setText(getResources().getString(R.string.releaseDate) + formatedDate);
        tvGenre.setText(getResources().getString(R.string.genre) + Movies.get(clickedItemIndex).getGenre());
        tvDirector.setText(getResources().getString(R.string.director) + Movies.get(clickedItemIndex).getDirector());
        tvActors.setText(getResources().getString(R.string.actors) + Movies.get(clickedItemIndex).getActors());
        tvPlot.setText(getResources().getString(R.string.plot) + "\n" + Movies.get(clickedItemIndex).getPlot());

        RatingBar ratingBar= (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setClickable(false);
        if(!Movies.get(clickedItemIndex).getImdbRating().equals("N/A"))
            ratingBar.setRating(Float.parseFloat(Movies.get(clickedItemIndex).getImdbRating())/10*5);
        else
            ratingBar.setRating(0);

        new getThumbnail().execute(Movies.get(clickedItemIndex).getPoster());
    }
}
