package example.com.weatherapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class AddCityActivity extends AppCompatActivity implements ValidationAsyncTask.sendResult {
    EditText etCity,etState;
    String City,State;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.weather_icon36dp);

        etCity = (EditText) findViewById(R.id.editTextCity);
        etState = (EditText) findViewById(R.id.editTextState);

        findViewById(R.id.buttonSaveCity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                City=etCity.getText().toString();
                State=etState.getText().toString();

                new ValidationAsyncTask(AddCityActivity.this).execute("http://api.sba.gov/geodata/all_data_for_city_of/" + City + "/" + State + ".JSON");

            }
        });
    }

    @Override
    public void result(boolean result) {

        Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
        if(result==true){
            intent.putExtra("City", City);
            intent.putExtra("State", State);
            startActivity(intent);
        }
        else{
            Toast.makeText(AddCityActivity.this, "Please enter valid City and State Initial!", Toast.LENGTH_LONG).show();
        }
    }
}

