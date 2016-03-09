package example.com.weatherapp;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HourlyDataActivity extends AppCompatActivity implements GetHourlyDataAsyncTask.contextInterface {
    String state, city;
     ArrayList<Weather> weatherData = null;
    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_data);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.weather_icon36dp);

        city = getIntent().getStringExtra("City");
        state = getIntent().getStringExtra("State");
        TextView tvCityName = (TextView) findViewById(R.id.textViewCityName);

        tvCityName.setText(city + ", " + state);
        //http://api.wunderground.com/api/api_key/hourly/q/state_initial/city_name.xml
        progressDialog = new ProgressDialog(HourlyDataActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Hourly Data");
        new GetHourlyDataAsyncTask(this).execute("http://api.wunderground.com/api/6b461fc81d9e1283/hourly/q/" + state + "/" + city +".xml");
    }
    //http://api.wunderground.com/api/6b461fc81d9e1283/hourly/q/CA/San_Francisco.xml
    @Override
    public void setupData(ArrayList<Weather> wData) {
        Log.d("Demo","Inside Hourly Data Activity");
        weatherData = wData;
        ListView listView = (ListView) findViewById(R.id.listView1);
        HourlyDataAdapter adapter = new HourlyDataAdapter(this, R.layout.row_hourlydata_layout, weatherData);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Demo", "Position: " + position + ", Value: " + weatherData.get(position).toString());
                Intent intent= new Intent(HourlyDataActivity.this, DetailsActivity.class);
                intent.putParcelableArrayListExtra("WeatherData", weatherData);
                intent.putExtra("clickedID", position);
                Log.d("Demo", String.valueOf(position));

                intent.putExtra("City", city);
                intent.putExtra("State", state);
                startActivity(intent);
            }
        });
    }
}

