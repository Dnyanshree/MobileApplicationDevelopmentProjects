package com.example.root.stayintouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity {

    public static String URL_PATH="https://stay-in-touch22.firebaseio.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        final Firebase myFirebaseRef = new Firebase(URL_PATH);

        findViewById(R.id.buttonCreateNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });

        AuthData authData = myFirebaseRef.getAuth();

        if (authData != null) {
            Intent intent = new Intent(MainActivity.this, ConversationActivity.class);
            startActivity(intent);
        }
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            EditText etEmail= (EditText) findViewById(R.id.etEmail);
            EditText etPassword= (EditText) findViewById(R.id.etPassword);

            @Override
            public void onClick(View v) {


                myFirebaseRef.authWithPassword(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(MainActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                        System.out.print("Authentication" + authData.getProviderData());
                        Intent intent = new Intent(MainActivity.this, ConversationActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(MainActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                        ;
                    }
                });

            }
        });


    }
}
