package example.com.inclass07app;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
/*
* Assignment: InClass07
* Filename: DetailsActivity.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class DetailsActivity extends AppCompatActivity implements getImage.contextInterface {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView title= (TextView)findViewById(R.id.textViewStoryTitle);
        TextView Byline=(TextView) findViewById(R.id.textViewStoryByline);
        TextView Abstract= (TextView) findViewById(R.id.textViewAbstract);

        title.setText(getIntent().getStringExtra("Title"));
        Byline.setText(getIntent().getStringExtra("Byline"));
        Abstract.setText(getIntent().getStringExtra("Abstract"));
        new getImage(this).execute(getIntent().getStringExtra("Url"));
        imageView=(ImageView) findViewById(R.id.imageView);

    }

    @Override
    public void setupThumbnail(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);

    }
}
