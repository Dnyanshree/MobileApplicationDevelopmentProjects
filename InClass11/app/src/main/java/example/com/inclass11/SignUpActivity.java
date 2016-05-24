package example.com.inclass11;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
 EditText etEmail, etPassword, etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail =(EditText)findViewById(R.id.editTextEmail);
        etPassword =(EditText)findViewById(R.id.editTextPassword);
        etName =(EditText)findViewById(R.id.editTextName);
        final Firebase myFirebaseRef = new Firebase("http://group22-inclass11.firebaseio.com/users");
        findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final User user=new User(etEmail.getText().toString(),etName.getText().toString(),etPassword.getText().toString());
                //myFirebaseRef.setValue(user);
               // final Firebase ref = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com");

                myFirebaseRef.createUser(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String,Object>>() {
                 @Override
                    public void onSuccess(Map<String, Object> stringUserMap) {
                         myFirebaseRef.child(etName.getText().toString()).setValue(user);
                        finish();

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(SignUpActivity.this,firebaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
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
