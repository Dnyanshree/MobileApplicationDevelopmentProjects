package example.com.inclass2b;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/*
Assignment: In Class Assignment 2
File Name: InClass2b
Name: Dnyanshree Shengulwar
 */
public class MainActivity extends AppCompatActivity {
    private static RadioGroup rg;
    private static EditText etDist;
    private static TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etDist = (EditText) findViewById(R.id.editTextDistance);
        tvResult = (TextView) findViewById(R.id.textViewResult);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
            }
        });

        findViewById(R.id.buttonConvert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int etDistance = Integer.parseInt(etDist.getText().toString());
                Double finalDist;
                int id = rg.getCheckedRadioButtonId();
                if(id == R.id.radioButtonInches){
                    finalDist = 39.3701 * etDistance;
                    tvResult.setText("Inches: " + finalDist);
                }else if(id == R.id.radioButtonFeets){
                    finalDist = 3.28 * etDistance;
                    tvResult.setText("Feets: " + finalDist);
                }else if(id == R.id.radioButtonMiles){
                    finalDist = 0.0006 * etDistance;
                    tvResult.setText("Miles: " + finalDist);
                }else if(id == R.id.radioButtonClearAll){
                    tvResult.setText("Result: ");
                    etDist.setText("");
                }else{
                    Log.d("demo", "Invalid Input");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
