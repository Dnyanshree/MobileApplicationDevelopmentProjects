package example.com.dynamiclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(relativeLayout);

        TextView textViewHelloWorld = new TextView(this);
        textViewHelloWorld.setText(R.string.HelloWorld_TextView_Label);
        textViewHelloWorld.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textViewHelloWorld.setId(View.generateViewId());
        relativeLayout.addView(textViewHelloWorld);

        Button buttonClickMe = new Button(this);
        buttonClickMe.setText(R.string.Click_Button_Label);
        buttonClickMe.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.addRule(RelativeLayout.BELOW,textViewHelloWorld.getId());
        buttonClickMe.setLayoutParams(buttonLayoutParams);
        relativeLayout.addView(buttonClickMe);


    }
}
