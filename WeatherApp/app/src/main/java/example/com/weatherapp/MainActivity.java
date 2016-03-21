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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
Assignment: Homework 05
FileName: MainActivity.java
Group 22
Team Members: Dnyanshree Shengulwar,
              Kedar Vijay Kulkarni,
              Marissa McLaughlin
*/
public class MainActivity extends AppCompatActivity implements GetHourlyDataAsyncTask.contextInterface{
    ArrayList<String> cities;
    ArrayList<City> allCities= new ArrayList<>();
    CityAdapter adapter;
    DatabaseDataManager dm;
    TextView tvDefaultText;
    ListView listView;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewCities);
        listView.setVisibility(View.INVISIBLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.weather_icon36dp);

        dm = DatabaseDataManager.getDatabaseDataManagerInstance();
        dm.init(this);
        try{
            allCities=dm.getAllCities();
            count=0;
        }catch (Exception e){e.printStackTrace();}
        cities=new ArrayList<String>();
        tvDefaultText = (TextView) findViewById(R.id.textViewDefault);
        for(City c: allCities){
            new GetHourlyDataAsyncTask(MainActivity.this,"").execute("http://api.wunderground.com/api/6b461fc81d9e1283/hourly/q/"
                    + c.getState() + "/" + c.getCityname() + ".xml");
            cities.add(c.getCityname()+", "+c.getState());
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
                Intent intent = new Intent(MainActivity.this, CityDataActivity.class);
                intent.putExtra("City", cities.get(position).toString().split(",")[0]);
                intent.putExtra("State", (cities.get(position).toString().split(", ")[1]).split(":")[0]);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                City c=new City(cities.get(position).toString().split(",")[0],cities.get(position).toString().split(", ")[1]);
                Log.d("City", dm.deleteCity(c)+"");
                cities.remove(position);
                if(cities.isEmpty()) {
                    listView.setVisibility(View.INVISIBLE);
                    tvDefaultText.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });

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
            case R.id.add_city:
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivity(intent);
                return true;
            case R.id.clear_saved_cities:
                if(dm.deleteAllCities() && dm.deleteAllNotes())
                    Toast.makeText(MainActivity.this,"Delete Successful",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Delete Failed",Toast.LENGTH_LONG).show();
                listView.setVisibility(View.INVISIBLE);
                cities=null;
                tvDefaultText.setVisibility(View.VISIBLE);
                return true;
            case R.id.view_notes:
                return true;

            case R.id.sort_name:
                Collections.sort(allCities, new Comparator<City>() {
                    @Override//Ascending Order Sort
                    public int compare(final City object1, final City object2) {
                        return object1.getCityname().compareTo(object2.getCityname());
                    }

                });
                cities.removeAll(cities);
                count=0;
                for(City c: allCities){
                    new GetHourlyDataAsyncTask(MainActivity.this,"").execute("http://api.wunderground.com/api/6b461fc81d9e1283/hourly/q/"
                            + c.getState() + "/" + c.getCityname() + ".xml");
                    cities.add(c.getCityname()+", "+c.getState());
                }
                adapter.notifyDataSetChanged();
                return true;
            case R.id.sort_name1:
                Collections.sort(allCities, new Comparator<City>() {
                    @Override//Descending Order Sort
                    public int compare(final City object1, final City object2) {
                        return object2.getCityname().compareTo(object1.getCityname());
                    }

                });
                cities.removeAll(cities);
                count=0;
                for(City c: allCities){
                    new GetHourlyDataAsyncTask(MainActivity.this,"").execute("http://api.wunderground.com/api/6b461fc81d9e1283/hourly/q/"
                            + c.getState() + "/" + c.getCityname() + ".xml");
                    cities.add(c.getCityname()+", "+c.getState());
                }
                adapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setupData(ArrayList<Weather> wData) {

        String city_state=cities.get(count);
        cities.remove(count);
        cities.add(count, city_state + ":" + wData.get(0).getTemperature());
        adapter.notifyDataSetChanged();
        count++;
    }
}
