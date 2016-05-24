package example.com.inclass11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView tvName, tvAmount, tvCategory, tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvName= (TextView)findViewById(R.id.textViewName);
        tvAmount= (TextView)findViewById(R.id.textViewAmount);
        tvCategory= (TextView)findViewById(R.id.textViewCategory);
        tvDate= (TextView)findViewById(R.id.textViewDate);

        tvName.setText(getIntent().getStringExtra("Name"));
        tvAmount.setText(getIntent().getStringExtra("Amount"));
        tvDate.setText(getIntent().getStringExtra("Date"));
        tvCategory.setText(getIntent().getStringExtra("Category"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.Delete:

                break;

        }
        return true;
    }
}
