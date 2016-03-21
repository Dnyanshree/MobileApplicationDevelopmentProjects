package example.com.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ForecastActivity extends Activity implements GetHourlyDataAsyncTask.contextInterface {
    TextView tvLocation;
    int position;
    ForecastAdapter adapter;
    ArrayList<Weather> wDataCopy=null;
    public static ProgressDialog progressDialog;
    static int CREATE_REQ_CODE = 100;
    String city, state;
    DatabaseDataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        dm = DatabaseDataManager.getDatabaseDataManagerInstance();
        dm.init(this);

        progressDialog = new ProgressDialog(ForecastActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Forecast Data");

        city = getIntent().getStringExtra("City");
        state = getIntent().getStringExtra("State");

        tvLocation=(TextView)findViewById(R.id.textViewLocation);
        tvLocation.setText(city + "," + state);

        new GetHourlyDataAsyncTask(this,"forecast").execute("http://api.wunderground.com/api/6b461fc81d9e1283/forecast10day/q/" + state + "/" + city + ".xml");
    }

    @Override
    public void setupData(ArrayList<Weather> wData) {
        //Log.d("ForecastWeather",wData.toString());
        wDataCopy=wData;

        ListView listView = (ListView) findViewById(R.id.listView);
        for(Weather w: wDataCopy){
            //Log.d("HasNote",dm.hasNote(dm.getCitykey(city,state),w.getTime().split("on ")[1].split(", ")[0])+"");
            if(dm.hasNote(dm.getCitykey(city,state),w.getTime().split("on ")[1].split(", ")[0]))
                w.setTemperature("1");
        }
        adapter = new ForecastAdapter(this, R.layout.forecast_row_layout, wDataCopy);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ForecastActivity.this, AddNotesActivity.class);
                intent.putExtra("clickedID", position);

                intent.putExtra("Date", wDataCopy.get(position).getTime().split("on ")[1].split(", ")[0]);
                intent.putExtra("City", city);
                intent.putExtra("State", state);
                startActivityForResult(intent, CREATE_REQ_CODE);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                wDataCopy.get(position).setTemperature(null);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CREATE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(ForecastActivity.this, "Note Saved Successfully", Toast.LENGTH_LONG).show();
                position = data.getIntExtra("Flag",0);
                Log.d("Position",position+"");
                wDataCopy.get(position).setTemperature("1");
                adapter.notifyDataSetChanged();
                //Using Temperature as a flag to indicate if there is note or not.
            }
        }
    }


}
