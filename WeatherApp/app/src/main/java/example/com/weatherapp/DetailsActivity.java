package example.com.weatherapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ArrayList<Weather> weatherList;
    String city, state;
    int clickedItemIndex=0;
    ImageView imageView;
    TextView tvCityAndTime,tvTemp,tvCloudsValue,tvMaxTemp,tvMinTemp,tvFeelsLike,tvHumidity,tvDewPoint,tvPressure,tvClouds,tvWinds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.weather_icon36dp);

        tvCityAndTime = (TextView) findViewById(R.id.tectViewCityName);
        tvTemp = (TextView) findViewById(R.id.textViewTemperature);
        tvCloudsValue = (TextView) findViewById(R.id.textViewCloudsValue);
        tvMaxTemp = (TextView) findViewById(R.id.textViewMaxTemp);
        tvMinTemp = (TextView) findViewById(R.id.textViewMinTemp);
        tvFeelsLike = (TextView) findViewById(R.id.textViewFeelsLike);
        tvHumidity = (TextView) findViewById(R.id.textViewHumidity);
        tvDewPoint = (TextView) findViewById(R.id.textViewDewpoint);
        tvPressure = (TextView) findViewById(R.id.textViewPressure);
        tvClouds = (TextView) findViewById(R.id.textViewClouds);
        tvWinds = (TextView) findViewById(R.id.textViewWinds);
        imageView = (ImageView) findViewById(R.id.imageViewIcon);

        weatherList = getIntent().getParcelableArrayListExtra("WeatherData");
        clickedItemIndex = getIntent().getIntExtra("clickedID", 0);
        city = getIntent().getStringExtra("City");
        state = getIntent().getStringExtra("State");
        tvCityAndTime.setText(city + ", " + state + "(" + weatherList.get(clickedItemIndex).getTime() + ")");
        tvTemp.setText(weatherList.get(clickedItemIndex).getTemperature()+ (char)0x00B0 + "F");
        tvCloudsValue.setText(weatherList.get(clickedItemIndex).getClimateType());
        tvMaxTemp.setText(" " + weatherList.get(clickedItemIndex).getMaximumTemp() + " Fahrenheit");
        tvMinTemp.setText(" " + weatherList.get(clickedItemIndex).getMinimumTemp() + " Fahrenheit");
        tvFeelsLike.setText(" " + weatherList.get(clickedItemIndex).getFeelsLike()+ " Fahrenheit");
        tvHumidity.setText(" " + weatherList.get(clickedItemIndex).getHumidity() + "%");
        tvDewPoint .setText(" " + weatherList.get(clickedItemIndex).getDewpoint() + " Fahrenheit");
        tvPressure.setText(" " + weatherList.get(clickedItemIndex).getPressure() + " hPa");
        tvClouds.setText(" " + weatherList.get(clickedItemIndex).getClouds());
        tvWinds.setText(" " + weatherList.get(clickedItemIndex).getWindSpeed() + "mph, " + weatherList.get(clickedItemIndex).getWindDirection());
        Picasso.with(this).load(weatherList.get(clickedItemIndex).getIconUrl()).into(imageView);
        findViewById(R.id.imageViewRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickedItemIndex < weatherList.size()-1)
                    clickedItemIndex++;
                else
                    clickedItemIndex=0;
                setupUI(clickedItemIndex);
            }
        });

        findViewById(R.id.imageViewLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickedItemIndex > 0)
                    clickedItemIndex--;
                else
                    clickedItemIndex = weatherList.size() - 1;
                setupUI(clickedItemIndex);
            }
        });
    }
     public void setupUI(int clickedItemIndex) {
        tvCityAndTime.setText(city + ", " + state + "(" + weatherList.get(clickedItemIndex).getTime() + ")");
        tvTemp.setText(weatherList.get(clickedItemIndex).getTemperature()+ (char)0x00B0 + "F");
        tvCloudsValue.setText(weatherList.get(clickedItemIndex).getClimateType());
        tvMaxTemp.setText(" " + weatherList.get(clickedItemIndex).getMaximumTemp() + " Fahrenheit");
        tvMinTemp.setText(" " + weatherList.get(clickedItemIndex).getMinimumTemp() + " Fahrenheit");
        tvFeelsLike.setText(" " + weatherList.get(clickedItemIndex).getFeelsLike()+ " Fahrenheit");
        tvHumidity.setText(" " + weatherList.get(clickedItemIndex).getHumidity() + "%");
        tvDewPoint .setText(" " + weatherList.get(clickedItemIndex).getDewpoint() + " Fahrenheit");
        tvPressure.setText(" " + weatherList.get(clickedItemIndex).getPressure() + " hPa");
        tvClouds.setText(" " + weatherList.get(clickedItemIndex).getClouds());
        tvWinds.setText(" " + weatherList.get(clickedItemIndex).getWindSpeed() + "mph, " + weatherList.get(clickedItemIndex).getWindDirection());
        Picasso.with(this).load(weatherList.get(clickedItemIndex).getIconUrl()).into(imageView);

    }
}
