package example.com.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //String[] colors = {"Red", "Blue", "Green", "White", "Black", "Orange", "Yellow"};
    ArrayList<Color> colors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors = new ArrayList<Color>();
        colors.add(new Color("Red", "#ff0000"));
        colors.add(new Color("Blue", "#0000FF"));
        colors.add(new Color("Green", "#008000"));
        colors.add(new Color("White", "#FFFFFF"));
        colors.add(new Color("Black", "#000000"));
        colors.add(new Color("Orange", "#FFA500"));
        colors.add(new Color("Yellow", "#FFFF00"));

        ListView listView = (ListView) findViewById(R.id.listView1);
      //  ArrayAdapter<Color> adapter = new ArrayAdapter<Color>(this, android.R.layout.simple_list_item_1, colors);
        ColorAdapter adapter = new ColorAdapter(this, R.layout.row_item_layout, colors);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);//notifies whenever we make any changes to the adapter
        /*
        adapter.add(new Color("Purple", "#800080"));
        adapter.remove(colors.get(3));
        adapter.insert(new Color("Brown", "#A52A2A"), 3);
        */
       // adapter.notifyDataSetChanged();//notify the adapter that the data has changed

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Demo", "Position: " + position + ", Value: " + colors.get(position).toString());
            }
        });
    }
}
