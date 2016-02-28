package example.com.inclass4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import example.com.inclass4.Util;

/*
* Assignment: InClass4
* Filename: MainActivity.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private CheckBox cbNumbers,cbUppercase, cbLowercase, cbSpecialchars;
    private Boolean bNumbers, bUppercase, bLowercase, bSpecialchars;
    ProgressDialog progressDialog;
    private String Password;
    private int Length;
    private TextView tvPassword;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPassword=(TextView) findViewById(R.id.textViewGeneratedPassword);

        cbNumbers= (CheckBox) findViewById(R.id.checkBoxNumber);
        cbUppercase= (CheckBox) findViewById(R.id.checkBoxUppercase);
        cbLowercase= (CheckBox) findViewById(R.id.checkBoxLowercase);
        cbSpecialchars= (CheckBox) findViewById(R.id.checkBoxSpecialchars);


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Generating Passwords...");

        adapter= ArrayAdapter.createFromResource(this,R.array.spinnerItems,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinnerLength);
        spinner.setAdapter(adapter);


        handler = new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what){
                    case DoWorkWithThread.STATUS_START:
                        progressDialog.show();
                        break;

                    case DoWorkWithThread.STATUS_DONE:
                        progressDialog.dismiss();
                        tvPassword.setText(Password);
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.buttonThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbSpecialchars.isChecked() || cbUppercase.isChecked()|| cbLowercase.isChecked() || cbNumbers.isChecked()) {
                    new Thread(new DoWorkWithThread()).start();
                    bNumbers= cbNumbers.isChecked();
                    bUppercase=cbUppercase.isChecked();
                    bLowercase=cbLowercase.isChecked();
                    bSpecialchars=cbSpecialchars.isChecked();
                }else
                    Toast.makeText(MainActivity.this,"Please check at lease one checkbox!!",Toast.LENGTH_LONG ).show();
            }
        });
        findViewById(R.id.buttonAsync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Logging", spinner.getSelectedItemPosition()+"");
                Length=spinner.getSelectedItemPosition();
                if(cbSpecialchars.isChecked() || cbUppercase.isChecked()|| cbLowercase.isChecked() || cbNumbers.isChecked()) {
                    new DoWork().execute();
                    bNumbers= cbNumbers.isChecked();
                    bUppercase=cbUppercase.isChecked();
                    bLowercase=cbLowercase.isChecked();
                    bSpecialchars=cbSpecialchars.isChecked();
                }else
                    Toast.makeText(MainActivity.this,"Please check at lease one checkbox!!",Toast.LENGTH_LONG ).show();
            }
        });
    }

    class DoWork extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Password= Util.getPassword(Length,bNumbers,bUppercase,bLowercase,bSpecialchars);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            tvPassword.setText(Password);
        }

    }

    class DoWorkWithThread implements Runnable{
        static final int STATUS_START = 0x00;
        static final int STATUS_DONE = 0x01;

        @Override
        public void run() {
            Message msg = new Message();
            msg.what = STATUS_START;
            handler.sendMessage(msg);

            Password= Util.getPassword(Length,bNumbers,bUppercase,bLowercase,bSpecialchars);

            msg = new Message();
            msg.what = STATUS_DONE;
            handler.sendMessage(msg);
        }
    }

}
