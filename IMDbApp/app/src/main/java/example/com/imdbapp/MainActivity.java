package example.com.imdbapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

/*
Assignment: Homework 04
FileName: MainActivity.java
Group 22
Team Members: Dnyanshree Shengulwar,
              Kedar Vijay Kulkarni,
              Marissa McLaughlin
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.app_icon);

        final EditText etmovie = (EditText) findViewById(R.id.editText);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchMovieActivity.class);
                intent.putExtra("Movie", etmovie.getText().toString());
                startActivity(intent);
                //finish();
            }
        });
    }

}
