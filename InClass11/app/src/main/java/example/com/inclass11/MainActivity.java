package example.com.inclass11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static final String URL_PATH ="https://group22-inclass11.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        final Firebase myFirebaseRef = new Firebase(URL_PATH);

        findViewById(R.id.buttonCreateNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        AuthData authData = myFirebaseRef.getAuth();

        if (authData != null) {
            Intent intent = new Intent(MainActivity.this, ExpensesListActivity.class);
            startActivity(intent);
        }
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            EditText etEmail= (EditText) findViewById(R.id.editTextEmail);
            EditText etPassword= (EditText) findViewById(R.id.editTextPassword);

            @Override
            public void onClick(View v) {
                System.out.println(etEmail.getText().toString() + etPassword.getText().toString());

                    myFirebaseRef.authWithPassword(etEmail.getText().toString(),etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            Intent intent = new Intent(MainActivity.this, ExpensesListActivity.class);
                            startActivity(intent);
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
