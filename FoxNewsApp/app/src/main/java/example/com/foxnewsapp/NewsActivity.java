package example.com.foxnewsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/*
* Assignment: InClass6
* Filename: NewsActivity.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class NewsActivity extends AppCompatActivity implements getNewsFeed.contextInterface {
    LinearLayout linearLayout;
    View.OnClickListener onClickListener;
    static ImageView imageView;
    ArrayList<News> newsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        new getNewsFeed(this).execute(getIntent().getStringExtra("URL"));



        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsActivity.this, v.getTag() + "", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(NewsActivity.this,NewsDetailsActivity.class);
                intent.putExtra("Title",newsList.get(v.getId()).getTitle().toString());
                intent.putExtra("Description", newsList.get(v.getId()).getDescription().toString());
                intent.putExtra("Pubdate",newsList.get(v.getId()).getPubDate().toString());
                intent.putExtra("Thumbnail",newsList.get(v.getId()).getThumbnail().toString());
                intent.putExtra("link",newsList.get(v.getId()).getLink().toString());
                Log.d("Demo",newsList.get(v.getId()).getThumbnail().toString());
                startActivity(intent);

            }
        };
        RelativeLayout relativeLayout= (RelativeLayout)findViewById(R.id.relativeLayout);

        ScrollView scrollView= new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout= new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        relativeLayout.setBackgroundColor(getResources().getColor(R.color.opaque_red));
        scrollView.addView(linearLayout);


        relativeLayout.addView(scrollView);

    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void setupData(ArrayList<News> news) {
        int i;
        newsList=news;
        for(i=news.size()-1;i>=0;i--){
            TextView textView= new TextView(this);
            textView.setTag(news.get(i).getTitle());
            textView.setText(news.get(i).getTitle());
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
