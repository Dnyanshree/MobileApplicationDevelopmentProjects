package example.com.foxnewsapp;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/*
* Assignment: InClass6
* Filename: NewsDetailsActivity.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class NewsDetailsActivity extends AppCompatActivity {
    TextView tvTitle,tvPubDate,tvDesc;
    static ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        imageViewResult= (ImageView) findViewById(R.id.imageView);

        tvDesc= (TextView)findViewById(R.id.textViewDesc);
        tvPubDate= (TextView) findViewById(R.id.textViewPubdate);
        tvTitle= (TextView) findViewById(R.id.textViewTitle);


        String dateStr = getIntent().getStringExtra("Pubdate");
        String formatedDate="";
        DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.US);
        DateFormat writeFormat= new SimpleDateFormat("MM/dd/yyyy HH:mm a");
        Date date=null;
        try {
            date = (Date) formatter.parse(dateStr);
            formatedDate=writeFormat.format(date);

        }
        catch (ParseException e){
            e.printStackTrace();
        }





        tvTitle.setText(getIntent().getStringExtra("Title"));
        tvPubDate.setText(formatedDate);
        tvDesc.setText(getIntent().getStringExtra("Description").split("<")[0]);
        new getThumbnail().execute(getIntent().getStringExtra("Thumbnail"));
        final String link=getIntent().getStringExtra("link");
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NewsDetailsActivity.this, NewsWebView.class);
                intent.putExtra("link",link);
                startActivity(intent);
            }
        });
    }


}
