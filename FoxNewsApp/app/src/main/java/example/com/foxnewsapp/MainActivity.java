package example.com.foxnewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/*
* Assignment: InClass6
* Filename: MainActivity.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class MainActivity extends AppCompatActivity{
    HashMap<String, String> categoriesHMAP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesHMAP = new HashMap<String, String>();
        populateHashmap();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,v.getTag()+"",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,NewsActivity.class);
                intent.putExtra("URL",categoriesHMAP.get(v.getTag()));
                startActivity(intent);
            }
        };

        RelativeLayout relativeLayout= (RelativeLayout)findViewById(R.id.relativeLayout);

        ScrollView scrollView= new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout linearLayout= new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.opaque_red));
        scrollView.addView(linearLayout);
        int i=0;
        for(String S: categoriesHMAP.keySet()){
            TextView textView= new TextView(this);
            textView.setTag(S);
            textView.setText(S);
            textView.setTextSize(40);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setGravity(Gravity.CENTER);
            textView.setClickable(true);
            textView.setOnClickListener(onClickListener);
            linearLayout.addView(textView);
            i++;
        }

        relativeLayout.addView(scrollView);

    }


    void populateHashmap(){

        categoriesHMAP.put("Most Popular", "http://feeds.foxnews.com/foxnews/most-popular");
        categoriesHMAP.put("Entertainment", "http://feeds.foxnews.com/foxnews/entertainment");
        categoriesHMAP.put("Health", "http://feeds.foxnews.com/foxnews/health");
        categoriesHMAP.put("Life Style","http://feeds.foxnews.com/foxnews/section/lifestyle");
        categoriesHMAP.put("Opinion", "http://feeds.foxnews.com/foxnews/opinion");
        categoriesHMAP.put("Politics", "http://feeds.foxnews.com/foxnews/politics");
        categoriesHMAP.put("Science", "http://feeds.foxnews.com/foxnews/science");
        categoriesHMAP.put("Sports", "http://feeds.foxnews.com/foxnews/sports");
        categoriesHMAP.put("Tech", "http://feeds.foxnews.com/foxnews/tech");
        categoriesHMAP.put("Travel", "http://feeds.foxnews.com/foxnews/internal/travel/mixed");
        categoriesHMAP.put("U.S.", "http://feeds.foxnews.com/foxnews/national");


    }


}
