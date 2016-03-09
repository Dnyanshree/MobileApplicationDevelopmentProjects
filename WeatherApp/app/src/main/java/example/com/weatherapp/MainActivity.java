package example.com.weatherapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/*
Assignment: Homework 05
FileName: MainActivity.java
Group 22
Team Members: Dnyanshree Shengulwar,
              Kedar Vijay Kulkarni,
              Marissa McLaughlin
*/
public class MainActivity extends AppCompatActivity {
    final static ArrayList<String> cities = new ArrayList<String>();
    String state, city;
    CityAdapter adapter;
    TextView tvDefaultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listViewCities);
        listView.setVisibility(View.INVISIBLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.weather_icon36dp);

        tvDefaultText = (TextView) findViewById(R.id.textViewDefault);
        city = getIntent().getStringExtra("City");
        state = getIntent().getStringExtra("State");
        String cityState = city + ", " + state;
        if(city!=null && state!=null) {
            if(!cities.contains(cityState))
            cities.add(city + ", " + state);
            listView.setVisibility(View.VISIBLE);
        }
    if (cities!=null && !cities.isEmpty()) {
        tvDefaultText.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        adapter = new CityAdapter(this, R.layout.row_item_layout, cities);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);//notifies whenever we make any changes to the adapter
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Demo", "Position: " + position + ", Value: " + cities.get(position).toString());
                Intent intent = new Intent(MainActivity.this, HourlyDataActivity.class);
                intent.putExtra("City", city);
                intent.putExtra("State", state);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Demo Long Press", "Position: " + position + ", Value: " + cities.get(position).toString());
                cities.remove(position);
                adapter.notifyDataSetChanged();
                if(cities.isEmpty()) {
                    listView.setVisibility(View.INVISIBLE);
                    tvDefaultText.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        for (String s : cities) {
            Log.d("Demo", s);
        }
    }
    else{
        System.out.println("The list is empty");
    }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.add_city:
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
