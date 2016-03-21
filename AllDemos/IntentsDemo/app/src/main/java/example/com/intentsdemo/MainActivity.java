package example.com.intentsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final static String NAME_KEY = "NAME";
    final static String AGE_KEY = "AGE";
    final static String USER_KEY = "USER";
    final static String PERSON_KEY = "PERSON";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button2Second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                //Implicit Intent
               // Intent intent = new Intent("example.com.intentsdemo.intent.action.VIEW");
               // intent.addCategory(Intent.CATEGORY_DEFAULT);

                //Passing simple data between activities
                intent.putExtra(NAME_KEY, "Bob Smith");
                intent.putExtra(AGE_KEY, 27.5);
                User user = new User("Alice", 23.7);
                intent.putExtra(USER_KEY, user);

                Person person = new Person("Harry Potter","Hogwarts",19.3);
                intent.putExtra(PERSON_KEY,person);

                startActivity(intent);




            }
        });
    }
}
