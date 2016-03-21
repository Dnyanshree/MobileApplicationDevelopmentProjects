package example.com.intentsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if(getIntent().getExtras() != null){
            String name = getIntent().getExtras().getString(MainActivity.NAME_KEY);
            double age = getIntent().getExtras().getDouble(MainActivity.AGE_KEY, 22.0);
            User user = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
           // Toast.makeText(this,name + " " + age,Toast.LENGTH_LONG).show();
           //Toast.makeText(this,user.toString(),Toast.LENGTH_LONG).show();
            Person person = getIntent().getExtras().getParcelable(MainActivity.PERSON_KEY);
            Toast.makeText(this,person.toString(),Toast.LENGTH_LONG).show();
        }else{

        }findViewById(R.id.buttonDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
