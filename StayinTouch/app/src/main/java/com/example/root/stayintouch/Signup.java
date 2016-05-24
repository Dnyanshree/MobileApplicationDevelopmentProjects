package com.example.root.stayintouch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText etEmail,etPassword,etName,etConfirmPassword,etPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Firebase.setAndroidContext(this);

        etEmail =(EditText)findViewById(R.id.editTextEmail);
        etPassword =(EditText)findViewById(R.id.editTextPassword);
        etName =(EditText)findViewById(R.id.editTextName);
        etConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
        etPhone=(EditText) findViewById(R.id.editTextPhone);

        final Firebase myFirebaseRef = new Firebase(MainActivity.URL_PATH+"/users");
        findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!etEmail.getText().toString().isEmpty()&&!etPassword.getText().toString().isEmpty()&&
                        !etName.getText().toString().isEmpty()&&!etConfirmPassword.getText().toString().isEmpty()&&
                        !etPhone.getText().toString().isEmpty() &&(
                        (etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))))
                    myFirebaseRef.createUser(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String,Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringUserMap) {

                            Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.android);
                            ByteArrayOutputStream stream=new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                            final byte[] image=stream.toByteArray();

                            final String img_str = Base64.encodeToString(image, 0);
                            final User user=new User(etEmail.getText().toString(),etPassword.getText().toString(),
                                    etName.getText().toString(),etPhone.getText().toString(),img_str);

                            myFirebaseRef.child(etEmail.getText().toString().split("@")[0]).setValue(user);

                            /*myFirebaseRef.authWithPassword(etEmail.getText().toString(), etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    Toast.makeText(Signup.this, "Sign up success!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Signup.this, ConversationActivity.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    Toast.makeText(Signup.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    ;
                                }
                            });*/
                            finish();

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(Signup.this,firebaseError.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
                else
                    Toast.makeText(Signup.this,"Please fill in all the details correctly!",Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
