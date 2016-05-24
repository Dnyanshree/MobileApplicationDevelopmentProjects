package com.example.root.stayintouch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ViewContactActivity extends AppCompatActivity {
    TextView tvFullName, tvName, tvPhone, tvEmail;
    ImageView ivProfilePic;
    User userForContact;
    Firebase myFbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        final Firebase myFirebaseRef= new Firebase(MainActivity.URL_PATH+"/Messages");
        final Firebase myUserRef= new Firebase(MainActivity.URL_PATH+"/users");

        AuthData authData = myFirebaseRef.getAuth();

        if (authData == null) {
            finish();
        }
        tvFullName = (TextView) findViewById(R.id.textViewFullName);
        tvName = (TextView) findViewById(R.id.textViewName);
        tvPhone = (TextView) findViewById(R.id.textViewPhone);
        tvEmail = (TextView) findViewById(R.id.textViewEmail);
        ivProfilePic = (ImageView) findViewById(R.id.imageViewProfilePic);



        final String name = getIntent().getStringExtra("name");

        myUserRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    userForContact = userSnapshot.getValue(User.class);

                    if (name.equals(userForContact.getName())) {

                        tvFullName.setText(userForContact.getName());
                        tvName.setText(userForContact.getName());
                        tvPhone.setText(userForContact.getPhone());
                        tvEmail.setText(userForContact.getEmail());
                        byte[] decodedString = Base64.decode(userForContact.getProfilePic(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        ivProfilePic.setImageBitmap(decodedByte);

                    }
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


       // myFbRef=myFirebaseRef;
     //  Log.d("Demo",myFirebaseRef.getAuth().getProviderData().values().toString());

    }
}
