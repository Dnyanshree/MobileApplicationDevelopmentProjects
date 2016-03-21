package example.com.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class CityDataActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_data);


        TabHost tabHost = getTabHost();
        String City,State;
        City=getIntent().getStringExtra("City");
        State=getIntent().getStringExtra("State");
        TabSpec hourlyData = tabHost.newTabSpec("Hourly Data");
        hourlyData.setIndicator("Hourly Data");
        // setting Title and Icon for the Tab
        Intent hourlyDataIntent = new Intent(this, HourlyDataActivity.class);
        hourlyDataIntent.putExtra("City",City);
        hourlyDataIntent.putExtra("State", State);
        hourlyData.setContent(hourlyDataIntent);


        TabSpec forecast = tabHost.newTabSpec("Forecast");
        forecast.setIndicator("Forecast");
        // setting Title and Icon for the Tab
        Intent forecastIntent = new Intent(this, ForecastActivity.class);
        forecastIntent.putExtra("City",City);
        forecastIntent.putExtra("State", State);
        forecast.setContent(forecastIntent);

        tabHost.addTab(hourlyData);
        tabHost.addTab(forecast);
    }
}
