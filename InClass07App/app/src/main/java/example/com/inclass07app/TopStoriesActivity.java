package example.com.inclass07app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;
/*
* Assignment: InClass07
* Filename: TopStoriesActivity.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class TopStoriesActivity extends AppCompatActivity implements GetNewsListAsyncTask.contextInterface {
    public static ProgressDialog progressDialog;
    ListView listViewNews;
    ArrayList<Story> stories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_stories);

        listViewNews = (ListView) findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Stories");
        String url;
        url="http://api.nytimes.com/svc/topstories/v1/"+getIntent().getStringExtra("category")+".json?api-key=3f991e0e50d2542de9424e769fa55703:10:74582583";
        Log.d("URL",url.toString());
        new GetNewsListAsyncTask(this).execute(url);

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(TopStoriesActivity.this, DetailsActivity.class);
                intent.putExtra("Title", stories.get(position).getStoryTitle());
                intent.putExtra("Byline", stories.get(position).getStoryByline());
                intent.putExtra("Abstract",stories.get(position).getStoryAbstract());
                intent.putExtra("Url",stories.get(position).getStoryNormalImageUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupData(ArrayList<Story> stories) {
        this.stories=stories;
        StoryAdapter adapter = new StoryAdapter(this, R.layout.row_item_layout, stories);
        listViewNews.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

    }
}
