package foodapp.kk.com.howgeekyareyou;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {


    private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 8000;
    private boolean didUserClick=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Message msg = new Message();
        msg.what = STOPSPLASH;

        splashHandler.sendMessageDelayed(msg, SPLASHTIME);

        findViewById(R.id.buttonStartQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                didUserClick=true;
                showWelcomeScreen();
            }
        });
    }

    public void showWelcomeScreen(){
        Intent intent=new Intent(MainActivity.this,Welcome.class);
        startActivity(intent);
        finish();
    }


    //handler for splash screen
    private Handler splashHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    //remove SplashScreen from view
                    if(!didUserClick)
                        showWelcomeScreen();
                    break;
            }
            super.handleMessage(msg);
        }
    };



}
