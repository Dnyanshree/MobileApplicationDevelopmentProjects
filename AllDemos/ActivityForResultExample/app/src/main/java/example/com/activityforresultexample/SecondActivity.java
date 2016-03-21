package example.com.activityforresultexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        editText = (EditText)findViewById(R.id.editTextHere);

        findViewById(R.id.buttonSendResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();
                if(value == null || value.length() == 0){
                    setResult(RESULT_CANCELED);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.VALUE_KEY, value);
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });

    }
}
