package example.com.inclass07app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
/*
* Assignment: InClass07
* Filename: MainActivity.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] categories = {"Home","World","National","Politics","NYRegion","Business","Opinion",
        "Technology","Science","Health","Sports","Arts","Fashion","Dining","Travel","Magazine","RealEstate"};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner= (Spinner) findViewById(R.id.spinnerCategories);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,
                categories);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoriesAdapter);

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,TopStoriesActivity.class);

                intent.putExtra("category",categories[spinner.getSelectedItemPosition()].toLowerCase());
                startActivity(intent);
            }
        });
    }
}
